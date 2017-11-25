package com.nilin.etherealmuisc.utils

import android.provider.MediaStore
import android.content.Context
import android.util.Log
import com.nilin.etherealmuisc.model.Music


/**
* Created by liangd on 2017/9/19.
*/
object MusicUtils {
    /**
     * 扫描系统里面的音频文件，返回一个list集合
     */
    fun getMusicData(context: Context): ArrayList<Music> {
        val list = ArrayList<Music>()
        // 媒体库查询语句（写一个工具类MusicUtils）
        val cursor = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val music:Music?= Music()
//                music!!.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                music!!.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                music.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                music.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                music.duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                music.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                if (music.size.toInt() > 1000 * 800) {
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
