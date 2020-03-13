package com.example.quizofy

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.service.notification.NotificationListenerService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val alarmservice=Intent(context,NotificationListenerService::class.java)
        alarmservice.putExtra("reason",intent.getStringExtra("reason"))
        alarmservice.putExtra("timestamp",intent.getLongExtra("timestamp",0))
        context.startService(alarmservice)
    }

}