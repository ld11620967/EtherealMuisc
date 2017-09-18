package com.nilin.etherealmuisc.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


/**
* Created by nilin on 2017/9/9.
*/
class FragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    private val mFragments = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments.get(position)
    }

    override fun getCount(): Int {
        return mFragments.size
    }

}
