package com.nilin.etherealmuisc.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.adapter.MusicAdapter
import com.nilin.etherealmuisc.utils.MusicUtils
import kotlinx.android.synthetic.main.activity_local_music.*

import kotlinx.android.synthetic.main.include_app_bar.*


class LocalMusicActivity : AppCompatActivity(), View.OnClickListener {

    var adapter: MusicAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_music)

        toolbar.setTitle("本地音乐")
        toolbar.setNavigationOnClickListener(this)

        rv_local_music.layoutManager = LinearLayoutManager(this)
//        adapter = MusicAdapter(this,R.layout.rv_music)
//        val list:List<Song> = ArrayList()
        val list = MusicUtils.getMusicData(this)
        adapter = MusicAdapter(this,list,R.layout.rv_music)
Log.i("aaaaaa",list[0].toString())
        rv_local_music.adapter = adapter
    }

override fun onClick(p0: View?) {
        finish()
    }

}
