package com.hwei.custom_calendar

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CalendarMonth @JvmOverloads constructor(
    context: Context,
    private val position: Int? = 0,
    attrs: AttributeSet? = null,
    def: Int = -1
) :
    FrameLayout(context, attrs, def), CalendarObserver {

    private val currentDate = Calendar.getInstance()

    private var onChooseDateListener: OnChooseDateListener? = null

    init {
        when (position) {
            0 -> currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) - 2)
            1 -> currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) - 1)
            2 -> null
            3 -> currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) + 1)
            4 -> currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) + 2)
        }
        val view = LayoutInflater.from(context).inflate(R.layout.view, this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 7)
            .apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        if (position == 0 && haveEmptyView()) {
                            val calendar = Calendar.getInstance().apply {
                                set(
                                    currentDate.get(Calendar.YEAR),
                                    currentDate.get(Calendar.MONTH),
                                    1
                                )
                            }
                            return calendar.get(Calendar.DAY_OF_WEEK) - 1
                        } else {
                            return 1
                        }
                    }
                }
            }
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(context).inflate(R.layout.day, parent, false)
                return object : RecyclerView.ViewHolder(view) {}.apply {
                    this.itemView.setOnClickListener {
                        onChooseDateListener?.onChoose(
                            Date(
                                currentDate.get(Calendar.YEAR) - 1900,
                                currentDate.get(Calendar.MONTH),
                                layoutPosition
                            )
                        )
                    }
                }
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                if (haveEmptyView() && position == 0) {
                    return
                }
                holder.itemView.findViewById<TextView>(R.id.tv).text =
                    if (position == 1 && haveEmptyView()) {
                        (currentDate.get(Calendar.MONTH) + 1).toString() + "月"
                    } else {
                        position.toString()
                    }
            }

            override fun getItemCount(): Int {
                if (haveEmptyView()) {
                    return 1 + Utils.getDayOfMonth(
                        currentDate.get(Calendar.YEAR),
                        currentDate.get(Calendar.MONTH) + 1
                    )
                }
                return Utils.getDayOfMonth(
                    currentDate.get(Calendar.YEAR),
                    currentDate.get(Calendar.MONTH) + 1
                )
            }
        }
        CalendarObservable.addObserver(this)
    }

    private fun haveEmptyView(): Boolean {
        return currentDate.get(Calendar.DAY_OF_WEEK_IN_MONTH) != 0
    }

    interface OnChooseDateListener {
        fun onChoose(date: Date)
    }

    fun setOnChooseDateListener(onChooseDateListener: OnChooseDateListener) {
        this.onChooseDateListener = onChooseDateListener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("calendarView", "onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d("calendarView", "onLayout")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("calendarView", "onDraw")
    }

    override fun notified(@CalendarObservable.Direct int: Int) {
        if (int == CalendarObservable.LEFT) {
            currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) - 2)
        } else {
            currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) + 2)
        }
        requestLayout()
        invalidate()
    }
}