package com.pltech.study.kotlin.other;

import com.google.gson.JsonArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DepUtil {
    static void parseDependency(
            String[] lines,
            String depFilePath,
            String ancestryFile,
            String offspringsPath) {
        /*
         * LinkedList可实现栈和队列数据：
         * 栈 ： push表示入栈，在头部添加元素 ， pop表示出栈，返回头部元素 ，peek查看栈头部元素
         * 队列： offer 尾部添加元素， poll 返回头部元素，并且从队列中删除， peek返回头部元素
         */
        LinkedList<DependencyBean> stack = new LinkedList<>();
        HashMap<String, ArrayList<DependencyBean>> aarMap = new HashMap<>();
        HashMap<String, ConflictLibBean> pathMap = new HashMap<>();
        if (lines == null || lines.length < 1) {
            return;
        }
        JsonArray jsonArray = new JsonArray();
        try {

            for (String line : lines) {
                if (!line.contains("--- ") || line.contains("{strictly")) {
                    continue;
                }
                line = line.replace(" -> ", ":")
                        .replace("FAILED", "")
                        .replace(" (*)", "")
                        .replace("(*)", "")
                        .replace("(c)", "")
                        .replace("[", "")
                        .replace("]", "")
                        .replace("\\", "+")
                        .replace("+---", "    ")
                        .replace("|", " ")
                        .replace("     ", "!");
                String newLine = line.replace("!", "");
                /* 忽略不检查的库 */
                if (newLine.startsWith("project")) {
                    continue;
                }

                String[] dependency = newLine.split(":");
                if (dependency.length <= 2) {
                    continue;
                }

                int lineNum = calculateDepth(line);

                DependencyBean aarBean = new DependencyBean();
                aarBean.setLibGroup(dependency[0]);
                aarBean.setLibName(dependency[1]);
                aarBean.setCurrVersion(dependency[2]);
                aarBean.setLibDesc(aarBean.getLibGroup() + ":" + aarBean.getLibName() + ":" + aarBean.getCurrVersion());
                aarBean.setTargetVersion(dependency[2]);
                aarBean.setAarDepth(lineNum);
                if (dependency.length > 3) {
                    aarBean.setTargetVersion(dependency[3]);
                    aarBean.setLibDesc(aarBean.getLibDesc() + " -> " + aarBean.getTargetVersion());
                }
                if (stack.isEmpty()) {
                    stack.addLast(aarBean);
                } else {
                    DependencyBean topBean = stack.peekLast();
                    while (!stack.isEmpty() && topBean.getAarDepth() >= lineNum) {
                        stack.removeLast();
                        topBean = stack.peekLast();
                    }
                    stack.addLast(aarBean);
                }
                String key = aarBean.getLibGroup() + ":" + aarBean.getLibName();
                // 当前库在依赖树中的向上到根的路径
                ConflictLibBean depPath;
                if (pathMap.containsKey(key)) {
                    depPath = pathMap.get(key);
                } else {
                    depPath = new ConflictLibBean();
                }
                depPath.setLibDesc(key);
                depPath.setTargetVer(aarBean.getTargetVersion());
                StringBuilder path = new StringBuilder();
                String blank = "    ";//换行锁进空白符
                for (DependencyBean it : stack) {
                    if (!aarBean.getTargetVersion().equals(aarBean.getCurrVersion())) {
                        path.append("--->").append(it).append("\n").append(blank);
                        blank += "    ";
                    }
                    //addMap(it, aarBean, aarMap);
                }
                if (path.length() > 0) {
                    depPath.getDepPaths().add(path.toString().trim());
                    pathMap.put(key, depPath);
                }

                //构建每个依赖库的节点对象供打印
                //createDepBean(dependency, jsonArray);
            }
            //打印依赖当前库的依赖路径
            writeAncestryPath(ancestryFile, pathMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //writeLibraryNode(jsonArray.toString(), depFilePath);
        //打印当前库的所有依赖库
        //writeDependencyPath(offspringsPath, aarMap);
    }

    private static void writeAncestryPath(
            String gradleFilePath,
            HashMap<String, ConflictLibBean> aarMap
    ) throws IOException {
        //创建一个字符写入的流对象
        File file = new File(gradleFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        for (Map.Entry<String, ConflictLibBean> entry : aarMap.entrySet()) {
            String key = entry.getKey();
            List<String> pathList = entry.getValue().getDepPaths();
            if (pathList != null) {
                out.write(key);
                for (String path : pathList) {
                    out.newLine();
                    out.write(path);
                }
                out.newLine();
            }
            out.newLine();
            out.flush();
        }
    }

    /**
     * 计算依赖库在树的深度
     */
    private static int calculateDepth(String str) {
        if (str.isEmpty()) return 0;
        str = str.substring(0, str.lastIndexOf('!'));
        return str.length();
    }

    /**
     * @param targetAarBean : 当前需要加入的设置
     */
    private void addMap(
            DependencyBean targetAarBean,
            DependencyBean aarBean,
            HashMap<String, ArrayList<DependencyBean>> aarMap
    ) {
//        if (aarMap.containsKey(targetAarBean.getLibDesc())) {
//            ArrayList<DependencyBean> list = aarMap.get(targetAarBean.getLibDesc());
//            boolean isContain = false;
//            list ?.forEach {
//                if (it.libDesc == aarBean.libDesc) {
//                    isContain = true
//                }
//            }
//            if (!isContain) {
//                list !!.add(aarBean)
//            }
//        } else {
//            var list = ArrayList < DependencyBean > ()
//            list.add(aarBean)
//            aarMap[targetAarBean.libDesc] = list
//        }
    }
}
