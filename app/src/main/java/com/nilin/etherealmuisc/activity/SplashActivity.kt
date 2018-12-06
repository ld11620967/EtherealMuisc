package com.nilin.etherealmuisc.activity

import android.view.animation.AnimationUtils
import android.os.Bundle
import android.view.animation.Animation
import android.app.Activity
import android.content.Context
import android.content.Intent
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.db.Music
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.litepal.LitePal
import org.litepal.extension.find


/**
 * Created by liangd on 2017/11/23.
 */
class SplashActivity : Activity() {

    var music_info: Music? = null
    private var imgAnimation: Animation? = null
    private var textAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val music_pref = getSharedPreferences("music_pref", Context.MODE_PRIVATE)
        val position = music_pref.getInt("position", -1)

        if (position != -1) {
            music_info = LitePal.find<Music>((position).toLong())
        }

        GlobalScope.launch  {
            delay(800L)
            goHome()
        }

        imgAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
        textAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim)

        welcomeImg.startAnimation(imgAnimation)
        welcomeText.startAnimation(textAnimation)
    }

    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        if (music_info !== null) {
            intent.putExtra("song", music_info!!.song)
            intent.putExtra("singer", music_info!!.singer)
            intent.putExtra("path", music_info!!.path)
            intent.putExtra("firstStart", true)
        }
        startActivity(intent)
        finish()
    }
}