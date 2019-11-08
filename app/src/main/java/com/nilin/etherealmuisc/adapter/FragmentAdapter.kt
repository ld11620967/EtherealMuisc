package com.nilin.etherealmuisc.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * Created by nilin on 2017/9/9.
 */
class FragmentAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    private val mFragments = ArrayList<androidx.fragment.app.Fragment>()

    fun addFragment(fragment: androidx.fragment.app.Fragment) {
        mFragments.add(fragment)
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return mFragments.get(position)
    }

    override fun getCount(): Int {
        return mFragments.size
    }

}
