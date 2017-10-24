package com.nilin.etherealmuisc.activity

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.WindowManager
import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.include_play_bar.*
import android.widget.SeekBar
import com.nilin.etherealmuisc.model.Song
import com.nilin.etherealmuisc.utils.MediaUtils
import com.nilin.etherealmuisc.utils.MusicUtils
import java.io.BufferedReader
import java.io.InputStreamReader
import com.nilin.etherealmuisc.view.DefaultLrcBuilder


/**
 * Created by liangd on 2017/9/19.
 */
class PlayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_play)

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.nilin.etherealmusic.play")

        //显示歌词
//        val lrcView = (findViewById<View>(R.id.lrcView) as LrcView)
        val lrc = getFromAssets("lyric.lrc")
        val builder = DefaultLrcBuilder()
        val rows = builder.getLrcRows(lrc)
        lrcView.setLrc(rows)

        iv_play_back.setOnClickListener { finish() }
    }

    fun player_start() {
        iv_play_bar_play.setBackgroundResource(R.drawable.player_start)
    }

    fun player_pause() {
        iv_play_bar_play.setBackgroundResource(R.drawable.player_pause)
    }


    //Handler用于更新已经播放时间
    private val myHandler: MyHandler? = null

    fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            playService!!.seekTo(progress)
        }
    }

    fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    fun onStopTrackingTouch(seekBar: SeekBar) {

    }

    internal class MyHandler(private val playActivity: PlayActivity?) : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (playActivity != null) {
                when (msg.what) {
                    UPDATE_TIME//更新时间(已经播放时间)
                    -> playActivity.MusicStatus.setText(MediaUtils.formatTime(msg.obj as Long))
                    UPDATE_LRC -> playActivity.lrcView.seekLrcToTime(msg.obj as Long)
                    else -> {
                    }
                }
            }
        }
    }

    override fun publish(progress: Int) {
        myHandler!!.obtainMessage(UPDATE_TIME, progress).sendToTarget()
        MusicSeekBar.setProgress(progress)
        myHandler.obtainMessage(UPDATE_LRC, progress).sendToTarget()
    }

    override fun change() {
        MusicTime.setText(MediaUtils.formatTime(playService!!.duration.toLong()))//设置结束时
        MusicSeekBar.setMax(playService!!.duration)//设置进度条最大值为MP3总时间

        val pref = getSharedPreferences("title", Context.MODE_PRIVATE)
        val title = pref.getString("title", "")
        tv_music_title.setText(title)

        if (playService!!.isPlaying) {
            ib_play_contorl.isSelected = true
        } else {
            ib_play_contorl.isSelected = false
        }
    }

    /**
     * 歌词显示
     */
    fun getFromAssets(fileName: String): String {
        try {
            val inputReader = InputStreamReader(resources.assets.open(fileName))
            val bufReader = BufferedReader(inputReader)
            var line = ""
            var Result = ""
            while (bufReader.readLine() != null) {
                line = bufReader.readLine()
                if (line.trim { it <= ' ' } == "")
                    continue
                Result += line + "\r\n"
            }
            return Result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
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
        unbindPlayService()//解绑服务
    }

    companion object {
        private val UPDATE_TIME = 0x10    //更新播放事件的标记
        private val UPDATE_LRC = 0x20     //更新播放事件的标记
    }

}
