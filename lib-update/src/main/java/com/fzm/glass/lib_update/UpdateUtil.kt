package com.fzm.glass.lib_update

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ToastUtils
import com.fuzamei.lib_base.AppUpdateInfoBean
import com.fuzamei.lib_base.utils.XToast
import com.vector.update_app.UpdateAppBean
import com.vector.update_app.UpdateAppManager
import com.vector.update_app.service.DownloadService.DownloadCallback
import java.io.File
import kotlin.math.roundToInt

/**
 * App更新工具
 */
object UpdateUtil {
    /**
     * 获取当前本地apk的版本Code
     */
    fun getVersionCode(context: Context): Int {
        var versionCode = 0
        try {
            versionCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionCode
    }

    /**
     * 获取当前本地apk的版本Name
     */
    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionName
    }

    /**
     * 检测最新版本提示
     * @param context 上下文
     */
    fun showAppVersionCheckToast(context: Context) {
        try {
            XToast.toastShort(
                String.format(
                    context.getString(R.string.glass_update_current_is_newest_version_XX),
                    "V${getVersionName(context)}"
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            XToast.toastShort(R.string.glass_update_current_is_newest_version)
        }
    }

    /**
     * @param fragmentActivity 上下文
     * @param appUpdateInfoBean App更新数据类
     */
    fun showUpdateDialog(fragmentActivity: FragmentActivity, appUpdateInfoBean: AppUpdateInfoBean) {
        val forceUpdate = appUpdateInfoBean.isForce
        val apkFileUrl = appUpdateInfoBean.installAddress
        val updateDialog = UpdateDialog(fragmentActivity)
            .setVersion("V${appUpdateInfoBean.version}")
            .setMessage(appUpdateInfoBean.content)
            .setUpdateForce(forceUpdate)
        updateDialog.setCancelable(!forceUpdate)
        updateDialog.setUpdateButtonEvent(object : View.OnClickListener {
            override fun onClick(v: View?) {
                downloadApk(fragmentActivity, apkFileUrl, object : DownloadCallback {
                    override fun onStart() {
                        updateDialog.setProgress(0)
                    }

                    override fun onProgress(progress: Float, totalSize: Long) {
                        updateDialog.setProgress((progress * 100).roundToInt())
                    }

                    override fun setMax(totalSize: Long) {}

                    override fun onFinish(file: File): Boolean {
                        return true
                    }

                    override fun onError(msg: String) {
                        XToast.toastShort(R.string.glass_update_download_fail)
                    }

                    override fun onInstallAppAndAppOnForeground(file: File): Boolean {
                        return false
                    }
                })
            }
        })
        updateDialog.show()
    }

    /**
     * 下载Apk
     */
    private fun downloadApk(
        fragmentActivity: FragmentActivity,
        apkFileUrl: String,
        downloadCallback: DownloadCallback
    ) { // 下载信息数据类UpdateAppBean
        val updateAppBean = UpdateAppBean()
        // 设置 apk 的下载地址
        updateAppBean.apkFileUrl = apkFileUrl
        // 设置 apk 的保存路径
        var path: String? = ""
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || !Environment.isExternalStorageRemovable()) {
            try {
                path = fragmentActivity.externalCacheDir?.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (TextUtils.isEmpty(path)) {
                path =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
            }
        } else {
            path = fragmentActivity.cacheDir.absolutePath
        }
        updateAppBean.targetPath = path
        // 设置下载请求类
        updateAppBean.httpManager = UpdateAppHttpUtil()
        UpdateAppManager.download(fragmentActivity, updateAppBean, downloadCallback)
    }
}