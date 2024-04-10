@file:Suppress("UnusedImport")

package com.example.nf_frontend.data.notifications


import android.Manifest
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

class Notification :  AppCompatActivity() {
    private val channelId = "Channel101"
    private val notificationId = 101  // peut être défini dans sendNotification()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif)

        // activity_main et notifyButton devrait être défini dans un fichier xml (contenant les bouttons correspondants ?)

        val showNotificationButton = findViewById<Button>(R.id.notifyButton)
        showNotificationButton.setOnClickListener {
            createNotificationChannel()
            sendNotification()
        }
    }

    // chaque notification doit être assignée à un canal
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal : Rappel Quizz"
            val descriptionText = "TCe canal de notification sert à rappeler les utilisateur.trices de faire le quizz."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // val notificationManager: NotificationManager
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {

        // val notificationId = SimpleDateFormat

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)  // l'image de notification
            .setContentTitle("Sample Notification")
            .setContentText("This is a sample notification.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        val notificationManagerCompat = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(
                this,
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
            return
        }
        notificationManagerCompat.notify(notificationId, builder.build())


    }


}