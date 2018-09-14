package com.nilin.etherealmuisc.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.*
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.activity_play.*
import android.widget.SeekBar
import com.nilin.etherealmuisc.utils.MediaUtils
import com.nilin.etherealmuisc.view.DefaultLrcBuilder
import java.io.*


/**
 * Created by liangd on 2017/9/19.
 */
class PlayActivity() : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    //Handler用于更新已经播放时间
    private var myHandler: MyHandler? = null
    var aa = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_play)

        //显示歌词
        val file = File("storage/emulated/0/倩影随行.lrc")
        if (file.exists()) {
            val fis = FileInputStream(file)
            val size = fis.available()
            val buffer = ByteArray(size)
            //  把内存从inputstream内读取到数组上
            fis.read(buffer)
            fis.close()
            val lrc = String(buffer)
            val builder = DefaultLrcBuilder()
            val rows = builder.getLrcRows(lrc)
            lrcView.setLrc(rows)
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.nilin.etherealmusic.play")
        registerReceiver(broadcastReceiver, intentFilter)

        myHandler = MyHandler(this)

        iv_play_back.setOnClickListener(this)
        ib_music_contorl.setOnClickListener(this)
        ib_play_previous.setOnClickListener(this)
        ib_play_contorl.setOnClickListener(this)
        ib_play_next.setOnClickListener(this)
        ib_my_favorite.setOnClickListener(this)

        MusicSeekBar.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            playService!!.seekTo(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_play_back -> finish()

            R.id.ib_music_contorl -> {
                if (aa==1) {
                    aa=2
                    ib_music_contorl.setBackgroundResource(R.drawable.button_loop)

                } else if (aa==2) {
                    aa=3
                    ib_music_contorl.setBackgroundResource(R.drawable.button_loop_one)

                } else if (aa==3) {
                    aa=1
                    ib_music_contorl.setBackgroundResource(R.drawable.button_random)

                }
            }

            R.id.ib_play_previous -> {
                playService!!.previous()
            }

            R.id.ib_play_contorl -> {
                if (playService!!.isPlaying) {
                    playService!!.pause()
                    ib_play_contorl.isSelected = false
                } else {
                    playService!!.start()
                    ib_play_contorl.isSelected = true
                }
            }

            R.id.ib_play_next -> {
                playService!!.next()
            }

            R.id.ib_my_favorite -> {
                if (aa==1) {
                    aa=2
                    ib_my_favorite.isSelected = true

                } else {
                    aa=1
                    ib_my_favorite.isSelected = false
                }
            }
        }
    }

    internal class MyHandler(private val playActivity: PlayActivity?) : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (playActivity != null) {
                when (msg.what) {
                    UPDATE_TIME//更新时间(已经播放时间)
                    -> playActivity.MusicStatus.setText(MediaUtils.formatTime(msg.obj.toString().toLong()))
                    UPDATE_LRC -> playActivity.lrcView.seekLrcToTime(msg.obj.toString().toLong())
                    else -> {
                    }
                }
            }
        }
    }

    override fun publish(progress: Int) {
        myHandler!!.obtainMessage(UPDATE_TIME, progress).sendToTarget()
        MusicSeekBar.setProgress(progress)
        myHandler!!.obtainMessage(UPDATE_LRC, progress).sendToTarget()
    }

    override fun change() {
        MusicTime.setText(MediaUtils.formatTime(playService!!.duration.toLong()))//设置结束时
        MusicSeekBar.setMax(playService!!.duration)//设置进度条最大值为MP3总时间

        val pref = getSharedPreferences("music_pref", Context.MODE_PRIVATE)
        val song = pref.getString("song", "空灵音乐")
        changeF1(song)

        ib_play_contorl.isSelected = playService!!.isPlaying

//        if (playService!!.isPlaying) {
//            ib_play_contorl.isSelected = true
//        } else {
//            ib_play_contorl.isSelected = false
//        }
    }

    /**
     * 歌词显示
     */
//    fun getFromAssets(fileName: String): String {
//        try {
//            val inputReader = InputStreamReader(resources.assets.open(fileName))
//            val bufReader = BufferedReader(inputReader)
//            var line = bufReader.readLine()
//            var Result = ""
//            while (line != null) {
//                Result += line + "\r\n"
//                line = bufReader.readLine()
//            }
//            return Result
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return ""
//    }

    fun changeF1(song: String) {
        if (song.length > 10) {
            tv_music_song.setText(song.substring(0, 10))
        } else {
            tv_music_song.setText(song)
        }
    }

    val broadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action.equals("com.nilin.etherealmusic.play")) {
                val song = intent.getStringExtra("song")
                (context as PlayActivity).changeF1(song)
            }
        }
    }

    constructor(parcel: Parcel) : this() {
        aa = parcel.readInt()
    }

    companion object {
        private val UPDATE_TIME = 0x10    //更新播放事件的标记
        private val UPDATE_LRC = 0x20     //更新播放事件的标记
    }

    override fun onResume() {
        super.onResume()
        bindPlayService()//绑定服务
    }

    override fun onPause() {
        super.onPause()
        unbindPlayService()//解绑服务
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
        unbindPlayService()//解绑服务
    }

}
