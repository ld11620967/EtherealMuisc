package com.nilin.etherealmuisc.utils

import android.provider.MediaStore
import android.content.Context
import android.util.Log
import com.nilin.etherealmuisc.model.Song

/**
* Created by liangd on 2017/9/19.
*/
object MusicUtils {
    /**
     * 扫描系统里面的音频文件，返回一个list集合
     */
    fun getMusicData(context: Context): List<Song> {
        val list = ArrayList<Song>()
        // 媒体库查询语句（写一个工具类MusicUtils）
        val cursor = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val song:Song?= Song()
//                song!!.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                song!!.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                song.duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                song.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                if (song.size.toInt() > 1000 * 800) {
                    // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                    if (song.song!!.contains("-")) {
                        val str = song.song!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        song.singer = str[0]
                        song.song = str[1]
                    }
                    list.add(song)
                }
            }
            // 释放资源
            cursor.close()
        }
        return list
    }

    /**
     * 定义一个方法用来格式化获取到的时间
     */
    fun formatTime(time: Int): String {
        return if (time / 1000 % 60 < 10) {
            (time / 1000 / 60).toString() + ":0" + time / 1000 % 60

        } else {
            (time / 1000 / 60).toString() + ":" + time / 1000 % 60
        }
    }
}
