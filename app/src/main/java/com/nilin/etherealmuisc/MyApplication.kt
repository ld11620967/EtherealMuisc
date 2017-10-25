package com.nilin.etherealmuisc

import android.app.Application
import com.nilin.etherealmuisc.greendao.DaoMaster
import com.nilin.etherealmuisc.greendao.MusicDao


/**
* Created by nilin on 2017/9/16.
*/
class MyApplication : Application(){

    var mMusicDao: MusicDao? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDao()
    }

    fun initDao() {
        val helper = DaoMaster.DevOpenHelper(this, "note.db", null)
        val db = helper.writableDatabase
        val daoMaster = DaoMaster(db)
        val daoSession = daoMaster.newSession()
        mMusicDao = daoSession.musicDao
    }

    open fun getMusicDao(): MusicDao {
        return mMusicDao!!
    }

    companion object {
        // 因为我们程序运行后，Application是首先初始化的，如果在这里不用判断instance是否为空
        var instance: MyApplication? = null
            private set
    }
}