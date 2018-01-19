package com.example.jhw_n_491.break_alarm;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // layout
    Button btn_registration;
    TextView view_text;
    ListView alarm_list;

    // Time Thread
    ProgressHandler handler;
    SimpleDateFormat mSdf;
    private String time;

    // Alarm Class add
    AlarmRegistration mRegistration;
    Calendar registration_time;
    private int alarm_count = 0;

    // BreakAlarmApplication
    BreakAlarmApplication app;
    private static final SimpleDateFormat cSdf = new SimpleDateFormat("HH:mm:ss");
    private ArrayList<String> alarm_list_strings;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Break Alarm");

        app = (BreakAlarmApplication) getApplicationContext();
        app.init();

        initUiComponents();

        runTime();

        // ListView에 출력 하기 위해 알람 목록을 문자열 리스트로 가져옵니다.
        alarm_list_strings = new ArrayList<String>();
        for (Calendar c: app.getAlarmList()) {
            alarm_list_strings.add(cSdf.format(c.getTime()));
        }

        // ListView에 알람 목록을 표시합니다.
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                alarm_list_strings);
        alarm_list.setAdapter(adapter);
        alarm_count = alarm_list.getCount();

        // Button Listener
        btn_registration.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DialogTimePicker();
            }
        });

        alarm_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String selected_item = (String)parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("알람 삭제");
                builder.setMessage("[" + selected_item + "] 알람을 삭제하시겠습니까?");
                builder.setPositiveButton("삭제",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // 알람 목록에서 선택된 알람을 삭제합니다.
                                mRegistration.AlarmCancel(position+1);
                                alarm_count--;
                                app.removeAlarm(selected_item);
                                alarm_list_strings.remove(selected_item);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });
    }

    private void DialogTimePicker()
    {
        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                // 다중 알람을 위하여 mPendingIntent 2번째 인자 값을 증가
                alarm_count++;
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, 0);

                app.setAlarm(cal);
                alarm_list_strings.add(cSdf.format(cal.getTime()));
                adapter.notifyDataSetChanged();

                // Alarm 등록
                mRegistration.AlarmStart(hourOfDay, minute, alarm_count);

            }
        };

        TimePickerDialog alert = new TimePickerDialog(this, mTimeSetListener,0,0,false);
        alert.show();
    }

    public void runTime()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try{
                        time = mSdf.format(new Date(System.currentTimeMillis()));

                        Message message = handler.obtainMessage();
                        handler.sendMessage(message);

                        Thread.sleep(1000);
                    }catch(InterruptedException ex){}
                }
            }
        });
        thread.start();
    }

    class ProgressHandler extends Handler{
        @Override
        public void handleMessage(Message msg)
        {
            view_text.setText(time);
        }
    }

    void initUiComponents()
    {
        btn_registration = (Button)findViewById(R.id.registration_btn);
        view_text = (TextView)findViewById(R.id.current_Time);
        alarm_list = (ListView)findViewById(R.id.alarm_list);

        mSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        registration_time = Calendar.getInstance();
        mRegistration = new AlarmRegistration(getApplicationContext(), registration_time);
        handler = new ProgressHandler();
    }

    public void onBackPressed()
    {
        // 뒤로 가기를 눌렀을때 알람 목록 데이타를 회수 합니다.
        app.clear();
        super.onBackPressed();
    }
}
