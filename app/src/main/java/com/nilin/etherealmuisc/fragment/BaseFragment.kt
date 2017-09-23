package com.nilin.etherealmuisc.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.support.v4.app.Fragment
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.service.PlayService


/**
* Created by nilin on 2017/9/23.
*/
abstract class BaseFragment : Fragment() {

    protected var playService: PlayService? = null
    private var isBound = false
    val context = MyApplication.instance

    private val conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val playBinder = service as PlayService.PlayBinder
            playService = playBinder.service
        }

        override fun onServiceDisconnected(name: ComponentName) {
            playService = null
            isBound = false
        }
    }

    //绑定服务
    fun bindPlayService() {
        if (!isBound) {
            val intent = Intent(context, PlayService::class.java)
            context!!.bindService(intent, conn, Context.BIND_AUTO_CREATE)
            isBound = true
        }
    }

    //解除服务
    fun unbindPlayService() {
        if (isBound) {
            context!!.unbindService(conn)
            isBound = false
        }
    }

}