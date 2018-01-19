package com.example.jhw_n_491.break_alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class AlarmReceiver extends BroadcastReceiver {

    // 화면설정
    private static PowerManager.WakeLock sCpuWakeLock;

    @Override
    public void onReceive(Context context, Intent intent) {

        // BroadCast Receiver 클래스
        // 이벤트가 발생하면 이곳에서 역활 수행
        // Service 호출

        Intent mServiceintent = new Intent(context,AlarmService.class);
        context.startService(mServiceintent);

        if (sCpuWakeLock != null) {
            return;
        }
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        sCpuWakeLock = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, "hi");

        sCpuWakeLock.acquire();

        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }
}
