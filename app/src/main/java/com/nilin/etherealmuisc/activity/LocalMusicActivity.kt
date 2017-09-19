package com.nilin.etherealmuisc.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.adapter.MusicAdapter
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.activity_local_music.*

import kotlinx.android.synthetic.main.include_app_bar.*
import com.nilin.etherealmuisc.model.Song


/**
 * Created by liangd on 2017/9/19.
 */
class LocalMusicActivity : AppCompatActivity(), View.OnClickListener {

    var adapter: MusicAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_music)

        toolbar.setTitle("本地音乐")
        toolbar.setNavigationOnClickListener(this)

        rv_local_music.layoutManager = LinearLayoutManager(this)
        adapter = MusicAdapter(this, R.layout.rv_music)
        rv_local_music.addItemDecoration(ItemDecoration(
                this, LinearLayoutManager.HORIZONTAL, 2, resources.getColor(R.color.grey_100p)))
        rv_local_music.adapter = adapter

        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener {
            adapter, _, position ->
//                        play(adapter.data[position] as Song)
        }

        adapter!!.setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener {
            adapter, view, position ->

            false
        })

    }


//    private fun play(song: Song) {
//        if (!playService!!.isPlaying) {
//            playService!!.play()
//        } else {
//            playService!!.pause()
//        }
//    }


    override fun onClick(p0: View?) {
        finish()
    }

}
