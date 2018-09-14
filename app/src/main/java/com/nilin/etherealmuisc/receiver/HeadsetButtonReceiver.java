package com.nilin.etherealmuisc.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.nilin.etherealmuisc.service.PlayService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jerre on 2018/3/1.
 */

public class HeadsetButtonReceiver extends BroadcastReceiver{
    private Context context;
    private Timer timer = new Timer();
    private static int clickCount;
    private static onHeadsetListener headsetListener;
    protected PlayService playService;


    public HeadsetButtonReceiver(){
        super();
    }

    public HeadsetButtonReceiver(Context ctx){
        super();
        context = ctx;
        headsetListener = null;
        registerHeadsetReceiver();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            KeyEvent keyEvent = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_HEADSETHOOK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                clickCount = clickCount + 1;
                if(clickCount == 1){
                    HeadsetTimerTask headsetTimerTask = new HeadsetTimerTask();
                    timer.schedule(headsetTimerTask,1000);
                }
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_MEDIA_NEXT) {
                handler.sendEmptyMessage(2);
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_MEDIA_PREVIOUS) {
                handler.sendEmptyMessage(3);
            }
        }
    }

    class HeadsetTimerTask extends TimerTask {
        @Override
        public void run() {
            try{
                if(clickCount==1){
                    handler.sendEmptyMessage(1);
                }else if(clickCount==2){
                    handler.sendEmptyMessage(2);
                }else if(clickCount>=3){
                    handler.sendEmptyMessage(3);
                }
                clickCount=0;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.what == 1) {
                    Log.i("11111111111111","111111111111111111");
//                    headsetListener.playOrPause();
                    playService.pause();
                }else if(msg.what == 2){
                    Log.i("2222222222222","2222222222222");
//                    headsetListener.playNext();
                    playService.next();
                }else if(msg.what == 3){
                    Log.i("3333333333333","33333333333333");
//                    headsetListener.playPrevious();
                    playService.previous();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    interface onHeadsetListener{
        void playOrPause();
        void playNext();
        void playPrevious();
    }

    public void setOnHeadsetListener(onHeadsetListener newHeadsetListener){
        headsetListener = newHeadsetListener;
    }

    public void registerHeadsetReceiver() {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        ComponentName name = new ComponentName(context.getPackageName(), HeadsetButtonReceiver.class.getName());
        audioManager.registerMediaButtonEventReceiver(name);
    }

    public void unregisterHeadsetReceiver(){
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        ComponentName name = new ComponentName(context.getPackageName(), HeadsetButtonReceiver.class.getName());
        audioManager.unregisterMediaButtonEventReceiver(name);
    }
}
