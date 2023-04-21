package org.antrack.app.modules.audio;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class Media {
    private static final String TAG = "MediaTools";
    private static MediaRecorder recorder = null;

    public static void recordAudio(String file, final int time) {
        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(file);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.start();

        Thread timer = new Thread(() -> {
            try {
                Thread.sleep(time * 1000L);
            } catch (InterruptedException e) {
                Log.d(TAG, "timer interrupted");
            } finally {
                recorder.stop();
                recorder.release();
            }
        });

        timer.start();
    }
}
