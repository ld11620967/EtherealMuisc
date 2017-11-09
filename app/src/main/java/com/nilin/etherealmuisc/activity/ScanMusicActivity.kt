package com.nilin.etherealmuisc.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.nilin.etherealmuisc.Music
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.model.Song
import com.nilin.etherealmuisc.utils.MusicUtils
import com.nilin.etherealmuisc.utils.MusicUtils.getMusicData
import kotlinx.android.synthetic.main.activity_scan_music.*
import kotlinx.android.synthetic.main.include_app_bar.*
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.animation.ObjectAnimator
import com.nilin.etherealmuisc.view.ShaderView


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
        /* new Thread(new Runnable() {
          @Override
          public void run() {
            while (degrees <= 360) {
              degrees += 1;
              mShaderView.setDegrees(degrees);

              try {
                Thread.sleep(30);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }


          }
        }).start();

        degrees = 0;
        mShaderView.setDegrees(degrees);*/

        MyApplication.instance!!.getMusicDao().deleteAll()
        for (i in 0..getMusicData(this).size - 1) {
            val list = Music(i.toLong(), getMusicData(this).get(i).song, getMusicData(this).get(i).singer, getMusicData(this).get(i).path)
            MyApplication.instance!!.getMusicDao().insertInTx(list)
        }
    }

    override fun onClick(p0: View?) {
        finish()
    }
}
