package nivida.com.amulyamica;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Nivida new on 17-Sep-16.
 */
public class AmulyaFMService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        //Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.e(TAG, "DATA Message Body: " + remoteMessage.getData().toString());

        notifyUser(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));

        //Calling method to generate notification
        //showNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("title"));
    }



    //This method is only generating push notification
    //It is same as we did in earlier posts

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void notifyUser(String title, String message){
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fromNotification",true);
        intent.putExtra("message",message);
        intent.putExtra("title",title);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.icon_amulya_mica);
        Notification mNotification = new Notification.Builder(this)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(message))
                .setContentTitle(title)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentText(message)
                .setLargeIcon(icon)
                .setSmallIcon(R.drawable.icon_amulya_mica)
                .setContentIntent(pIntent)
                .setSound(soundUri)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotification);
    }
}