package com.rudy.tictactoy.tictactoy

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat

class Notifications
{
    var notifyTag = "new request"
    fun Notify(context:Context,message:String,number:Int)
    {
        var intent = Intent(context,LoginActivity::class.java)
        var builder = NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle("New Request")
                .setContentText(message)
                .setNumber(number)
                .setSmallIcon(R.drawable.tictactoe)
                .setContentIntent(PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)

        var nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(notifyTag, 0, builder.build())
        }
        else
        {
            nm.notify(notifyTag.hashCode(), builder.build())
        }
    }
}