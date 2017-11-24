package com.nilin.etherealmuisc.activity

import android.view.animation.AnimationUtils
import android.os.Bundle
import android.view.animation.Animation
import android.app.Activity
import android.content.Intent
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit


/**
* Created by liangd on 2017/11/23.
*/
class SplashActivity : Activity() {

    val context = MyApplication.instance
    private var imgAnimation: Animation? = null
    private var textAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        launch(CommonPool) {
            delay(1200, TimeUnit.MILLISECONDS)
            goHome()
        }

        imgAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
        textAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim)

        welcomeImg.startAnimation(imgAnimation)
        welcomeText.startAnimation(textAnimation)
    }

    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}