package com.nilin.etherealmuisc.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.db.Music
import org.litepal.LitePal
import org.litepal.extension.findAll
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView




/**
 * Created by liangd on 2017/9/19.
 */
class MusicAdapter(var context: Context, layoutId: Int) : BaseQuickAdapter<Music, BaseViewHolder>(layoutId, LitePal.findAll<Music>()) {

    override fun convert(viewHolder: BaseViewHolder, music: Music) {

        if (music.isFavorite) {
            viewHolder.setImageResource(R.id.iv_favorite, R.drawable.btn_favorite).addOnClickListener(R.id.iv_favorite)
        } else {
            viewHolder.setImageResource(R.id.iv_favorite, R.drawable.btn_not_favorite_gray).addOnClickListener(R.id.iv_favorite)
        }
        viewHolder.setText(R.id.tv_song, music.song)
                  .setText(R.id.tv_singer, music.singer)
                  .setImageResource(R.id.iv_more, R.drawable.btn_more).addOnClickListener(R.id.iv_more)
    }

//    fun clickIsFavorite(position: Int, isFavorite: Boolean) {
//        val viewHolder: BaseViewHolder
//        val layoutManager = LinearLayoutManager(context)
//        val view=layoutManager.getChildAt(position)
//        if (isFavorite) {
//            viewHolder.setImageResource(R.id.iv_favorite, R.drawable.btn_favorite).addOnClickListener(R.id.iv_favorite)
//        } else {
//            viewHolder.setImageResource(R.id.iv_favorite, R.drawable.btn_not_favorite_gray).addOnClickListener(R.id.iv_favorite)
//        }
//    }
}