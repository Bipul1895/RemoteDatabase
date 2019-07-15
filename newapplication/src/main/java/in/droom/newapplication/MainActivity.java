package in.droom.newapplication;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import in.droom.analyticslibrary.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    Button btn2;
    Button btn4;
    Button btn5;
    Button delbutton;
    SingletonClass obj;
    public final String CHANNEL_ID = "personal_notification";
    public static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obj=SingletonClass.getInstance();


        btn2=findViewById(R.id.button2);

        btn4=findViewById(R.id.button4);
        btn5=findViewById(R.id.btn_push_to_server);
        delbutton=findViewById(R.id.del_button);


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this, DisplayDatabase.class);
                startActivity(intent);

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.PushData();
            }
        });

        delbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.DeleteSyncedData();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotificationChannel();

                Intent markIntent = new Intent(getApplicationContext(), MarkAsRead.class);
                PendingIntent pendingIntentMark = PendingIntent.getBroadcast(getApplicationContext(), 0, markIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Intent delIntent = new Intent(getApplicationContext(), DelNotification.class);
                PendingIntent pendingIntentDel = PendingIntent.getBroadcast(getApplicationContext(), 0, delIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Intent landingIntent=new Intent(getApplicationContext(),LandingActivity.class);
                landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent landingPendingIntent =PendingIntent.getActivity(getApplicationContext(), 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);

                Intent yesIntent=new Intent(getApplicationContext(),YesActivity.class);
                yesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent yesPendingIntent =PendingIntent.getActivity(getApplicationContext(), 0, yesIntent, PendingIntent.FLAG_ONE_SHOT);

                Intent noIntent=new Intent(getApplicationContext(),NoActivity.class);
                noIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent noPendingIntent =PendingIntent.getActivity(getApplicationContext(), 0, noIntent, PendingIntent.FLAG_ONE_SHOT);


                NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_assignment_returned_black_24dp);
                builder.setContentTitle("Droom Notification");
                builder.setContentText("This is a droom notification");
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                //builder.setAutoCancel(true);
                builder.setContentIntent(landingPendingIntent);
                builder.setDeleteIntent(pendingIntentDel);
                //builder.setOngoing(true);

                builder.addAction(R.drawable.ic_assignment_returned_black_24dp, "Yes", yesPendingIntent);
                builder.addAction(R.drawable.ic_assignment_returned_black_24dp, "No", noPendingIntent);
                builder.addAction(R.drawable.ic_assignment_returned_black_24dp, "Mark As Read", pendingIntentMark);

                NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
                notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
            }
        });

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name="Personal notifications";
            String description="Include all personal notifications";

            int importance= NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel=new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }



}
