package com.example.jhw_n_491.break_alarm;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button registration_btn;
    ListView alarm_list;
    BreakAlarmApplication app;
    private SharedPreferences sp;
    private static final String SP_NAME = "Break_Alarm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (BreakAlarmApplication) getApplicationContext();
        sp = getSharedPreferences("Break_Alarm", Activity.MODE_PRIVATE);

        getAlarmList();
        initUiComponents();

        registration_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DialogTimePicker();
            }
        });
    }

    private void getAlarmList() {
        String str_val = sp.getString("Alarm_List", null);
        String[] str_val_list = str_val.split("/");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Log.d("BreakAlarm", "getAlarmList:" + str_val);

        for (String s: str_val_list) {
            Calendar cal = Calendar.getInstance();
            Log.d("BreakAlarm", "for:" + s);
            try {
                cal.setTime(sdf.parse(s));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            app.setAlarm(cal);
        }
    }

    private void updateAlarmList()
    {
        SharedPreferences.Editor editor = sp.edit();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String saved_str = "";

        for (Calendar cal : app.getAlarmList()) {
            saved_str += sdf.format(cal.getTime());
            saved_str += "/";
            Log.d("BreakAlarm", "puAlarmList for: " + saved_str);
        }

        editor.putString("Alarm_List", saved_str);
        Log.d("BreakAlarm", "puAlarmList: " + saved_str);
        editor.apply();
    }

    private void DialogTimePicker()
    {
        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Calendar 객체를 생성하여, UI에서 설정한 시간과 분으로 설정하여 전역 자료 구조에 저장한다.
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR, hourOfDay);
                cal.add(Calendar.MINUTE, minute);
                cal.add(Calendar.SECOND, 0);

                app.setAlarm(cal);
                updateAlarmList();
            }
        };

        TimePickerDialog alert = new TimePickerDialog(this, mTimeSetListener,0,0,false);
        alert.show();
    }

    void initUiComponents()
    {
        registration_btn = (Button)findViewById(R.id.registration_btn);
        alarm_list = (ListView)findViewById(R.id.alarm_list);
    }
}
