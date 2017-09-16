package com.nilin.etherealmuisc.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.include_app_bar.*

class ScanMusicActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_music)

        toolbar.setTitle("扫描音乐")
        toolbar.setNavigationOnClickListener(this);

    }
}
