package com.nilin.etherealmuisc.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.service.PlayService
import kotlinx.android.synthetic.main.activity_play.*


class PlayActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play)
        iv_play_back.setOnClickListener { finish() }
        ib_play_start.setOnClickListener { play() }
        ib_play_pause.setOnClickListener { pause() }
    }

    private fun play() {
        PlayService().start()

    }
    private fun pause() {
//        PlayService().pause()
    }
}
