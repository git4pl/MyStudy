package com.pltech.study.kotlin.other

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.*
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * gradle 依赖树分析工具
 */
object FileUtil {

    /**
     * 计算依赖库在树的深度
     */
    private fun calculateDepth(str: String): Int {
        if (str.isEmpty()) return 0
        var count = 0
        run outside@{
            str.toCharArray().forEach {
                if (it == '!') {
                    count++
                } else {
                    return@outside
                }
            }
        }
        return count
    }

    /**
     * @param targetAarBean : 当前需要加入的设置
     */
    private fun addMap(
        targetAarBean: DependencyBean,
        aarBean: DependencyBean,
        aarMap: HashMap<String, ArrayList<DependencyBean>>
    ) {
        if (aarMap.containsKey(targetAarBean.libDesc)) {
            val list: ArrayList<DependencyBean>? = aarMap[targetAarBean.libDesc]
            var isContain = false
            list?.forEach {
                if (it.libDesc == aarBean.libDesc) {
                    isContain = true
                }
            }
            if (!isContain) {
                list!!.add(aarBean)
            }
        } else {
            var list = ArrayList<DependencyBean>()
            list.add(aarBean)
            aarMap[targetAarBean.libDesc] = list
        }
    }

    /**
     * @param inputStream :gradle解析输入流信息
     * @param depFilePath 保存生成的json文件
     * @param ancestryFile 保存依赖当前库的祖先库节点
     * @param offspringsPath 保存当前库的子孙库节点
     */
    @JvmStatic
    fun parseDependency(
        inputStream: InputStream?,
        depFilePath: String,
        ancestryFile: String,
        offspringsPath: String
    ) {
        /**
         * LinkedList可实现栈和队列数据：
         * 栈 ： push表示入栈，在头部添加元素 ， pop表示出栈，返回头部元素 ，peek查看栈头部元素
         * 队列： offer 尾部添加元素， poll 返回头部元素，并且从队列中删除， peek返回头部元素
         */
        val stack = LinkedList<DependencyBean>()
        val aarMap: HashMap<String, ArrayList<DependencyBean>> = HashMap()
        val pathMap: HashMap<String, ConflictLibBean?> = HashMap()
        //val sdkList = ArrayList<SDKModelBean>()
        if (inputStream == null) {
            return
        }
        val jsonArray = JsonArray()
        val reader: BufferedReader?
        try {
            reader = BufferedReader(
                InputStreamReader(inputStream)
            )
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (!line!!.contains("--- ")) {
                    continue
                }
                if (line!!.contains("{strictly")) {
                    continue
                }

                val replaceLine = line!!
                    .replace(" -> ", ":")
//                            .replace("|", "")
//                            .replace(" ", "")
                    .replace("FAILED", "")
                    .replace(" (*)", "")
                    .replace("(*)", "")
                    .replace("(c)", "")
                    .replace("[", "")
                    .replace("]", "")
                    .replace("\\", "+")
                    .replace("+---", "    ")
                    .replace("|", " ")
                    .replace("     ", "!")
                val newLine = replaceLine.replace("!", "")
                /* 忽略不检查的库 */
                if (
//                            newLine.startsWith("com.google.guava:guava")
//                            || newLine.startsWith("org.apache.httpcomponents")
//                            || newLine.startsWith("org.jetbrains.kotlin")
//                            || newLine.startsWith("com.google.")
//                            || newLine.startsWith("io.reactivex")
//                            || newLine.startsWith("com.jakewharton")
//                            || newLine.startsWith("androidx")
//                            ||
                    newLine.startsWith("project")
                ) {
                    continue
                }

                val dependency = newLine.split(":".toRegex()).toTypedArray()
                if (dependency.isEmpty() || dependency.size <= 2) {
                    continue
                }

                val lineNum = calculateDepth(replaceLine)

                val aarBean = DependencyBean()
                aarBean.libGroup = dependency[0]
                aarBean.libName = dependency[1]
                aarBean.currVersion = dependency[2]
                aarBean.libDesc =
                    aarBean.libGroup + ":" + aarBean.libName + ":" + aarBean.currVersion
                aarBean.targetVersion = dependency[2]
                aarBean.aarDepth = lineNum
                if (dependency.size > 3) {
                    aarBean.targetVersion = dependency[3]
                    aarBean.libDesc += " -> ${aarBean.targetVersion}"
                }
                if (stack.isEmpty()) {
                    stack.addLast(aarBean)
                } else {
                    var topBean = stack.peekLast()
                    while (!stack.isEmpty() && topBean.aarDepth >= lineNum) {
                        stack.removeLast()
                        topBean = stack.peekLast()
                    }
                    stack.addLast(aarBean)
                }
                val key = aarBean.libGroup + ":" + aarBean.libName
                // 当前库在依赖树中的向上到根的路径
                val depPath = if (pathMap.containsKey(key)) {
                    pathMap[key]
                } else ConflictLibBean()
                depPath?.libDesc = key
                depPath?.targetVer = aarBean.targetVersion
                var path = ""
                var blank = "    "//换行锁进空白符
                stack.forEach {
                    if (aarBean.targetVersion !== aarBean.currVersion) {
                        path = "$path--->$it\n$blank"
                        blank += "    "
                    }
                    addMap(it, aarBean, aarMap)
                }
                if (path.isNotEmpty()) {
                    depPath?.depPaths?.add(path.trimEnd())
                    pathMap[key] = depPath
                }

                //构建每个依赖库的节点对象供打印
                createDepBean(dependency, jsonArray)
            }
            //打印依赖当前库的依赖路径
            writeAncestryPath(ancestryFile, pathMap)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        writeLibraryNode(jsonArray.toString(), depFilePath)
        //打印当前库的所有依赖库
        writeDependencyPath(offspringsPath, aarMap)
    }

