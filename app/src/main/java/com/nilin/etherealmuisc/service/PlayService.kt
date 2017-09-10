package com.nilin.etherealmuisc.service

import android.app.Service
import android.media.MediaPlayer
import android.content.Intent
import android.os.IBinder



/**
 * Created by nilin on 2017/9/10.
 */
class PlayService : Service(), MediaPlayer.OnCompletionListener {
    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCompletion(p0: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
