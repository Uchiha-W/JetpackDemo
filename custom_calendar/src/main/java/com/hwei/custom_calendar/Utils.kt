package com.hwei.custom_calendar

object Utils {
    /**
     * 获取一个月多少天
     */
    fun getDayOfMonth(year: Int, month: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
                29
            } else {
                28
            }
            else -> throw Exception("1年只有12个月")
        }
    }
}