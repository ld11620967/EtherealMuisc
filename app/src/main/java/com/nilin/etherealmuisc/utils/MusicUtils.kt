package com.nilin.etherealmuisc.utils

import android.provider.MediaStore
import android.content.Context
import com.nilin.etherealmuisc.model.Music
import android.media.audiofx.AudioEffect
import android.content.Intent
import android.content.pm.PackageManager
import android.preference.PreferenceManager
import android.util.Log
import com.nilin.etherealmuisc.MyApplication


/**
 * Created by liangd on 2017/9/19.
 */
object MusicUtils {
    /**
     * 扫描系统里面的音频文件，返回一个list集合
     */
    fun getMusicData(context: Context): ArrayList<Music> {
        val filter_size = PreferenceManager.getDefaultSharedPreferences(context).getString("filter_size", "0")
        val filter_time = PreferenceManager.getDefaultSharedPreferences(context).getString("filter_time", "0")
        val list = ArrayList<Music>()
        // 媒体库查询语句（写一个工具类MusicUtils）
        val cursor = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val music: Music? = Music()
//                music!!.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                music!!.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                music.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                music.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                music.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                music.duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                if (music.size.toInt()/1000 > filter_size.toInt() || music.duration!!.toInt()/1000 > filter_time.toInt()) {
                    // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                    if (music.song!!.contains("-")) {
                        val str = music.song!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        music.singer = str[0]
                        music.song = str[1]
                    }
                    list.add(music)
                }
            }
            // 释放资源
            cursor.close()
        }
        return list
    }

    fun isAudioControlPanelAvailable(context: Context): Boolean {
        return isIntentAvailable(context, Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL))
    }

    private fun isIntentAvailable(context: Context, intent: Intent): Boolean {
        return context.packageManager.resolveActivity(intent, PackageManager.GET_RESOLVED_FILTER) != null
    }
}