    private fun writeAncestryPath(
        gradleFilePath: String,
        aarMap: HashMap<String, ConflictLibBean?>,
    ) {
        //创建一个字符写入的流对象
        val file = File(gradleFilePath)
        if (!file.exists()) {
            file.createNewFile()
        }
        val out: BufferedWriter?
        out = BufferedWriter(FileWriter(file))
        for ((key, value) in aarMap.entries) {
            val pathList = value?.depPaths
            if (pathList != null) {
                out.write(key)
                for (path in pathList) {
                    out.newLine()
                    out.write(path)
                }
                out.newLine()
            }
            out.newLine()
            out.flush()
        }
    }

    /**
     * 打印出各个节点的子依赖节点
     */
    private fun writeDependencyPath(
        gradleFilePath: String,
        aarMap: HashMap<String, ArrayList<DependencyBean>>
    ) {
        //创建一个字符写入的流对象
        val file = File(gradleFilePath)
        if (!file.exists()) {
            file.createNewFile()
        }
        var out: BufferedWriter? = null
        out = BufferedWriter(FileWriter(file))

        for ((key, value) in aarMap.entries) {
            //val sb = StringBuffer()
            out.write(key)
            value.forEach {
                out.newLine()
                out.write("+---")
                out.write(it.toString())
                //sb.append(it.toString())
            }
            out.newLine()
            out.newLine()
            out.flush()
        }
    }

    /**
     * 打印出所有库节点信息，包括Group名、库名、使用版本、目标版本
     */
    private fun writeLibraryNode(toBeWritten: String, depFilePath: String) {
        val file = File(depFilePath)
        if (file.exists()) {
            file.delete()
        }
        var osw: OutputStreamWriter? = null
        try {
            file.createNewFile()
            val os: OutputStream = FileOutputStream(file)
            osw = OutputStreamWriter(os, StandardCharsets.UTF_8)
            osw.write(toBeWritten)
            osw.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (osw != null) {
                try {
                    osw.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 构建打印依赖库属性的节点
     */
    private fun createDepBean(dependency: Array<String>, jsonArray: JsonArray) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("group", dependency[0])
        jsonObject.addProperty("name", dependency[1])
        jsonObject.addProperty("version", dependency[2])
        if (dependency.size > 3) {
            jsonObject.addProperty("newVersion", dependency[3])
        }
        jsonArray.add(jsonObject)
    }
}