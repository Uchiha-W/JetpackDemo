package com.hwei.custom_calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.text.SimpleDateFormat
import java.util.*


class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) :
    ViewPager(context, attrs) {

    val current = Calendar.getInstance()
    val last: Calendar
        get() = Calendar.getInstance().apply {
            set(Calendar.MONTH, current.get(Calendar.MONTH) - 1)
        }
    val next: Calendar
        get() = Calendar.getInstance().apply {
            set(Calendar.MONTH, current.get(Calendar.MONTH) + 1)
        }

    val list = listOf(
        CalendarMonth(context, 0), CalendarMonth(context, 1),
        CalendarMonth(context, 2),
        CalendarMonth(context, 3), CalendarMonth(context, 4)
    )

    init {
        adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return list.size
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val calendar = list[position].apply {
                    setOnChooseDateListener(object : CalendarMonth.OnChooseDateListener {
                        override fun onChoose(date: Date) {
                            Toast.makeText(
                                context,
                                SimpleDateFormat("yyyy-MM-dd").format(date),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
                container.addView(calendar)
                return calendar
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View?)
            }
        }


        this.addOnPageChangeListener(object : OnPageChangeListener {
            var currentPosition = 2
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == SCROLL_STATE_IDLE) {
                    if (currentPosition == list.size - 1) {
                        //setCurrentItem(1, false)
                        setCurrentItem(2, false)
                        CalendarObservable.notified(CalendarObservable.RIGHT)
                    } else if (currentPosition == 0) {
                        setCurrentItem(2, false)
                        CalendarObservable.notified(CalendarObservable.LEFT)
                        //setCurrentItem(list.size - 2, false)
                    }
                }
            }

        })
        setCurrentItem(2, false)
    }
}