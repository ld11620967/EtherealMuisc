package com.nilin.etherealmuisc.service

import com.nilin.etherealmuisc.model.Song

/**
 * Created by liangd on 2017/9/25.
 */
interface OnPlayerEventListener {

    /**
     * 切换歌曲
     */
    fun onChange(music: Song)

    /**
     * 继续播放
     */
    fun onPlayerStart()

    /**
     * 暂停播放
     */
    fun onPlayerPause()

    /**
     * 更新进度
     */
    fun onPublish(progress: Int)

    /**
     * 缓冲百分比
     */
    fun onBufferingUpdate(percent: Int)

    /**
     * 更新定时停止播放时间
     */
    fun onTimer(remain: Long)

    fun onMusicListUpdate()
}