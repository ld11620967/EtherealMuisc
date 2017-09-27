package com.nilin.etherealmuisc.activity

import android.os.Bundle
import android.util.Log
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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play)

        iv_play_back.setOnClickListener { finish() }

        ib_play_contorl.setOnClickListener {
            if (playService!!.isPlaying) {
                playService!!.pause()
                ib_play_contorl.setBackgroundResource(R.drawable.player_start)
            } else {
                playService!!.start()
                ib_play_contorl.setBackgroundResource(R.drawable.player_pause)
            }
        }

//        if (playService == null) {
//            Log.i("3333333333333333","null")
//            return
//        }else
//            if(playService!!.isPlaying) {
//            Log.i("2222222222222","stop")
//            iv_play_bar_play.setBackgroundResource(R.drawable.player_start)
//        } else {
//            Log.i("111111111111","start")
//            iv_play_bar_play.setBackgroundResource(R.drawable.player_pause)
//        }

    }

    fun player_start() {
        iv_play_bar_play.setBackgroundResource(R.drawable.player_start)
    }

    fun player_pause() {
        iv_play_bar_play.setBackgroundResource(R.drawable.player_pause)
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
