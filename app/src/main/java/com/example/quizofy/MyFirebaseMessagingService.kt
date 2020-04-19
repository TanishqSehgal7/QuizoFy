package com.example.quizofy

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.media.SoundPool
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG= String::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG,"From ${remoteMessage?.from}")

        remoteMessage?.data?.isNotEmpty()?.let {

            if (!remoteMessage.data.isNullOrEmpty()){
                val msg : String=remoteMessage.data.get("message").toString()
            }
        }

       remoteMessage?.notification.let {
           sendNotification(remoteMessage.notification?.body)
       }
    }

    private fun sendNotification(messageBody:String?){
        val intent=Intent(this,MainActivity::class.java)
        val addFlags = intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
        val channelId=getString(R.string.notification_channel_description)
        val defaultSoundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder=NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(getString(R.string.fcm_fallback_notification_channel_label))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            val channel=NotificationChannel(channelId,"Channel title",NotificationManager.IMPORTANCE_HIGH)
            channel.description="QuizoFy Notification Channel"
            channel.vibrationPattern= longArrayOf(0,1000,500,500)
            channel.enableVibration(true)
            channel.enableLights(true)
            channel.lightColor=Color.GREEN
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0,notificationBuilder.build())
    }
}