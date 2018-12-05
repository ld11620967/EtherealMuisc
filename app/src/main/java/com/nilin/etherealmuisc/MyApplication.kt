package com.nilin.etherealmuisc

import android.app.Application
import org.litepal.LitePal


/**
* Created by nilin on 2017/9/16.
*/
open class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        instance = this
    }

    companion object {
        // 因为我们程序运行后，Application是首先初始化的，如果在这里不用判断instance是否为空
        var instance: MyApplication? = null
            private set
    }
}