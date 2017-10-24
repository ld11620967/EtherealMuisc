package com.nilin.etherealmuisc.activity

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.nilin.etherealmuisc.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.fragment.LocalFragment
import com.nilin.etherealmuisc.fragment.LocalMusicFragment
import com.nilin.etherealmuisc.fragment.OnlineFragment
import com.nilin.etherealmuisc.fragment.SearchMusicFragment
import com.nilin.etherealmuisc.service.PlayService
import kotlinx.android.synthetic.main.include_music_tab_bar.*
import kotlinx.android.synthetic.main.include_play_bar.*


/**
 * Created by liangd on 2017/9/19.
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    override fun publish(progress: Int) {

    }

    override fun change() {

    }

    val context = MyApplication.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        nav_view.setNavigationItemSelectedListener(this)
        viewpager.addOnPageChangeListener(this)
        clickListener()
        startService(Intent(this, PlayService::class.java))

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.nilin.etherealmusic.play")
        registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun initView() {
        val adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(LocalFragment())
        adapter.addFragment(OnlineFragment())
        viewpager.setAdapter(adapter)
        tv_local_music.setSelected(true)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position == 0) {
            tv_local_music.setSelected(true)
            tv_online_music.setSelected(false)
        } else {
            tv_local_music.setSelected(false)
            tv_online_music.setSelected(true)
        }
    }

    fun clickListener() {
        iv_menu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }
        tv_local_music.setOnClickListener { viewpager.setCurrentItem(0) }
        tv_online_music.setOnClickListener { viewpager.setCurrentItem(1) }
        iv_search.setOnClickListener {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, SearchMusicFragment(), null)
                    .addToBackStack(null)
                    .commit()
        }
        music_play_bar.setOnClickListener {
            //            if (playService!!.isPlaying) {
//                playActivity!!.player_pause()
//            } else {
//                playActivity!!.player_start()
//            }

            startActivity(Intent(this, PlayActivity::class.java))
//            val intent = Intent(this, PlayActivity::class.java)
//            intent.putExtra("isRefreshing", 1)
//            startActivity(intent)
        }
        iv_play_bar_play.setOnClickListener {
            if (!playService!!.isPlaying) {
                playService!!.start()
                iv_play_bar_play.setBackgroundResource(R.drawable.ic_play_bar_btn_pause)
            } else {
                playService!!.pause()
                iv_play_bar_play.setBackgroundResource(R.drawable.ic_play_bar_btn_play)
            }
        }
        iv_play_bar_next.setOnClickListener { next() }
    }


//    private fun doFragmentReceive(context: Context, intent: Intent) {
//        val action = intent.action
//        if (action == FragmentReceiver.ACTION_OPENLOCALMUSICFRAGMENT) {
//            //打开本地音乐
//            mFragmentListener.openFragment(LocalMusicFragment())
//        }
//        else if (action == FragmentReceiver.ACTION_OPENLIKEMUSICFRAGMENT) {
//            //打开喜欢
//            mFragmentListener.openFragment(LikeMusicFragment())
//        } else if (action == FragmentReceiver.ACTION_OPENRECENTMUSICFRAGMENT) {
//            //打开最近
//            mFragmentListener.openFragment(RecentMusicFragment())
//        }
//    }


//    private fun play() {
//        playService!!.play()
//        if (playService!!.isPlaying) {
//            iv_play_bar_play.setBackgroundResource(R.drawable.ic_play_bar_btn_play)
//        } else {
//            iv_play_bar_play.setBackgroundResource(R.drawable.ic_play_bar_btn_pause)
//        }
//    }

    private operator fun next() {
//        PlayService().next()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_search_music -> {
                startActivity(Intent(this, ScanMusicActivity::class.java))
            }
            R.id.nav_time_stop -> {

            }
            R.id.nav_settings -> {

            }
            R.id.nav_about -> {
                Toast.makeText(this, "空灵音乐", Toast.LENGTH_LONG).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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

    fun changeF2(song: String, songer: String, play: Boolean) {
        tv_play_bar_title.text = song
        tv_play_bar_artist.text = songer
        if (play) {
            iv_play_bar_play.isSelected = true
        } else {
            iv_play_bar_play.isSelected = false
        }
    }

    var broadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val song = intent.getStringExtra("song")
            val songer = intent.getStringExtra("songer")
            val play = intent.getBooleanExtra("play", false)
            (context as MainActivity).changeF2(song, songer, play)
        }
    }

}
