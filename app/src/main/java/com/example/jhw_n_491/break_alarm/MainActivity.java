package com.example.jhw_n_491.break_alarm;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button registration_btn;
    ListView alarm_list;
    BreakAlarmApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (BreakAlarmApplication) getApplicationContext();

        initUiComponents();

        registration_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DialogTimePicker();
            }
        });
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
                app.setAlarm(cal);
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
