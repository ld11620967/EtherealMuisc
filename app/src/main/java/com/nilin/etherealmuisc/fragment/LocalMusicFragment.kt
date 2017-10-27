package com.nilin.etherealmuisc.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.nilin.etherealmuisc.Music
import com.nilin.etherealmuisc.MyApplication

import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.adapter.MusicAdapter
import com.nilin.etherealmuisc.model.Song
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_local_music.*
import kotlinx.android.synthetic.main.include_app_bar.*


class LocalMusicFragment : BaseFragment(), View.OnClickListener {

    var adapter: MusicAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_local_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setTitle("本地音乐")
        toolbar.setNavigationOnClickListener(this)

        rv_local_music.layoutManager = LinearLayoutManager(context)
        adapter = MusicAdapter(context!!, R.layout.rv_music)
        rv_local_music.addItemDecoration(ItemDecoration(
                context, LinearLayoutManager.HORIZONTAL, 2, resources.getColor(R.color.grey_100p)))
        rv_local_music.adapter = adapter



        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val song = adapter.data[position] as Song
            playService!!.prepare(song.path!!)

            val editor = MyApplication.instance!!.getSharedPreferences("title", Context.MODE_PRIVATE).edit()
            editor.putString("title", song.song)
            editor.putInt("position", position)
            editor.apply()

            val intent = Intent("com.nilin.etherealmusic.play")
            intent.putExtra("song", song.song)
            intent.putExtra("songer", song.singer)
            intent.putExtra("play", true)
            intent.putExtra("position", position)
            context.sendBroadcast(intent)
            playService!!.start()
        }

        adapter!!.setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener { adapter, _, position ->

            false
        })
    }

    override fun onClick(p0: View?) {
        getFragmentManager().popBackStack();
    }

    override fun onResume() {
        super.onResume()
        bindPlayService()//绑定服务
    }

    override fun onPause() {
        super.onPause()
        unbindPlayService()//解绑服务
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindPlayService()//解绑服务
    }

}
