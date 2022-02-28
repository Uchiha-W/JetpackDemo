package com.fzm.glass.lib_update

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.glass_update_dialog_update.*
import kotlin.math.roundToInt

/**
 * @author zhengjy
 * @since 2018/08/06
 * Description:App更新Dialog
 */
class UpdateDialog(context: Context) : Dialog(context) {
    /**
     * DATA
     */
    private var firstUpdateProgress = true // 第一次更新进度
    private var updatingTip: String // 正在更新时的默认提示

    /**
     * 设置是否"强制更新"
     */
    fun setUpdateForce(force: Boolean): UpdateDialog {
        if (force) {
            tv_ignore.text = "退出"
            tv_ignore.setOnClickListener { ActivityUtils.finishAllActivities() }
        } else {
            tv_ignore.text = "忽略"
            tv_ignore.setOnClickListener { cancel() }
        }
        return this
    }

    fun setVersion(title: String?): UpdateDialog {
        newVersionText.text = title
        return this
    }

    /**
     * 设置下载信息
     */
    fun setMessage(message: String?): UpdateDialog {
        // 设置gravity  一行就center  否则start|center_vertical
        val viewTreeObserver = tv_message.viewTreeObserver
        viewTreeObserver.addOnPreDrawListener {
            val lineCount = tv_message.lineCount
            if (lineCount <= 1) {
                tv_message.gravity = Gravity.CENTER
            } else {
                tv_message.gravity = Gravity.START or Gravity.CENTER_VERTICAL
            }
            true
        }
        tv_message.text = message
        return this
    }

    /**
     * 设置下载时的提示
     */
    fun setUpdatingTip(tips: String): UpdateDialog {
        updatingTip = tips
        return this
    }

    /**
     * 更新下载进度
     */
    @SuppressLint("SetTextI18n")
    fun setProgress(progress: Int): UpdateDialog {
        ll_update_progress.visibility = View.VISIBLE
        ll_button.visibility = View.GONE
        if (progress != 0 && firstUpdateProgress) { // 首次进入则修改下载提示
            tv_update_tip.text = updatingTip
            firstUpdateProgress = false
        }
        tv_update_percent.text = "$progress%"
        pb_update_progress.progress = progress
        return this
    }

    /**
     * 设置"更新"按钮事件
     */
    fun setUpdateButtonEvent(listener: OnClickListener?): UpdateDialog {
        tv_update.setOnClickListener(listener)
        return this
    }

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.glass_update_dialog_update)
        updatingTip = context.getString(R.string.glass_update_downloading)
        // 默认不强制更新
        tv_ignore.setOnClickListener { cancel() }
    }
}