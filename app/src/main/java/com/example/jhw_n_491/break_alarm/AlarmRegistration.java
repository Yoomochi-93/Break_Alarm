package com.example.jhw_n_491.break_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmRegistration {

    AlarmManager mAlarmManager;
    AlarmManager new_AlarmManager;
    PendingIntent mPendingIntent;
    AlarmNotification mNotification;
    Calendar mCalendar;
    Context mContext;
    Intent mAlarmIntent;

    AlarmRegistration(Context recv_context, Calendar recv_calendar)
    {
        initUiComponents();
        mContext = recv_context;
        mCalendar = recv_calendar;
    }

    public void AlarmStart(int alarm_hour, int alarm_minute, int alarm_count) {
        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        // 현재시간 & 알람 등록 시간 저장할 Calendar 변수 설정
        int current_hour = (mCalendar.get(Calendar.HOUR) * 60);
        int current_minute = (mCalendar.get(Calendar.MINUTE));

        mCalendar.set(Calendar.HOUR, alarm_hour);
        mCalendar.set(Calendar.MINUTE, alarm_minute);
        mCalendar.set(Calendar.SECOND, 0);

        //Notication 호출
        mNotification = new AlarmNotification(mContext.getApplicationContext(), mCalendar, alarm_count);
        mNotification.ExpandedlayoutNotification();

        // BroadCast로 전달 및 AlarmManager에게 실행을 부탁한  mPendingIntent 생성
        mPendingIntent = PendingIntent.getBroadcast(
                mContext.getApplicationContext(),
                alarm_count,
                mAlarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // 현재 시간 보다 늦은경우 현재 날 +1
        if ((current_hour + current_minute) >= ((alarm_hour * 60) + alarm_minute)) {
            mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.get(Calendar.DAY_OF_MONTH) + 1);
            mAlarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    mCalendar.getTimeInMillis(),
                    mPendingIntent
            );
        } else {
            mAlarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    mCalendar.getTimeInMillis(),
                    mPendingIntent
            );
        }
    }

    public void Exist_AlarmList(int alarm_hour, int alarm_minute, int alarm_count)
    {
        new_AlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        // 현재시간 & 알람 등록 시간 저장할 Calendar 변수 설정
        Calendar new_cal = Calendar.getInstance();
        int current_hour = (new_cal.get(Calendar.HOUR) * 60);
        int current_minute = (new_cal.get(Calendar.MINUTE));

        new_cal.set(Calendar.HOUR, alarm_hour);
        new_cal.set(Calendar.MINUTE, alarm_minute);
        new_cal.set(Calendar.SECOND, 0);

        //Notication 호출
        mNotification = new AlarmNotification(mContext.getApplicationContext(), new_cal, alarm_count);
        mNotification.ExpandedlayoutNotification();

        // BroadCast로 전달 및 AlarmManager에게 실행을 부탁한  mPendingIntent 생성
        mPendingIntent = PendingIntent.getBroadcast(
                mContext.getApplicationContext(),
                alarm_count,
                mAlarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // 현재 시간 보다 늦은경우 현재 날 +1
        if ((current_hour + current_minute) >= ((alarm_hour * 60) + alarm_minute)) {
            new_cal.set(Calendar.DAY_OF_MONTH, new_cal.get(Calendar.DAY_OF_MONTH) + 1);

            new_AlarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    new_cal.getTimeInMillis(),
                    mPendingIntent
            );
        } else {
            new_AlarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    new_cal.getTimeInMillis(),
                    mPendingIntent
            );
        }
    }

    void AlarmCancel(int alarm_count)
    {
        // 알람 삭제
        PendingIntent sender = PendingIntent.getBroadcast(
                mContext.getApplicationContext(),
                alarm_count,
                mAlarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager cancel_alarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        cancel_alarm.cancel(sender);
    }

    void initUiComponents()
    {
        mAlarmIntent = new Intent("com.example.jhw_n_491.break_alarm.ALARM_START");
    }
}
