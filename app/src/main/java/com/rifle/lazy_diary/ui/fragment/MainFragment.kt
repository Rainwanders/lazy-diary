package com.rifle.lazy_diary.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rifle.lazy_diary.R
import com.rifle.lazy_diary.databinding.FragmentMainBinding
import com.rifle.lazy_diary.widgets.StatusSelector
import yalantis.com.sidemenu.interfaces.ScreenShotable

class MainFragment : Fragment(), ScreenShotable {
    private lateinit var mBinding: FragmentMainBinding
    private lateinit var mMoodList: ArrayList<Int>
    private lateinit var mSpiritList: ArrayList<Int>


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_main, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {
        initStatusList()
        setClickEvent()
        setSelectListener()
        mBinding.moodSelector.setImageList(mMoodList)
        mBinding.spiritSelector.setImageList(mSpiritList)
    }


    private fun initStatusList() {
        mMoodList = arrayListOf(
                R.drawable.ic_mood_normal,
                R.drawable.ic_mood_happy,
                R.drawable.ic_mood_angry,
                R.drawable.ic_mood_sad,
                R.drawable.ic_mood_cry
        )

        mSpiritList = arrayListOf(
                R.drawable.ic_spirit_boring,
                R.drawable.ic_spirit_cute,
                R.drawable.ic_spirit_fighting,
                R.drawable.ic_spirit_faint,
                R.drawable.ic_spirit_sleepy
        )
    }

    private fun setClickEvent() {
        mBinding.currentMood.setOnClickListener {
            mBinding.moodSelector.startExpandAnim()
        }

        mBinding.currentSpirit.setOnClickListener {
            mBinding.spiritSelector.startExpandAnim()
        }
    }


    private fun setSelectListener() {
        mBinding.moodSelector.setSelectListener(
                object : StatusSelector.OnSelectListener {
                    override fun select(index: Int) {
                        mBinding.currentMood.setImageResource(mMoodList[index])
                    }
                })

        mBinding.spiritSelector.setSelectListener(
                object : StatusSelector.OnSelectListener {
                    override fun select(index: Int) {
                        mBinding.currentSpirit.setImageResource(mSpiritList[index])
                    }
                })
    }

    override fun getBitmap(): Bitmap? = null
    override fun takeScreenShot() {}
}