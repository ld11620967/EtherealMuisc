package com.nilin.etherealmuisc.adapter

import android.content.Context
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kcode.gankotlin.repository.Song
import com.nilin.etherealmuisc.R


/**
 * Created by nilin on 2017/9/17.
 */
class MusicAdapter(var context: Context, val list: List<Song>,layoutId:Int) : BaseQuickAdapter<Song, BaseViewHolder>(layoutId) {

//    private val list: List<Song>? = null

    override fun convert(viewHolder: BaseViewHolder?, song: Song?) {

//        viewHolder!!.setText(R.id.item_song,song!!.song)
//        viewHolder.setText(R.id.item_singer,song.singer)

        viewHolder!!.setText(R.id.item_song,list[0].song)
        viewHolder.setText(R.id.item_singer,list[0].singer)
    }
}