package com.example.gestioncitasparadise;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class myFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static int notificationId = 0;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        if (message.getNotification() != null) {
            String title = message.getNotification().getTitle();
            String text = message.getNotification().getBody();
            String CHANNEL_ID = "MESSAGE";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID,
                        "Message Notification",
                        NotificationManager.IMPORTANCE_HIGH
                );
                getSystemService(NotificationManager.class).createNotificationChannel(channel);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true);

                NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                bigTextStyle.bigText(text);

                notificationBuilder.setStyle(bigTextStyle);

                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                // Asignar un ID único a cada notificación
                int uniqueNotificationId = notificationId++;
                NotificationManagerCompat.from(this).notify(uniqueNotificationId, notificationBuilder.build());
            }


        }
        super.onMessageReceived(message);
    }
}
