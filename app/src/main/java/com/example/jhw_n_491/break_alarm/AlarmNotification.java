package com.example.jhw_n_491.break_alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmNotification{

    Context context;
    Calendar recv_cal;
    Date date;
    SimpleDateFormat sdf;
    String[] Events;
    private int alarm_count=0;

    AlarmNotification(Context mcontext, Calendar mCalendar, int mCount)
    {
        context = mcontext;
        recv_cal = mCalendar;
        alarm_count = mCount;
    }

    public void ExpandedlayoutNotification()
    {
        NotificationCompat.Builder mBuilder = createNotification();
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        date = recv_cal.getTime();
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Events = new String[2];
        Events[0] = alarm_count+ "번째 알람이 등록되었습니다.";
        Events[1] = sdf.format(date);

        inboxStyle.setBigContentTitle("알람이 등록되었습니다.");
        inboxStyle.setSummaryText("Alarm Message");

        for(String str : Events)
        {
            inboxStyle.addLine(str);
        }

        mBuilder.setStyle(inboxStyle);


        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    // 알림 빌드
    private NotificationCompat.Builder createNotification()
    {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle("알림이 등록되었습니다.")
                .setContentText(alarm_count + " 번 알람")
                .setSmallIcon(R.mipmap.ic_launcher/*스와이프 전 아이콘*/)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        return builder;
    }
}
