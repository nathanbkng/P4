package com.example.nf_frontend.data.delay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.nf_frontend.data.notifications.Notification

class MyAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Créer le canal de notification
        Notification.createNotificationChannel(context)

        // Envoyer la notification
        Notification.sendNotification(context)
    }
}
