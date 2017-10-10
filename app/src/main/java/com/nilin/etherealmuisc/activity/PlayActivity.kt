package com.nilin.etherealmuisc.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.WindowManager
import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.include_play_bar.*
import android.widget.SeekBar
import com.nilin.etherealmuisc.utils.MediaUtils


/**
 * Created by liangd on 2017/9/19.
 */
class PlayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play)

        iv_play_back.setOnClickListener { finish() }

        ib_play_contorl.setOnClickListener {
            if (playService!!.isPlaying) {
                playService!!.pause()
                ib_play_contorl.isSelected = false
            } else {
                playService!!.start()
                ib_play_contorl.isSelected = true
            }
        }


//            if (playService == null) {
//            Log.i("3333333333333333","null")
//            return
//        }else
//            if(playService!!.isPlaying) {
//            Log.i("2222222222222","stop")
//                ib_play_contorl.setBackgroundResource(R.drawable.player_pause)
//        } else {
//            Log.i("111111111111","start")
//                ib_play_contorl.setBackgroundResource(R.drawable.player_start)
//        }




    }

    fun player_start() {
        iv_play_bar_play.setBackgroundResource(R.drawable.player_start)
    }

    fun player_pause() {
        iv_play_bar_play.setBackgroundResource(R.drawable.player_pause)
    }



    //Handler用于更新已经播放时间
    private val myHandler: MyHandler? = null

    fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            playService!!.seekTo(progress)
        }
    }

    fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    fun onStopTrackingTouch(seekBar: SeekBar) {

    }

    internal class MyHandler(private val playActivity: PlayActivity?) : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (playActivity != null) {
                when (msg.what) {
                    UPDATE_TIME//更新时间(已经播放时间)
                    -> playActivity.MusicStatus.setText(MediaUtils.formatTime(msg.obj as Long))
//                    UPDATE_LRC -> playActivity.lrcView.seekLrcToTime(msg.obj as Int)
                    else -> {
                    }
                }
            }
        }
    }

//    fun changeF1(song: String, songer: String, play: Boolean) {
////            tv_play_bar_title.text = song
////            tv_play_bar_artist.text = songer
//        if (play) {
//            ib_play_contorl.isSelected = true
//        } else {
//            ib_play_contorl.isSelected = false
//        }
//
//    }

//    class MusicBroadcastReceiver : BroadcastReceiver() {
//
//        override fun onReceive(context: Context, intent: Intent) {
//            val song = intent.getStringExtra("song")
//            val songer = intent.getStringExtra("songer")
//            val play = intent.getBooleanExtra("play", false)
//            (context as PlayActivity).changeF1(song, songer, play)
//        }
//
//    }

    override fun onResume() {
        super.onResume()
        bindPlayService()//绑定服务
    }

    override fun onPause() {
        super.onPause()
        unbindPlayService()//解绑服务
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindPlayService()//解绑服务
    }

    companion object {
        private val UPDATE_TIME = 0x10    //更新播放事件的标记
        private val UPDATE_LRC = 0x20     //更新播放事件的标记
    }

}
