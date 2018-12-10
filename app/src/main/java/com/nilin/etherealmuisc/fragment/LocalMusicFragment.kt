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
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_local_music.*
import kotlinx.android.synthetic.main.include_app_bar.*
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.db.Music
import kotlinx.android.synthetic.main.rv_music.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litepal.LitePal
import org.litepal.extension.find


@Suppress("DEPRECATION")
class LocalMusicFragment : BaseFragment(), View.OnClickListener {

    var musicAdapter: MusicAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_local_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setTitle("本地音乐")
        toolbar.setNavigationOnClickListener(this)

        rv_local_music.layoutManager = LinearLayoutManager(context)
        musicAdapter = MusicAdapter(context!!, R.layout.rv_music)
        rv_local_music.addItemDecoration(ItemDecoration(
                context, LinearLayoutManager.HORIZONTAL, 2, resources.getColor(R.color.grey_100p)))
        rv_local_music.adapter = musicAdapter

        musicAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
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

        musicAdapter!!.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val song = adapter.data[position] as Music
            val music = Music()
            val isFavorite = LitePal.find<Music>(position + 1.toLong())!!.isFavorite

            if (view.getId() == R.id.iv_favorite) {
                if (isFavorite) {
                    music.setToDefault("isFavorite")
                    music.update(position + 1.toLong())
                    GlobalScope.launch(Dispatchers.Main) {
                            val drawable = getResources().getDrawable(R.drawable.btn_not_favorite_gray)
                            view.iv_favorite.setImageDrawable(drawable)
                        Log.i("1111111111","111111111111")
                    }
                } else {
                    music.isFavorite = true
                    music.update(position + 1.toLong())
                    GlobalScope.launch(Dispatchers.Main) {
                            val drawable = getResources().getDrawable(R.drawable.btn_favorite)
                            view.iv_favorite.setImageDrawable(drawable)
                        Log.i("22222222222222","2222222222222")
                    }
                }
            } else if (view.getId() == R.id.iv_more) {
                val dialog = AlertDialog.Builder(getContext()!!)
                dialog.setTitle(song.song)
                dialog.setItems(R.array.local_music_dialog, DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        0// 分享
                        -> Log.i("111111111111111111", "000000000000000")
//                    -> shareMusic(music)
                        1// 设为铃声
                        -> Log.i("1111111111111111111", "11111111111111")
//                    -> requestSetRingtone(music)
                        2// 查看歌曲信息
                        -> Log.i("111111111111111111", "222222222222222")
//                    -> musicInfo(music)
                        3// 删除
                        -> Log.i("111111111111111111", "3333333333333")
//                    -> deleteMusic(music)
                    }
                })
                dialog.show()
            }
        }
    }

    override fun onClick(p0: View?) {
        getFragmentManager()!!.popBackStack();
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

