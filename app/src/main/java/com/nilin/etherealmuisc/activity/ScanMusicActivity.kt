package com.nilin.etherealmuisc.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.activity_scan_music.*
import kotlinx.android.synthetic.main.include_app_bar.*


/**
* Created by liangd on 2017/9/19.
*/
class ScanMusicActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_music)

        toolbar.setTitle("扫描音乐")
        toolbar.setNavigationOnClickListener(this)

        scan_music.setOnClickListener {  }
    }

    override fun onClick(p0: View?) {
        finish()
    }

}
