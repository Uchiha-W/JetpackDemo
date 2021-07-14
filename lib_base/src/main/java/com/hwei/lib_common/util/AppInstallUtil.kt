package com.hwei.lib_common.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.FileProvider
import com.hwei.lib_common.BaseApplication
import java.io.File


object AppInstallUtil {

    const val UNKNOW_SOURCE = 10086

    fun installApk(context: Context, apkPath: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canInstall = context.packageManager.canRequestPackageInstalls()
            if (canInstall) {
                installApkInternal(context, apkPath)
            } else {
                //"安装应用需要打开安装未知来源应用权限，请去设置中开启权限"
                val packageUri = Uri.parse("package:" + BaseApplication.context.packageName)
                val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri)
                (context as Activity).startActivityForResult(intent, UNKNOW_SOURCE)
            }
        } else {
            installApkInternal(context, apkPath)
        }
    }

    private fun installApkInternal(context: Context, downloadApk: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val file = File(downloadApk)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val apkUri = FileProvider.getUriForFile(
                context,
                BaseApplication.context.packageName + ".fileprovider",
                file
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val uri = Uri.fromFile(file)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }
}