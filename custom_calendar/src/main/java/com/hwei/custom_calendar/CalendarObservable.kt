package com.hwei.custom_calendar

import androidx.annotation.IntDef

object CalendarObservable {

    private val list = mutableListOf<CalendarObserver>()

    fun addObserver(calendarObserver: CalendarObserver) {
        list.add(calendarObserver)
    }

    fun clearObserver(calendarObserver: CalendarObserver) {
        list.remove(calendarObserver)
    }

    fun clearAll() {
        list.clear()
    }


    fun notified(@Direct int: Int) {
        list.forEach {
            it.notified(int)
        }
    }

    const val LEFT = 0
    const val RIGHT = 1

    @IntDef(LEFT, RIGHT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Direct

}


interface CalendarObserver {
    fun notified(@CalendarObservable.Direct int: Int)
}