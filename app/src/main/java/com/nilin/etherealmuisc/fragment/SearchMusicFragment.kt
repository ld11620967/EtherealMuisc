package com.nilin.etherealmuisc.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.chad.library.adapter.base.BaseQuickAdapter

import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.adapter.SearchMusicAdapter
import com.nilin.etherealmuisc.db.Music
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.include_music_search_bar.*
import kotlinx.android.synthetic.main.fragment_search_music.*
import org.litepal.LitePal



@Suppress("DEPRECATION")
class SearchMusicFragment : BaseFragment() {

    var adapter: SearchMusicAdapter? = null
    val inputManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_search_music.requestFocus()
        showInputMethod()

        iv_back.setOnClickListener {
            hideInputMethod()
            getFragmentManager()!!.popBackStack()
        }

        iv_delete.setOnClickListener {
            tv_search_music.setText("")
        }

        iv_search.setOnClickListener {
            val searchEditText = tv_search_music.text.toString()
            Log.i("11111111111111", searchEditText)


        }

        tv_search_music.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!tv_search_music.getText().toString().equals("")) {
                    iv_delete.setVisibility(View.VISIBLE)
                } else {
                    iv_delete.setVisibility(View.INVISIBLE)
                }
            }
        })

//        rv_search_music.layoutManager = LinearLayoutManager(context)
//        adapter = SearchMusicAdapter(context!!, R.layout.rv_local_music)
//        rv_search_music.addItemDecoration(ItemDecoration(
//                context, LinearLayoutManager.HORIZONTAL, 2, resources.getColor(R.color.grey_100p)))
//        rv_search_music.adapter = adapter


//        val list = LitePal.where("song=?", "偷功").find(Music::class.java)
//        Log.i("111111111111111111111",list[0].song!!)
//        Log.i("111111111111111111111",list[0].singer!!)

//            adapter = SearchMusicAdapter(context!!, R.layout.rv_local_music,list)
//            var list1= LitePal.find<Music>().select("song",searchEditText)


        rv_search_music.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
//        adapter = SearchMusicAdapter(context!!, R.layout.rv_local_music, list)
        adapter = SearchMusicAdapter(context!!, R.layout.rv_local_music)
        rv_search_music.addItemDecoration(ItemDecoration(
                context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, 2, resources.getColor(R.color.grey_100p)))
        rv_search_music.adapter = adapter

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
        inputManager.hideSoftInputFromWindow(tv_search_music.getWindowToken(), 0)
    }
}

