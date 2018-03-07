package com.nilin.etherealmuisc.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter

import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.adapter.MusicChartsAdapter
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_online.*


/**
 * Created by liangd on 2017/9/19.
 */
class OnlineFragment : BaseFragment() {

    val localMusicFragment = LocalMusicFragment()

    var adapter: MusicChartsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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

            activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, localMusicFragment, null)
                    .addToBackStack(null)
                    .commit()

        }
    }

}
