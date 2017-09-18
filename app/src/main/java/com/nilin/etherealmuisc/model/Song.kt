package com.kcode.gankotlin.repository

/**
 * Created by nilin on 2017/9/17.
 */
//data class Song(var song: String, var singer: String, var path: String, var size: String)

class Song {
    //歌曲名
    var song: String? = null
    //歌曲
    var singer: String? = null
    //歌曲地址
    var path: String? = null
    // 持续时间
    var duration: String? = null
    //歌曲大小
    var size: Long = 0
}
