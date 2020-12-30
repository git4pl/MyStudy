package com.pltech.study.kotlin

class AlgorithmForCharacters {
    /**
     * 求一组字符串的公共前缀
     */
    fun longestCommonPrefix(strList: Array<String>): String {
        if (strList.isEmpty()) {
            return ""
        }
        val str = strList[0]
        for (i in str.indices) {
            val ch = str[i]
            for (j in 1 until strList.size) {
                if (i == strList[j].length || ch != strList[j][i]) {
                    return str.substring(0, i)
                }
            }
        }
        return str
    }


}