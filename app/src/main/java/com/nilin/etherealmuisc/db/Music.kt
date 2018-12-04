package com.nilin.etherealmuisc.db

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport
import com.nilin.etherealmuisc.greendao.MusicDao.Properties.Song




//data class Music(var song: String, var singer: String, var path: String, var size: String)

class Music : LitePalSupport() {
    @Column(unique = true, defaultValue = "unknown")
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

//    private val usic = ArrayList<Music>()
}