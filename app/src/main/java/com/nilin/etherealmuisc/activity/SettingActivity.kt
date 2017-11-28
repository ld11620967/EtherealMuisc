package com.nilin.etherealmuisc.activity

import android.text.TextUtils
import android.preference.Preference
import android.content.ActivityNotFoundException
import android.media.audiofx.AudioEffect
import android.content.Intent
import android.os.Bundle
import com.nilin.etherealmuisc.service.PlayService
import android.preference.PreferenceFragment
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.nilin.etherealmuisc.MyApplication
import com.nilin.etherealmuisc.R
import com.nilin.etherealmuisc.utils.MusicUtils
import kotlinx.android.synthetic.main.include_app_bar.*


/**
 * Created by liangd on 2017/11/27.
 */
class SettingActivity : BaseActivity() {

    val context = MyApplication.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar.title = "设置"
        setSupportActionBar(toolbar)
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        }

        val settingFragment = SettingFragment()
        settingFragment.setPlayService(playService)
        if (playService == null) {
            Log.i("1111111111111", "playService111111111111====================null")
        }
        fragmentManager
                .beginTransaction()
                .replace(R.id.ll_fragment_container, settingFragment)
                .commit()
    }

    class SettingFragment : PreferenceFragment(), Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

        val context = MyApplication.instance
        private var mSoundEffect: Preference? = null
        private var mFilterSize: Preference? = null
        private var mFilterTime: Preference? = null
        private var mPlayService: PlayService? = null

        fun setPlayService(playService: PlayService?) {
            this.mPlayService = playService
            if (mPlayService == null) {
                Log.i("1111111111111", "playService22222222222222====================null")
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preference_setting)

            mSoundEffect = findPreference(getString(R.string.setting_key_sound_effect))
            mFilterSize = findPreference(getString(R.string.setting_key_filter_size))
            mFilterTime = findPreference(getString(R.string.setting_key_filter_time))
            mSoundEffect!!.onPreferenceClickListener = this
            mFilterSize!!.onPreferenceChangeListener = this
            mFilterTime!!.onPreferenceChangeListener = this
//
//            mFilterSize!!.summary = getSummary(Preferences.getFilterSize(), R.array.filter_size_entries, R.array.filter_size_entry_values)
//            mFilterTime!!.summary = getSummary(Preferences.getFilterTime(), R.array.filter_time_entries, R.array.filter_time_entry_values)
        }

        override fun onPreferenceClick(preference: Preference): Boolean {
            if (preference === mSoundEffect) {
                startEqualizer()
                return true
            }
            return false
        }

        private fun startEqualizer() {
            if (MusicUtils.isAudioControlPanelAvailable(activity)) {
                val intent = Intent()
                val packageName = activity.packageName
                intent.action = AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL
                intent.putExtra(AudioEffect.EXTRA_PACKAGE_NAME, packageName)
                intent.putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.CONTENT_TYPE_MUSIC)
                Log.i("1111111111111", "111111111111111111111")
                intent.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, mPlayService!!.getAudioSessionId())
                Log.i("1111111111111", mPlayService!!.getAudioSessionId().toString())
                try {
                    startActivityForResult(intent, 1)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(context, "设备不支持", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "设备不支持", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
            if (preference === mFilterSize) {
//                Preferences.saveFilterSize(newValue as String)
//                mFilterSize!!.summary = getSummary(Preferences.getFilterSize(), R.array.filter_size_entries, R.array.filter_size_entry_values)
//                onFilterChanged()
                return true
            } else if (preference === mFilterTime) {
//                Preferences.saveFilterTime(newValue as String)
//                mFilterTime!!.summary = getSummary(Preferences.getFilterTime(), R.array.filter_time_entries, R.array.filter_time_entry_values)
//                onFilterChanged()
                return true
            }
            return false
        }

        fun getSummary(value: String, entries: Int, entryValues: Int): String {
            val entryArray = resources.getStringArray(entries)
            val entryValueArray = resources.getStringArray(entryValues)
            for (i in entryValueArray.indices) {
                val v = entryValueArray[i]
                if (TextUtils.equals(v, value)) {
                    return entryArray[i]
                }
            }
            return entryArray[0]
        }

//        private fun onFilterChanged() {
//            showProgress()
//            mPlayService!!.stop()
//            mPlayService!!.updateMusicList(object : EventCallback<Void> {
//                fun onEvent(aVoid: Void) {
//                    cancelProgress()
//                    val listener = mPlayService!!.getOnPlayEventListener()
//                    if (listener != null) {
//                        listener!!.onChange(mPlayService!!.getPlayingMusic())
//                    }
//                }
//            })
//        }

//        private fun showProgress() {
//            if (mProgressDialog == null) {
//                mProgressDialog = ProgressDialog(activity)
//                mProgressDialog!!.setMessage("正在扫描音乐")
//            }
//            if (!mProgressDialog!!.isShowing) {
//                mProgressDialog!!.show()
//            }
//        }
//
//        private fun cancelProgress() {
//            if (mProgressDialog != null && mProgressDialog!!.isShowing) {
//                mProgressDialog!!.cancel()
//            }
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
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

}