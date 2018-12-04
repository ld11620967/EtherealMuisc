package com.nilin.etherealmuisc.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
//import com.nilin.etherealmuisc.Music
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.utils.MusicUtils.getMusicData
import kotlinx.android.synthetic.main.activity_scan_music.*
import kotlinx.android.synthetic.main.include_app_bar.*
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.animation.ObjectAnimator
import android.widget.Toast
import com.nilin.etherealmuisc.db.Music
import org.jetbrains.anko.custom.async
import org.litepal.LitePal
import org.litepal.extension.findAll


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

        val degrees = ObjectAnimator.ofInt(sv, "degrees", 0, 360)
        degrees.interpolator = LinearInterpolator()
        degrees.duration = 1000
        degrees.repeatCount = ValueAnimator.INFINITE
        degrees.start()

        async({
            MyApplication.instance!!.getMusicDao().deleteAll()
            for (i in 0..getMusicData(MyApplication.instance!!).size - 1) {

                val music = Music()
                music.song=getMusicData(MyApplication.instance!!).get(i).song
                music.singer=getMusicData(MyApplication.instance!!).get(i).singer
                music.path=getMusicData(MyApplication.instance!!).get(i).path
                music.save()

//                val allSongs = LitePal.findAll<Music>()
//                MyApplication.instance!!.getMusicDao().insertInTx(allSongs)

//                val list = Music(i.toLong(), getMusicData(MyApplication.instance!!).get(i).song, getMusicData(MyApplication.instance!!).get(i).singer, getMusicData(MyApplication.instance!!).get(i).path)
//                MyApplication.instance!!.getMusicDao().insertInTx(list)
            }
//                val allSongs = LitePal.findAll<Music>()
//                MyApplication.instance!!.getMusicDao().insertInTx(allSongs)
            runOnUiThread {
                degrees.cancel()
                Toast.makeText(MyApplication.instance!!,"已扫描完毕",Toast.LENGTH_SHORT).show()
            }
        })

//        Thread(Runnable {
//            var degrees = 0
//            while (degrees <= 360) {
//                degrees += 1
//                sv.setDegrees(degrees)
//                try {
//                    Thread.sleep(10)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//        }).start()
    }

    override fun onClick(p0: View?) {
        finish()
    }
}
