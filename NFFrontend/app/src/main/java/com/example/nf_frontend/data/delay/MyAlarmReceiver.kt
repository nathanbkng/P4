package com.example.nf_frontend.data.delay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.nf_frontend.data.notifications.Notification
import android.util.Log

class MyAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Récupérer le titre et la description depuis les extras de l'intent
        val code = intent.getStringExtra("CODE_EXTRA") ?: ""
        val quizzTitle = intent.getStringExtra("QUIZZTITLE_EXTRA") ?: ""
        val quizzDescription = intent.getStringExtra("QUIZZDESCRIPTION_EXTRA") ?: ""
        val quizzId = intent.getLongExtra("QUIZZID_EXTRA", -1.toLong())

        // Créer le canal de notification
        Notification.createNotificationChannel(context)

        Log.d("     MyAlarmReceiver     ", "Code = $code")
        Log.d("     MyAlarmReceiver     ", "Quizz Title = $quizzTitle")
        Log.d("     MyAlarmReceiver     ", "QuizzId $quizzId")
        Notification.sendNotification(context,code, quizzTitle, quizzDescription, quizzId)
        Log.d("     MyAlarmReceiver     ", "Notification sent")
    }
}
