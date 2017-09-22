package com.nilin.etherealmuisc.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nilin.etherealmuisc.R
import kotlinx.android.synthetic.main.fragment_local.*
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.activity.LocalMusicActivity


/**
* Created by liangd on 2017/9/19.
*/
class LocalFragment : Fragment() {

    var context = MyApplication.instance

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_local, container, false)
    }

    //Fragment 点击事件
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bt_local_music.setOnClickListener {

            activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, LocalMusicFragment(), null)
                    .addToBackStack(null)
                    .commit()

//        bt_local_music.setOnClickListener { startActivity(Intent(context, LocalMusicActivity::class.java)) }
        }

    }

}
