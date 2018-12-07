package com.nilin.etherealmuisc.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.db.Music
import org.litepal.LitePal
import org.litepal.extension.findAll


/**
 * Created by liangd on 2017/9/19.
 */
class MusicAdapter(var context: Context, layoutId: Int) : BaseQuickAdapter<Music, BaseViewHolder>(layoutId, LitePal.findAll<Music>()) {

    override fun convert(viewHolder: BaseViewHolder?, music: Music?) {

        viewHolder!!.setImageResource(R.id.iv_favorite, R.drawable.btn_not_favorite_gray).addOnClickListener(R.id.iv_favorite)
                    .setText(R.id.tv_song, music!!.song)
                    .setText(R.id.tv_singer, music.singer)
                    .setImageResource(R.id.iv_more, R.drawable.btn_more).addOnClickListener(R.id.iv_more)
    }
}