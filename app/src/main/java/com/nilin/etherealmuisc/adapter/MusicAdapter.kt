package com.nilin.etherealmuisc.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.model.Song
import com.nilin.etherealmuisc.utils.MusicUtils.getMusicData


/**
* Created by liangd on 2017/9/19.
*/
class MusicAdapter(var context: Context, layoutId: Int) : BaseQuickAdapter<Song, BaseViewHolder>(layoutId,getMusicData(context)) {

    override fun convert(viewHolder: BaseViewHolder?, song: Song?) {

        viewHolder!!.setText(R.id.item_song, song!!.song)
        viewHolder.setText(R.id.item_singer, song.singer)
        viewHolder.setImageResource(R.id.iv_more,R.drawable.ic_more)
                .addOnClickListener(R.id.iv_more)

    }

}