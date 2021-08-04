package com.hwei.lib_common.widge

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible
import com.hwei.lib_base.ktx.dp2px
import com.hwei.lib_common.R

class ProgressView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    def: Int = -1
) : View(context, attributeSet, def) {

    private var progress = 0
    private val lineHeight = 20.dp2px

    private val mPaint = Paint().apply {
        isAntiAlias = true
        color = context.getColor(R.color.app_600)
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, (width * progress / 100).toFloat(), lineHeight.toFloat(), mPaint)
    }

    fun start(newProgress: Int) {
        val animation = ObjectAnimator.ofInt(this, "progress", progress, newProgress)
        animation.duration = 300
        animation.interpolator = AccelerateDecelerateInterpolator()
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                if (progress == 100) {
                    this@ProgressView.isVisible = false
                }
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })
        animation.start()
    }
}