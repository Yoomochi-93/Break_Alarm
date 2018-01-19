package com.example.jhw_n_491.break_alarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class AlarmService extends Service {

    // Service, 진동 , 알람소리, 화면 켜짐 등이 울리게함
    Vibrator vibrator;
    Uri notification;
    Ringtone r;
    AudioManager audioManager;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // 진동
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{1000, 1000},0);


        // 알람음 설정을 위한 audioManager, 벨소리 +1 || 벨소리가 0인경우 강제로 소리를 키움
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE || audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
        {
            int volume = this.audioManager.getStreamVolume(AudioManager.STREAM_RING)+1;

            audioManager.setStreamVolume(AudioManager.STREAM_RING, volume, AudioManager.FLAG_PLAY_SOUND);
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        }

        startActivity(new Intent(this,AlarmPopup.class));

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        // 서비스가 종료될 때 실행
        vibrator.cancel();
        if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE || audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
        {
            r.stop();
        }
    }
}
