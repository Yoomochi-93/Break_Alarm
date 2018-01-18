package com.example.jhw_n_491.break_alarm;

import android.app.Application;

import java.util.Calendar;
import java.util.ArrayList;

public class BreakAlarmApplication extends Application{
    private ArrayList<Calendar> alarm_list;

    BreakAlarmApplication()
    {
        alarm_list = new ArrayList<Calendar>();
    }

    public ArrayList<Calendar> getAlarmList()
    {
        return alarm_list;
    }

    public void setAlarm(Calendar cal)
    {
        alarm_list.add(cal);
    }
}
