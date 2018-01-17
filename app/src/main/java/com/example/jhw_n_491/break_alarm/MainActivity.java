package com.example.jhw_n_491.break_alarm;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button registration_btn;
    ListView alarm_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Toast.makeText(getApplicationContext(), "Time is="+ hourOfDay + ":" + minute,Toast.LENGTH_SHORT).show();
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
