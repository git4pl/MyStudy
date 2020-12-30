package com.wb.study

/**
 *
 * Created by Pang Li on 2020/12/23
 */
object BookRatingUtil {
    @JvmStatic
    fun getRating(rating: Int): String {
        return when (rating) {
            1 -> "一星"
            2 -> "二星"
            3 -> "三星"
            4 -> "四星"
            5 -> "五星"
            else -> "未评星"
        }
    }
}