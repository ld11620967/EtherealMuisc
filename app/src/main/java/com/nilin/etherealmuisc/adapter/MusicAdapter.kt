package com.nilin.etherealmuisc.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.Music
import com.nilin.etherealmuisc.R


/**
 * Created by liangd on 2017/9/19.
 */
class MusicAdapter(var context: Context, layoutId: Int) : BaseQuickAdapter<Music, BaseViewHolder>(layoutId, MyApplication.instance!!.getMusicDao().queryBuilder().list()) {

    override fun convert(viewHolder: BaseViewHolder?, music: Music?) {

        viewHolder!!.setText(R.id.item_song, music!!.song)
        viewHolder.setText(R.id.item_singer, music.singer)
        viewHolder.setImageResource(R.id.iv_more, R.drawable.ic_more)
                .addOnClickListener(R.id.iv_more)
    }

}