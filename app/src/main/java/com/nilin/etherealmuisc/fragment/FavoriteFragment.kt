package com.nilin.etherealmuisc.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_local_music.*
import kotlinx.android.synthetic.main.include_app_bar.*
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.adapter.FavoriteMusicAdapter
import com.nilin.etherealmuisc.db.Music
import kotlinx.android.synthetic.main.rv_music.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.litepal.LitePal
import org.litepal.extension.find


@Suppress("DEPRECATION")
class FavoriteFragment : BaseFragment(), View.OnClickListener {

    var favoriteMusicAdapter: FavoriteMusicAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_local_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setTitle("我的最爱")
        toolbar.setNavigationOnClickListener(this)

        rv_local_music.layoutManager = LinearLayoutManager(context)
        favoriteMusicAdapter = FavoriteMusicAdapter(context!!, R.layout.rv_music)
        rv_local_music.addItemDecoration(ItemDecoration(
                context, LinearLayoutManager.HORIZONTAL, 2, resources.getColor(R.color.grey_100p)))
        rv_local_music.adapter = favoriteMusicAdapter

        favoriteMusicAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
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

        favoriteMusicAdapter!!.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val musicList = adapter.data[position] as Music
            val music = Music()
            val isFavoriteMusicList = LitePal.where("isFavorite>?", "0").find<Music>()
            val isFavorite = isFavoriteMusicList[position].isFavorite
            val path = isFavoriteMusicList[position].path

            if (view.getId() == R.id.iv_favorite) {
                if (isFavorite) {
                    music.setToDefault("isFavorite")
                    music.updateAll("path=?", path)
                    GlobalScope.launch(Dispatchers.Main) {
                        val drawable = getResources().getDrawable(R.drawable.btn_not_favorite_gray)
                        view.iv_favorite.setImageDrawable(drawable)
                        launch {
                            delay(120)
                            adapter.remove(position)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            } else if (view.getId() == R.id.iv_more) {
                val dialog = AlertDialog.Builder(getContext()!!)
                dialog.setTitle(musicList.song)
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
