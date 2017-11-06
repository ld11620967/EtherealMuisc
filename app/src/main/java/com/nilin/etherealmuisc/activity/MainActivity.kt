package com.nilin.etherealmuisc.activity

import android.Manifest
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
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.R.id.tv_local_music
import com.nilin.etherealmuisc.R.id.viewpager
import com.nilin.etherealmuisc.fragment.LocalFragment
import com.nilin.etherealmuisc.fragment.OnlineFragment
import com.nilin.etherealmuisc.fragment.SearchMusicFragment
import com.nilin.etherealmuisc.service.PlayService
import kotlinx.android.synthetic.main.include_music_tab_bar.*
import kotlinx.android.synthetic.main.include_play_bar.*


/**
 * Created by liangd on 2017/9/19.
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

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

        if (Build.VERSION.SDK_INT >= 23) {
            //①checkSelfPermission 检查当前应用的权限
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED) {
                //②PERMISSION_DENIED说明没有权限需要手动申请
//                requestPermissions 请求权限的方法
                //第一个参数 activity
                //第二个参数 需要请求的权限的 权限String数组
                //第三个参数 请求码 用来区分不同的权限请求
                //需要注意 最后一个参数 requestCode需要>0
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                return
            }
        }
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
        iv_menu.setOnClickListener { drawer_layout.openDrawer(GravityCompat.START) }
        tv_local_music.setOnClickListener { viewpager.setCurrentItem(0) }
        tv_online_music.setOnClickListener { viewpager.setCurrentItem(1) }
        music_play_bar.setOnClickListener { startActivity(Intent(this, PlayActivity::class.java)) }
        iv_play_bar_next.setOnClickListener { playService!!.next() }
        iv_search.setOnClickListener {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, SearchMusicFragment(), null)
                    .addToBackStack(null)
                    .commit()
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
    }

    private operator fun next() {
        PlayService().next()
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

    override fun publish(progress: Int) {

    }

    override fun change() {

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

    fun changeF1(song: String, songer: String, play: Boolean) {
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
            (context as MainActivity).changeF1(song, songer, play)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        //③用户操作授权之后 会走这个回调方法 onRequestPermissionsResult
        if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {

        } else {
            Toast.makeText(this, "没有权限,无法获取音乐信息", Toast.LENGTH_LONG).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}

