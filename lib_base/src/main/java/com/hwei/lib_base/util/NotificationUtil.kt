package com.hwei.lib_base.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hwei.lib_base.BaseApplication
import com.hwei.lib_base.R
import com.hwei.lib_base.camera.CameraActivity

object NotificationUtil {

    private val last = "107"
    private val CHANNEL_ID = "108"


    fun gotoSetting(context: Context) {
        //通知权限
        val enable = NotificationManagerCompat.from(context).areNotificationsEnabled()
        if (!enable) {
            context.startActivity(Intent().apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    putExtra(Settings.EXTRA_CHANNEL_ID, context.applicationInfo.uid)
                }
                putExtra("app_packet", context.packageName)
                putExtra("app_uid", context.applicationInfo.uid)
            })
        }
    }


    fun show(context: Context) {

        val intent = Intent(context, CameraActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        createNotificationChannel()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_back)
            .setContentTitle("title")
            .setContentText("content")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOnlyAlertOnce(false)
            .build()

        // 消息提示音
//        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        RingtoneManager.getRingtone(context, uri).play()
//
//        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        // 震动
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            vibrator.vibrate(
//                VibrationEffect.createOneShot(
//                    400,
//                    VibrationEffect.DEFAULT_AMPLITUDE
//                )
//            )
//        }
//        NotificationManagerCompat.from(context).notify(Random.nextInt(), notification)
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            channel.enableLights(true)
            channel.enableVibration(true)
            // Register the channel with the system
            val notificationManager: NotificationManager =
                BaseApplication.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.deleteNotificationChannel(last)
            notificationManager.createNotificationChannel(channel)
        }
    }

}