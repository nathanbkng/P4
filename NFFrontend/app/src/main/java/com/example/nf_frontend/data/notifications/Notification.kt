@file:Suppress("UnusedImport")

package com.example.nf_frontend.data.notifications


import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.nf_frontend.R
import java.text.SimpleDateFormat

class Notification {
    companion object {
        private const val channelId = "Channel10  1"
        private const val notificationId = 101

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

        fun sendNotification(context: Context) {
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Sample Notification")
                .setContentText("This is a sample notification.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            val notificationManagerCompat = NotificationManagerCompat.from(context)

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            notificationManagerCompat.notify(notificationId, builder.build())
        }

    }
}