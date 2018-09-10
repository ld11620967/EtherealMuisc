package com.nilin.etherealmuisc.receiver

import android.annotation.SuppressLint
import android.content.ComponentName
import android.media.AudioManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.KeyEvent
import java.util.*


@Suppress("DEPRECATED_IDENTITY_EQUALS", "DEPRECATION")
class HeadsetButtonReceiver : BroadcastReceiver {
    private var context: Context? = null
    private val timer = Timer()

    internal var handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            try {
                if (msg.what === 1) {
                    headsetListener!!.playOrPause()
                } else if (msg.what === 2) {
                    headsetListener!!.playNext()
                } else if (msg.what === 3) {
                    headsetListener!!.playPrevious()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    constructor() : super() {}

    constructor(ctx: Context) : super() {
        context = ctx
        headsetListener = null
        registerHeadsetReceiver()
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_MEDIA_BUTTON == intent.action) {
            val keyEvent = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
            if (keyEvent.getKeyCode() === KeyEvent.KEYCODE_HEADSETHOOK && keyEvent.getAction() === KeyEvent.ACTION_UP) {
                clickCount = clickCount + 1
                if (clickCount == 1) {
                    val headsetTimerTask = HeadsetTimerTask()
                    timer.schedule(headsetTimerTask, 1000)
                }
            } else if (keyEvent.getKeyCode() === KeyEvent.KEYCODE_MEDIA_NEXT) {
                handler.sendEmptyMessage(2)
            } else if (keyEvent.getKeyCode() === KeyEvent.KEYCODE_MEDIA_PREVIOUS) {
                handler.sendEmptyMessage(3)
            }
        }
    }

    internal inner class HeadsetTimerTask : TimerTask() {
        override fun run() {
            try {
                if (clickCount == 1) {
                    handler.sendEmptyMessage(1)
                } else if (clickCount == 2) {
                    handler.sendEmptyMessage(2)
                } else if (clickCount >= 3) {
                    handler.sendEmptyMessage(3)
                }
                clickCount = 0
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    interface onHeadsetListener {
        fun playOrPause()
        fun playNext()
        fun playPrevious()
    }

    fun setOnHeadsetListener(newHeadsetListener: onHeadsetListener) {
        headsetListener = newHeadsetListener
    }

    fun registerHeadsetReceiver() {
        val audioManager = context!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val name = ComponentName(context!!.getPackageName(), HeadsetButtonReceiver::class.java.name)
        audioManager.registerMediaButtonEventReceiver(name)
    }

    fun unregisterHeadsetReceiver() {
        val audioManager = context!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val name = ComponentName(context!!.getPackageName(), HeadsetButtonReceiver::class.java.name)
        audioManager.unregisterMediaButtonEventReceiver(name)
    }

    companion object {
        private var clickCount: Int = 0
        private var headsetListener: onHeadsetListener? = null
    }
}