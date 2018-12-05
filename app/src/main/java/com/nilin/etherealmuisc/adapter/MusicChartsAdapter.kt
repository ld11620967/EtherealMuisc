package com.nilin.etherealmuisc.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.db.Music
import org.litepal.LitePal
import org.litepal.extension.findAll

/**
 * Created by liangd on 2018/3/7.
 */
class MusicChartsAdapter(var context: Context, layoutId: Int) : BaseQuickAdapter<Music, BaseViewHolder>(layoutId, LitePal.findAll<Music>()) {

    override fun convert(viewHolder: BaseViewHolder?, music: Music) {

        viewHolder!!.setImageResource(R.id.rank, R.drawable.rank_1)
                .addOnClickListener(R.id.rank)
        viewHolder.setText(R.id.song1, music.song)
        viewHolder.setText(R.id.song2, music.singer)
        viewHolder.setText(R.id.song3, music.singer)

    }

}