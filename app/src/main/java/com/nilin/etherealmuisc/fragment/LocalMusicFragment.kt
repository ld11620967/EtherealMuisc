package com.nilin.etherealmuisc.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.adapter.MusicAdapter
import com.nilin.etherealmuisc.Music
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_local_music.*
import kotlinx.android.synthetic.main.include_app_bar.*
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log
import com.nilin.etherealmuisc.MyApplication


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
            val song = adapter.data[position] as Music
            playService!!.prepare(song.path!!)

            val editor = MyApplication.instance!!.getSharedPreferences("music_pref", Context.MODE_PRIVATE).edit()
            editor.putString("song", song.song)
            editor.putInt("position", position)
            editor.apply()

            val intent = Intent("com.nilin.etherealmusic.play")
            intent.putExtra("song", song.song)
            intent.putExtra("singer", song.singer)
            intent.putExtra("position", position)
            context.sendBroadcast(intent)
            playService!!.start()
        }

        adapter!!.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, _, position ->
            val song = adapter.data[position] as Music
            val dialog = AlertDialog.Builder(getContext())
            dialog.setTitle(song.song)
            Log.i("111111111111111111","000000000000000")
            dialog.setItems(R.array.local_music_dialog, DialogInterface.OnClickListener { _, which ->
                when (which) {
                    0// 分享
                    -> Log.i("111111111111111111","000000000000000")
//                    -> shareMusic(music)
                    1// 设为铃声
                    -> Log.i("1111111111111111111","11111111111111")
//                    -> requestSetRingtone(music)
                    2// 查看歌曲信息
                    -> Log.i("111111111111111111","222222222222222")
//                    -> musicInfo(music)
                    3// 删除
                    -> Log.i("111111111111111111","3333333333333")
//                    -> deleteMusic(music)
                }
            })
            dialog.show()
        }
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
