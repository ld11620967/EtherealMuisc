package com.nilin.etherealmuisc.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nilin.etherealmuisc.R

import kotlinx.android.synthetic.main.include_app_bar.*


class LocalMusicActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_music)

        toolbar.setTitle("扫描音乐")
        toolbar.setNavigationOnClickListener(this);
    }

    override fun onClick(p0: View?) {
        finish()
    }

}
