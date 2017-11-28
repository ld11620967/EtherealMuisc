package com.nilin.etherealmuisc.activity

import com.nilin.etherealmuisc.service.PlayService
import android.content.Intent
import android.content.ComponentName
import android.content.Context
import android.os.IBinder
import android.content.ServiceConnection
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


/**
* Created by liangd on 2017/9/13.
*/
abstract class BaseActivity : AppCompatActivity() {

    protected var playService: PlayService? = null
    private var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val playBinder = service as PlayService.PlayBinder
            playService = playBinder.service
            playService!!.setMusicUpdatrListener(musicUpdatrListener)
            musicUpdatrListener.onChange()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            playService = null
            isBound = false
        }
    }

    //实现MusicUpdatrListener的相关方法,把PlayService.MusicUpdatrListener的具体内容,
    // 延迟到子类来具体实现(把具体的操作步骤在子类中实现)
    private val musicUpdatrListener = object : PlayService.MusicUpdatrListener {
        override fun onPublish(progress: Int) {
            publish(progress)
        }

        //初始化，回调PlayAcitvity中的change(),用于PlayAcitvity中change()数据初始化。
        override fun onChange() {
            change()
        }
    }

    //抽象类(子类来具体实现,用于更新UI)
    abstract fun publish(progress: Int)

    abstract fun change()

    //绑定服务
    fun bindPlayService() {
        if (!isBound) {
            val intent = Intent(this, PlayService::class.java)
            bindService(intent, conn, Context.BIND_AUTO_CREATE)
            isBound = true
        }
    }

    //解除服务
    fun unbindPlayService() {
        if (isBound) {
            unbindService(conn)
            isBound = false
        }
    }

}