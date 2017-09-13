package com.nilin.etherealmuisc.activity

import android.os.Bundle
import android.view.WindowManager
import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.activity_play.*


class PlayActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play)
        iv_play_back.setOnClickListener { finish() }
        ib_play_start.setOnClickListener { play() }
        ib_play_pause.setOnClickListener { pause() }
    }

    private fun play() {
        playService!!.start()

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
