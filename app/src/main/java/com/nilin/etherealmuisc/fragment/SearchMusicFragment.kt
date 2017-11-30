package com.nilin.etherealmuisc.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.chad.library.adapter.base.BaseQuickAdapter

import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.adapter.MusicAdapter
import com.nilin.etherealmuisc.model.Music
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_local_music.*
import kotlinx.android.synthetic.main.include_music_search_bar.*


class SearchMusicFragment : BaseFragment(){

    var adapter: MusicAdapter? = null
    val inputManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_search_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_local_music.requestFocus()
        showInputMethod()

        iv_back.setOnClickListener {
            hideInputMethod()
            getFragmentManager().popBackStack() }

        rv_local_music.layoutManager = LinearLayoutManager(context)
        adapter = MusicAdapter(context!!, R.layout.rv_music)
        rv_local_music.addItemDecoration(ItemDecoration(
                context, LinearLayoutManager.HORIZONTAL, 2, resources.getColor(R.color.grey_100p)))
//        rv_local_music.adapter = adapter

        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val song = adapter.data[position] as Music
            playService!!.prepare(song.path!!)
            val intent = Intent("com.nilin.etherealmusic.play")
            intent.putExtra("song", song.song)
            intent.putExtra("songer", song.singer)
            playService!!.start()
            intent.putExtra("play", true)
            context.sendBroadcast(intent)
        }
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

    private fun showInputMethod() {
        //自动弹出键盘
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun hideInputMethod() {
        //强制隐藏Android输入法窗口
        inputManager.hideSoftInputFromWindow(tv_local_music.getWindowToken(),0)
    }
}
