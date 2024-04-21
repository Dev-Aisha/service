package com.example.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


const val START_SERVICE = "start_service_action"

const val STOP_SERVICE = "stop_service_action"
const val NOTIFICATION_CHANNEL = 102

class TestService :Service(){
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action != null && intent?.action == START_SERVICE){
            stopSelf()
        }
        sendNotification()
        return START_STICKY
    }

    fun sendNotification(){
        val notification = NotificationCompat.Builder(this, "$NOTIFICATION_CHANNEL")
        notification.setContentTitle("Test Service")
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
        notification.setContentText("Running")
        notification.setAutoCancel(true)
        notification.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)


        val pendingIntent =
            PendingIntent.getActivity(
                this, 0, Intent(this, MainActivity::class.java)
                , PendingIntent.FLAG_IMMUTABLE
            )

        notification.setContentIntent(pendingIntent)

        notification.priority= NotificationCompat.PRIORITY_LOW
        val notificationManger=
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            notificationManger.createNotificationChannel(
                NotificationChannel(
                    "$NOTIFICATION_CHANNEL",
                    "Channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        notificationManger.notify(NOTIFICATION_CHANNEL, notification.build())
        startForeground(NOTIFICATION_CHANNEL, notification.build())




    }


}