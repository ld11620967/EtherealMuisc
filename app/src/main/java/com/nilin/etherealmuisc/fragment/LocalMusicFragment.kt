package com.nilin.etherealmuisc.fragment

import android.os.Bundle

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.nilin.etherealmuisc.MyApplication

import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.adapter.MusicAdapter
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_local_music.*
import kotlinx.android.synthetic.main.include_app_bar.*
import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import kotlinx.android.synthetic.main.fragment_local.*


class LocalMusicFragment : Fragment() {

    var context = MyApplication.instance
    var adapter: MusicAdapter? = null
    var mActivity: Activity? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_local_music, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//                toolbar.setTitle("本地音乐")
//        toolbar.setNavigationOnClickListener(context)

        rv_local_music.layoutManager = LinearLayoutManager(context)
        adapter = MusicAdapter(context!!, R.layout.rv_music)

        rv_local_music.setAdapter(adapter)

//        rv_local_music.addItemDecoration(ItemDecoration(
//                context!!, LinearLayoutManager.HORIZONTAL, 2, resources.getColor(R.color.grey_100p)))
        rv_local_music.adapter = adapter

//
//        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener {
//            adapter, _, position ->
//            //            play(adapter.data[position] as Song)
//        }
//
//        adapter!!.setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener {
//            adapter, view, position ->
//
//            false
//        })

    }

}

//private fun Toolbar.setNavigationOnClickListener(context: MyApplication?) {
//    Log.i("11111","22222")
//}
