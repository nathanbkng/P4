@file:Suppress("UnusedImport")

package com.example.nf_frontend.data.notifications

import com.example.nf_frontend.MainActivity
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.nf_frontend.R
import java.text.SimpleDateFormat

class Notification {
    companion object {
        private const val channelId = "Channel101"

        @SuppressLint("MissingInflatedId")
        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Canal : Rappel Quizz"
                val descriptionText = "Ce canal de notification sert à rappeler les utilisateur.trices de faire le quizz."
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel =
                    NotificationChannel(channelId, name, importance).apply {
                        description = descriptionText
                    }
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }


        fun sendNotification(context: Context, code: String, quizzTitle: String, quizzDescription: String, quizzId:Long) {
            val intent = Intent(context, MainActivity::class.java)
            val notificationId = System.currentTimeMillis().toInt() // Utilisation d'un identifiant unique
            val requestCode = notificationId
            // Ajouter les données comme extras à l'intent
            intent.putExtra("notification_data", Bundle().apply {
                putString("code", code)
                putString("quizzTitle", quizzTitle)
                putString("quizzDescription", quizzDescription)
                putLong("quizzId", quizzId)
            })
            val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Il est temps de passer un Quizz")
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .addLine("Cours : $code")
                        .addLine("Quizz : $quizzTitle")
                        .addLine("Description : $quizzDescription")
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(Color.BLACK)
                .setContentIntent(pendingIntent) // Associer le PendingIntent à la notification
                .setAutoCancel(true) // Ferme automatiquement la notification lorsqu'elle est cliquée
            val notificationManagerCompat = NotificationManagerCompat.from(context)

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            notificationManagerCompat.notify(notificationId, builder.build())
        }

    }
}