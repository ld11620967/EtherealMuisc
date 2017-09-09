package com.nilin.etherealmuisc.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.nilin.etherealmuisc.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_music_include.*
import android.content.Intent
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.fragment.LocalFragment
import com.nilin.etherealmuisc.fragment.OnlineFragment
import kotlinx.android.synthetic.main.include_music_tab_bar.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        nav_view.setNavigationItemSelectedListener(this)
        iv_menu.setOnClickListener {drawer_layout.openDrawer(GravityCompat.START)  }
        tv_local_music.setOnClickListener {viewpager.setCurrentItem(0)  }
        tv_online_music.setOnClickListener {viewpager.setCurrentItem(1)  }
        iv_search.setOnClickListener {startActivity(Intent(this, SearchMusicActivity::class.java))  }

    }

    private fun initView() {
        val adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(LocalFragment())
        adapter.addFragment(OnlineFragment())
        viewpager.setAdapter(adapter)
        tv_local_music.setSelected(true)
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

            }
            R.id.nav_time_stop -> {

            }
            R.id.nav_settings -> {

            }
            R.id.nav_about -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

