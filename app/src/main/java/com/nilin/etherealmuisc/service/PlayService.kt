package com.nilin.etherealmuisc.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.util.Log
import com.nilin.etherealmuisc.R
import java.util.concurrent.Executors
import android.os.IBinder
import android.os.Parcelable


/**
 * Created by nilin on 2017/9/10.
 */
class PlayService() : Service() {

    var mp: MediaPlayer? = MediaPlayer()

    val context: Context=this

//    private var musicUpdatrListener: MusicUpdatrListener? = null

    //创建一个单实例的线程,用于更新音乐信息
    private var es = Executors.newSingleThreadExecutor()


    inner class PlayBinder : Binder() {
        val service: PlayService
            get() = this@PlayService
    }

    override fun onBind(intent: Intent): IBinder? {
        return PlayBinder()
    }


    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer.create(this, R.raw.music)
        mp!!.isLooping = true

//        es!!.execute(updateSteatusRunnable)//更新进度值
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        //回收线程
        if (es != null && !es!!.isShutdown()) {//当进度值等于空,并且,进度值没有关闭
            es!!.shutdown()
            es = null
        }
    }

    //默认开始播放的方法
    fun start() {
//        if (mp != null && !mp!!.isPlaying) {//判断当前歌曲不等于空,并且没有在播放的状态
        mp!!.start()
        Log.i("22222", "22222")
//        }
    }

//    fun pause() {
////        if (mp.isPlaying) {
//        mp!!.pause()
////        }
//    }
//
//    fun stop() {
//        mp!!.stop()
//        try {
//            mp!!.prepare()
//            seekTo(0)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//    }
//
//    //获取当前是否为播放状态,提供给MyMusicListFragment的播放暂停按钮点击事件判断状态时调用
//    val isPlaying: Boolean
//        get() {
//            if (mp != null) {
//                return mp!!.isPlaying
//            }
//            return false
//        }
//
//    //获取当前的进度值
//    //mp,并且,为播放状态
//    val currentProgress: Int
//        get() {
//            if (mp != null && mp!!.isPlaying) {
//                return mp!!.currentPosition
//            }
//            return 0
//        }
//
//    //getDuration 获取文件的持续时间
//    val duration: Int
//        get() = mp!!.duration
//
//    //seekTo 寻找指定的时间位置
//    fun seekTo(msec: Int) {
//        mp!!.seekTo(msec)
//    }
//
//    //利用Runnable来实现多线程
//    /**
//     * Runnable
//     * Java中实现多线程有两种途径:继承Thread类或者实现Runnable接口.
//     * Runnable接口非常简单,就定义了一个方法run(),继承Runnable并实现这个
//     * 方法就可以实现多线程了,但是这个run()方法不能自己调用,必须由系统来调用,否则就和别的方法没有什么区别了.
//     * 好处:数据共享
//     */
//    //    internal var updateSteatusRunnable: Runnable = java.lang.Runnable //更新状态
////
////    {
////        //不断更新进度值
////        while (true) {
////            //音乐更新监听不为空,并且,媒体播放不为空,并且媒体播放为播放状态
////            if (musicUpdatrListener != null && mp != null && mp!!.isPlaying) {
////                musicUpdatrListener!!.onPublish(currentProgress)//获取当前的进度值
////            }
////            try {
////                Thread.sleep(500)//500毫秒更新一次
////            } catch (e: InterruptedException) {
////                e.printStackTrace()
////            }
////
////        }
////    }
//
//    //更新状态的接口(PlayService的内部接口),并在BaseActivity中实现
//    interface MusicUpdatrListener {
//        //音乐更新监听器
//        fun onPublish(progress: Int) //发表进度事件(更新进度条)
//
//        fun onChange()
//
//        //声明MusicUpdatrListener后,添加set方法
//    }
//
//    //set方法
//    fun setMusicUpdatrListener(musicUpdatrListener: MusicUpdatrListener) {
//        this.musicUpdatrListener = musicUpdatrListener
//    }


}