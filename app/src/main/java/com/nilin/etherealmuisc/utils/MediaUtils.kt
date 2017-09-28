package com.nilin.etherealmuisc.utils

/**
 * Created by liangd on 2017/9/28.
 */
object MediaUtils {

    /**
     * 格式化时间,将毫秒转换为分:秒格式
     */
    fun formatTime(time: Long): String {
        var min = (time / (1000 * 60)).toString() + ""
        var sec = (time % (1000 * 60)).toString() + ""
        if (min.length < 2) {
            min = "0" + time / (1000 * 60) + ""
        } else {
            min = (time / (1000 * 60)).toString() + ""
        }
        if (sec.length == 4) {
            sec = "0" + time % (1000 * 60) + ""
        } else if (sec.length == 3) {
            sec = "00" + time % (1000 * 60) + ""
        } else if (sec.length == 2) {
            sec = "000" + time % (1000 * 60) + ""
        } else if (sec.length == 1) {
            sec = "0000" + time % (1000 * 60) + ""
        }
        return min + ":" + sec.trim { it <= ' ' }.substring(0, 2)
    }

}