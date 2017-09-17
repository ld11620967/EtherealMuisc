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
class MusicAdapter(var context: Context, layoutId:Int) : BaseQuickAdapter<Song, BaseViewHolder>(layoutId) {

    override fun convert(viewHolder: BaseViewHolder?, article: Song?) {
////////////////////////////////////////////////////////////////////////////
        viewHolder!!.setText(list.get(i).song.toString())
        viewHolder.setText(list.get(i).singer.toString())

        val image: ImageView = viewHolder.getView(R.id.image)


    }
}