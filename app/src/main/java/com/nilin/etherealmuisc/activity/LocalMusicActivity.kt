package com.nilin.etherealmuisc.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.activity_local_music.*

import kotlinx.android.synthetic.main.include_app_bar.*


class LocalMusicActivity : AppCompatActivity(), View.OnClickListener {

    var adapter: ArticleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_music)

        toolbar.setTitle("本地音乐")
        toolbar.setNavigationOnClickListener(this)

        rv_local_music.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter(this,R.layout.item_news)
        rv_local_music.adapter = adapter
    }

    override fun onClick(p0: View?) {
        finish()
    }

}
