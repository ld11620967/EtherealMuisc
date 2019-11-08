package com.nilin.etherealmuisc.activity

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import android.view.MenuItem
import com.nilin.etherealmuisc.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.viewpager.widget.ViewPager
import android.widget.Toast
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.fragment.MainFragment
import com.nilin.etherealmuisc.fragment.SearchMusicFragment
import com.nilin.etherealmuisc.fragment.FavoriteFragment
import com.nilin.etherealmuisc.service.PlayService
import kotlinx.android.synthetic.main.include_music_tab_bar.*
import kotlinx.android.synthetic.main.include_play_bar.*
import androidx.appcompat.app.AlertDialog
import com.nilin.etherealmuisc.receiver.HeadsetButtonReceiver1
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast


/**
 * Created by liangd on 2017/9/19.
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener, HeadsetButtonReceiver1.onHeadsetListener {

    val context = MyApplication.instance
    private var lastBackPress: Long = 0
    var headsetButtonReceiver1: HeadsetButtonReceiver1? = null
    var job: Job? = null
    var path: String? = null
    var firstStart: Boolean? = null
    val searchMusicFragment = SearchMusicFragment()
    val favoriteFragment = FavoriteFragment()
    val localFragment = MainFragment()
    val localMusicFragment = localFragment.localMusicFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(localFragment)
//在线音乐（布局文件：include_music_tab_bar.xml）
//        adapter.addFragment(OnlineFragment())
        viewpager.setAdapter(adapter)
        tv_local_music.setSelected(true)
        nav_view.setNavigationItemSelectedListener(this)
        viewpager.addOnPageChangeListener(this)
        clickListener()
        startService(Intent(this, PlayService::class.java))
        val song = intent.getStringExtra("song")
        val singer = intent.getStringExtra("singer")
        path = intent.getStringExtra("path")
        firstStart = intent.getBooleanExtra("firstStart", false)
        if (song != null || singer != null || path != null) {
            changeF1(song, singer)
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.nilin.etherealmusic.play")
        intentFilter.addAction("com.nilin.etherealmusic.isPlaying")
        registerReceiver(broadcastReceiver, intentFilter)
        HeadsetButtonReceiver1(this)

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED) {
                //requestPermissions 请求权限的方法
                //第一个参数 activity
                //第二个参数 需要请求的权限的 权限String数组
                //第三个参数 请求码 用来区分不同的权限请求
                //需要注意 最后一个参数 requestCode需要>0
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                return
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position == 0) {
            tv_local_music.setSelected(true)
//在线音乐
//            tv_online_music.setSelected(false)
        } else {
            tv_local_music.setSelected(false)
//在线音乐
//            tv_online_music.setSelected(true)
        }
    }

    fun clickListener() {
        iv_menu.setOnClickListener { drawer_layout.openDrawer(GravityCompat.START) }
        tv_local_music.setOnClickListener { viewpager.setCurrentItem(0) }
//在线音乐
//        tv_online_music.setOnClickListener { viewpager.setCurrentItem(1) }
        music_play_bar.setOnClickListener { startActivity(Intent(this, PlayActivity::class.java)) }
        iv_play_bar_next.setOnClickListener { playService!!.next() }
        iv_search.setOnClickListener {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, searchMusicFragment, null)
                    .addToBackStack(null)
                    .commit()
        }
        iv_play_bar_play.setOnClickListener {
            if (firstStart!!) {
                if (path != null) {
                    playService!!.prepare(path!!)
                    playService!!.start()
                    iv_play_bar_play.isSelected = true
                    firstStart = false
                }
            } else {
                if (playService!!.isPlaying) {
                    playService!!.pause()
                    iv_play_bar_play.isSelected = false
                } else {
                    playService!!.start()
                    iv_play_bar_play.isSelected = true
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_search_music -> {
                startActivity(Intent(this, ScanMusicActivity::class.java))
            }
            R.id.nav_time_stop -> {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setIcon(R.drawable.ic_time_stop1)
                builder.setTitle("定时停止播放")
                val color = arrayOf("不开启", "10分钟", "20分钟", "30分钟", "45分钟", "60分钟", "90分钟")
                builder.setItems(color) { _, which ->
                    when (which) {
                        0 -> {
                            TimeStop(0)
                        }
                        1 -> {
                            TimeStop(10)
                        }
                        2 -> {
                            TimeStop(20)
                        }
                        3 -> {
                            TimeStop(30)
                        }
                        4 -> {
                            TimeStop(45)
                        }
                        5 -> {
                            TimeStop(60)
                        }
                        6 -> {
                            TimeStop(90)
                        }
                    }
                }
                builder.show()
            }
            R.id.nav_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                intent.putExtra("AudioSessionId", playService!!.getAudioSessionId())
                startActivity(intent)
            }
            R.id.nav_about -> {
                Toast.makeText(this, "空灵音乐", Toast.LENGTH_LONG).show()
            }
            R.id.nav_exit -> {
//                System.exit(0)
                android.os.Process.killProcess(android.os.Process.myPid())
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun publish(progress: Int) {
    }

    override fun change() {
    }

    fun changeF1(song: String, singer: String) {
        tv_play_bar_title.text = song
        tv_play_bar_artist.text = singer
    }

    fun changeF2(isPlaying: Boolean) {
        iv_play_bar_play.isSelected = isPlaying
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    var broadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action.equals("com.nilin.etherealmusic.play")) {
                val song = intent.getStringExtra("song")
                val singer = intent.getStringExtra("singer")
                (context as MainActivity).changeF1(song, singer)
            }
            if (action.equals("com.nilin.etherealmusic.isPlaying")) {
                val isPlaying = intent.getBooleanExtra("isPlaying", false)
                (context as MainActivity).changeF2(isPlaying)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {

        } else {
            Toast.makeText(this, "没有权限,无法获取音乐信息", Toast.LENGTH_LONG).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun TimeStop(time: Long) {
        if (time.toInt() == 0 && job == null) {
            Toast.makeText(this, "停止播放功能未开启", Toast.LENGTH_SHORT).show()
        } else if (time.toInt() != 0) {
            Toast.makeText(this, "$time 分钟后停止播放", Toast.LENGTH_SHORT).show()
            job = GlobalScope.launch {
                delay(1000L)
                playService!!.pause()
            }
        } else {
            job!!.cancel()
            Toast.makeText(this, "已取消定时停止播放", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (localMusicFragment.isVisible || searchMusicFragment.isVisible || favoriteFragment.isVisible) {
            super.onBackPressed()
        } else {
            val time = System.currentTimeMillis()
            if (time - lastBackPress < 2000) {
//                    System.exit(0)
                android.os.Process.killProcess(android.os.Process.myPid())
            } else {
                lastBackPress = time
                Toast.makeText(context, "再按一次退出", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun playOrPause() {
        playService!!.pause()
    }

    override fun playNext() {
        playService!!.next()
    }

    override fun playPrevious() {
        playService!!.previous()
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
        unregisterReceiver(broadcastReceiver)
        unregisterReceiver(headsetButtonReceiver1)
        unbindPlayService()//解绑服务
    }
}

