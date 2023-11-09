package uz.gita.todoapp_mehriddin

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import uz.gita.todoapp_mehriddin.util.checkPermissions
import uz.gita.todoapp_mehriddin.util.logger

class MyWorker(private val context: Context,workerParameters: WorkerParameters):CoroutineWorker(context,workerParameters) {


    companion object{
        const val CHANNEL_ID = "MCHANNEL"
    }

    override suspend fun doWork(): Result {

        val id = inputData.getLong("NotificationID",0L)
        val media = MediaPlayer.create(context,R.raw.sound)
        media.start()

       // createNotification()

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID,"ToDo",importance)

            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)

        }

        val notificationbuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Task")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText("Do task now !")
            .setPriority(PRIORITY_HIGH)

//        NotificationManagerCompat.from(context).notify(id.toInt(),notificationbuilder.build())

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(id.toInt(),notificationbuilder.build())


        return Result.success()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun createNotification(){

    }
}