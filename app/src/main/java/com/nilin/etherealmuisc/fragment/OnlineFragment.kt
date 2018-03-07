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
import com.nilin.etherealmuisc.adapter.MusicChartsAdapter
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_online.*


/**
 * Created by liangd on 2017/9/19.
 */
class OnlineFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var adapter: MusicChartsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the rv_music for this fragment
        return inflater!!.inflate(R.layout.fragment_online, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_online_music.layoutManager = LinearLayoutManager(context)
        adapter = MusicChartsAdapter(context!!, R.layout.rv_music_charts)
        rv_online_music.addItemDecoration(ItemDecoration(
                context, LinearLayoutManager.HORIZONTAL, 2, resources.getColor(R.color.grey_100p)))
        rv_online_music.adapter = adapter

        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val song = adapter.data[position] as Music
//            playService!!.prepare(song.path!!)

            val editor = MyApplication.instance!!.getSharedPreferences("music_pref", Context.MODE_PRIVATE).edit()
            editor.putString("song", song.song)
            editor.putInt("position", position)
            editor.apply()

            val intent = Intent("com.nilin.etherealmusic.play")
            intent.putExtra("song", song.song)
            intent.putExtra("singer", song.singer)
            intent.putExtra("position", position)
            context.sendBroadcast(intent)
//            playService!!.start()
        }

    }

}
