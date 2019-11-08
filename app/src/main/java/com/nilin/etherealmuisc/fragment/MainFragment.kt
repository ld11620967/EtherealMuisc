package com.nilin.etherealmuisc.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.fragment_local.*
import com.nilin.etherealmuisc.MyApplication


/**
* Created by liangd on 2017/9/19.
*/
class MainFragment : androidx.fragment.app.Fragment() {

    val context = MyApplication.instance
    val localMusicFragment = LocalMusicFragment()
    val FavoriteFragment = FavoriteFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_local, container, false)
    }

    //Fragment 点击事件
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bt_local_music.setOnClickListener {
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, localMusicFragment, null)
                    .addToBackStack(null)
                    .commit()
        }

        bt_my_favorite.setOnClickListener {
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, FavoriteFragment, null)
                    .addToBackStack(null)
                    .commit()
        }
    }
}
