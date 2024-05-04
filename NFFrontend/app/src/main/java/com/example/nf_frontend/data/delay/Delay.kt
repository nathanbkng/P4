package com.example.nf_frontend.data.delay

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*


fun setInexactAlarm(context: Context, delayDays: Int, hourOfDay: Int, minute: Int, code: String, quizzTitle: String, quizzDescription: String, quizzId: Long) {
    Log.d("setInexactAlarm", "On entre dans la fonction : setInexactAlarm")
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val alarmIntent = Intent(context, MyAlarmReceiver::class.java)

    alarmIntent.putExtra("CODE_EXTRA", code)
    alarmIntent.putExtra("QUIZZTITLE_EXTRA", quizzTitle)
    alarmIntent.putExtra("QUIZZDESCRIPTION_EXTRA", quizzDescription)
    alarmIntent.putExtra("QUIZZID_EXTRA", quizzId)

    val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    // Calculer le temps exact où tu veux que l'alarme se déclenche (3 jours plus tard à 8h00)
    val triggerTime = calculateTriggerTime(delayDays, hourOfDay, minute)
    Log.d("CALENDRIER ###############", "####   Temps calculé : $triggerTime    ####")

    // Configuration de l'alarme inexacte
    alarmManager.set(
        AlarmManager.RTC_WAKEUP,
        triggerTime,
        pendingIntent
    )

}


fun calculateTriggerTime(delayDays: Int, hourOfDay: Int, minute: Int): Long {
    // Mettre le bon fuseau horaire
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"))

    // Créer un nouvel objet Calendar pour l'heure de déclenchement de l'alarme sur le fuseau horaire de Paris
    val triggerCalendar = Calendar.getInstance()

    // Ajouter le délai en jours
    triggerCalendar.add(Calendar.DAY_OF_YEAR, delayDays)
    // Définir l'heure et les minutes de déclenchement de l'alarme
    triggerCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
    triggerCalendar.set(Calendar.MINUTE, minute)
    triggerCalendar.set(Calendar.SECOND, 0)
    triggerCalendar.set(Calendar.MILLISECOND, 0)

    // Calculer la différence en millisecondes entre l'heure actuelle et l'heure de déclenchement de l'alarme
    return triggerCalendar.timeInMillis
}

