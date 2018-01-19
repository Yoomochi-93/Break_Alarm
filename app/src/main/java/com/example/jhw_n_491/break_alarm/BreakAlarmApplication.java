package com.example.jhw_n_491.break_alarm;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;

public class BreakAlarmApplication extends Application{

    private ArrayList<Calendar> alarm_list;
    private SharedPreferences sp;
    private static final String SP_NAME = "Break_Alarm";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    AlarmRegistration exist_alarm;
    private int alarm_count = 0;

    public void init()
    {
        sp = getSharedPreferences("Break_Alarm", Activity.MODE_PRIVATE);
        alarm_list = new ArrayList<Calendar>();
        getSavedList();
    }

    private void getSavedList() {
        // 기존 등록된 알람 목록을 읽어옵니다.
        String str_val = sp.getString("Alarm_List", null);

        if (str_val != null) {
            String[] str_val_list = str_val.split("/");

            for (String s : str_val_list) {
                Calendar cal = Calendar.getInstance();
                exist_alarm = new AlarmRegistration(getApplicationContext(), cal);
                try {
                    cal.setTime(sdf.parse(s));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                alarm_count++;
                String Sample[] = s.split(":");
                exist_alarm.Exist_AlarmList(Integer.parseInt(Sample[0]),Integer.parseInt(Sample[1]),alarm_count);

                alarm_list.add(cal);
            }
        }
    }

    private void updateAlarmList()
    {
        SharedPreferences.Editor editor = sp.edit();
        String saved_str = "";

        if (alarm_list.size() > 0) {
            for (Calendar cal : alarm_list) {
                saved_str += sdf.format(cal.getTime());
                saved_str += "/";
            }
            editor.putString("Alarm_List", saved_str);
            editor.apply();
        } else {
            editor.clear();
            editor.commit();
        }
    }

    public ArrayList<Calendar> getAlarmList()
    {
        return alarm_list;
    }

    public void setAlarm(Calendar cal)
    {
        alarm_list.add(cal);
        updateAlarmList();
    }

    public void removeAlarm(String t)
    {
        String str_val = sp.getString("Alarm_List", null);

        if (str_val != null) {
            String[] str_val_list = str_val.split("/");
            ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(str_val_list));
            stringList.remove(t);

            alarm_list.clear();
            if (stringList.size() > 0) {
                for (String s : stringList) {
                    Calendar cal = Calendar.getInstance();
                    try {
                        cal.setTime(sdf.parse(s));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    alarm_list.add(cal);
                }
            } else {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
            }
        }
        alarm_count--;
        updateAlarmList();
    }

    public void clear()
    {
        alarm_list.clear();
    }


}
