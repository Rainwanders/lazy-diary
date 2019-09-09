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
import com.rifle.lazy_diary.viewmodel.MainViewModel
import com.rifle.lazy_diary.widgets.StatusSelector
import yalantis.com.sidemenu.interfaces.ScreenShotable

class MainFragment : Fragment(), ScreenShotable {
    private lateinit var mBinding: FragmentMainBinding
    private val mModel = MainViewModel()


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_main, container, false)
        mBinding.mainModel = mModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {
        setClickEvent()
        setSelectListener()
        mBinding.moodSelector.setImageList(mModel.mMoodList)
        mBinding.spiritSelector.setImageList(mModel.mSpiritList)
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
                        mBinding.mainModel?.setMoodImage(index)
                    }
                })

        mBinding.spiritSelector.setSelectListener(
                object : StatusSelector.OnSelectListener {
                    override fun select(index: Int) {
                        mBinding.mainModel?.setSpiritImage(index)
                    }
                })
    }

    override fun getBitmap(): Bitmap? = null
    override fun takeScreenShot() {}
}