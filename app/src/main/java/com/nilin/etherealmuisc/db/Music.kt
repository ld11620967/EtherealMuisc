package com.nilin.etherealmuisc.db

import org.litepal.crud.LitePalSupport


class Music : LitePalSupport() {
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
    //是否收藏
    var isFavorite: Boolean = false
}