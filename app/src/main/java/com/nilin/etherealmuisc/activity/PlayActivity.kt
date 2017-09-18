package com.nilin.etherealmuisc.activity

import android.os.Bundle
import android.view.WindowManager
import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.include_play_bar.*

/**
* Created by liangd on 2017/9/19.
*/
class PlayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play)
//        if (!playService!!.isPlaying) {
//            iv_play_bar_play.setBackgroundResource(R.drawable.player_pause)
//        } else {
//            iv_play_bar_play.setBackgroundResource(R.drawable.player_start)
//        }
        iv_play_back.setOnClickListener { finish() }
        ib_play_contorl.setOnClickListener {
            if (playService!!.isPlaying) {
                playService!!.pause()
                ib_play_contorl.setBackgroundResource(R.drawable.player_start)
            } else {
                playService!!.play()
                ib_play_contorl.setBackgroundResource(R.drawable.player_pause)
            }
        }

    }

    private fun play() {
//        playService!!.play()
        if (!playService!!.isPlaying) {
            playService!!.play()
        } else {
            playService!!.pause()
        }
    }
    private fun pause() {
//        PlayService().pause()
    }

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

}
