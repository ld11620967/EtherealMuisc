package com.nilin.etherealmuisc.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.nilin.etherealmuisc.R

class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play)
    }
}
