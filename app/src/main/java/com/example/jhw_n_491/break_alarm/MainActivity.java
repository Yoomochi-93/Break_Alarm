package com.example.jhw_n_491.break_alarm;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button registration_btn;
    ListView alarm_list;
    BreakAlarmApplication app;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private ArrayList<String> alarm_list_strings;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (BreakAlarmApplication) getApplicationContext();
        app.init();

        initUiComponents();

        // ListView에 출력 하기 위해 알람 목록을 문자열 리스트로 가져옵니다.
        alarm_list_strings = new ArrayList<String>();
        for (Calendar c: app.getAlarmList()) {
            alarm_list_strings.add(sdf.format(c.getTime()));
        }

        // ListView에 알람 목록을 표시합니다.
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                                             alarm_list_strings);
        alarm_list.setAdapter(adapter);

        registration_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DialogTimePicker();
            }
        });

        alarm_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selected_item = (String)parent.getItemAtPosition(position);
                Log.d("BreakAlarm", "ListView:" + selected_item);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("알람 삭제");
                builder.setMessage("[" + selected_item + "] 알람을 삭제하시겠습니까?");
                builder.setPositiveButton("삭제",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // 알람 목록에서 선택된 알람을 삭제합니다.
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
                // Calendar 객체를 생성하여, UI에서 설정한 시간과 분으로 설정하여 전역 자료 구조에 저장한다.
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, 0);

                // 설정한 알람 시간을 저장합니다.
                app.setAlarm(cal);
                alarm_list_strings.add(sdf.format(cal.getTime()));
                adapter.notifyDataSetChanged();
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

    public void onBackPressed()
    {
        // 뒤로 가기를 눌렀을때 알람 목록 데이타를 회수 합니다.
        app.clear();
        super.onBackPressed();
    }
}
