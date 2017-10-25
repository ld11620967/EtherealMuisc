package com.nilin.etherealmuisc.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.nilin.etherealmuisc.Music
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.utils.MusicUtils
import com.nilin.etherealmuisc.utils.MusicUtils.getMusicData
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

        scan_music.setOnClickListener { scanMusic() }
    }

    fun scanMusic() {
//        for (i in 0 ..getMusicData(this).lastIndex) {
//            val list = Music(null, getMusicData(this).get(i).song, getMusicData(this).get(i).path)
//            MyApplication.instance!!.getMusicDao().insertInTx(list)
//        }
    }

    override fun onClick(p0: View?) {
        finish()
    }
}
