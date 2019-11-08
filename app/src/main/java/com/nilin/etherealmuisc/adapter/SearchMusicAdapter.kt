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
//class SearchMusicAdapter(var context: Context, layoutResId: Int, data: List<Music>) : BaseQuickAdapter<List<Music>, BaseViewHolder>(layoutResId, data) {
//
//    override fun convert(helper: BaseViewHolder, mm: List<Music>) {
//
//        helper.setText(R.id.tv_song, mm[0].song)
//                  .setText(R.id.tv_singer, mm[0].singer)
//                  .setImageResource(R.id.iv_more, R.drawable.btn_more).addOnClickListener(R.id.iv_more)
//    }
//}


class SearchMusicAdapter(var context: Context, layoutId: Int) : BaseQuickAdapter<Music, BaseViewHolder>(layoutId, LitePal.findAll<Music>()) {

    override fun convert(viewHolder: BaseViewHolder, music: Music) {

        viewHolder.setText(R.id.tv_song, music.song)
                .setText(R.id.tv_singer, music.singer)
                .setImageResource(R.id.iv_more, R.drawable.btn_more).addOnClickListener(R.id.iv_more)
    }
}



//class HomeAdapter(layoutResId: Int, data: List<*>) : BaseQuickAdapter<HomeItem, BaseViewHolder>(layoutResId, data) {
//
//    override fun convert(helper: BaseViewHolder, item: HomeItem) {
//        helper.setText(R.id.text, item.getTitle())
//        helper.setImageResource(R.id.icon, item.getImageResource())
//
//    }
//}


