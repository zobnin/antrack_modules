package org.antrack.app.modules.alarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modules.Template;

import java.util.Timer;
import java.util.TimerTask;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Playing the alarm sound";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "alarm";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        final MediaPlayer mp = new MediaPlayer();
        final AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        final int volume = am.getStreamVolume(AudioManager.STREAM_MUSIC);

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);

        try {
            mp.setDataSource(context.getApplicationInfo().dataDir + "/alarm.ogg");
            mp.setLooping(true);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mp.stop();
                mp.release();
                am.setStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        volume,
                        0);
            }
        }, 10_000);

        return DONE;
    }
}
