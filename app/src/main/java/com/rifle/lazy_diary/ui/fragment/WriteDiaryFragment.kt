package com.rifle.lazy_diary.ui.fragment

import android.graphics.Bitmap
import com.rifle.lazy_diary.R
import com.rifle.lazy_diary.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_write_diary.*
import yalantis.com.sidemenu.interfaces.ScreenShotable

class WriteDiaryFragment : BaseFragment(), ScreenShotable {
    private val bitmap: Bitmap? = null

    override fun setLayout(): Int {
        return R.layout.fragment_write_diary
    }

    override fun getBitmap(): Bitmap {
        return bitmap!!
    }

    override fun takeScreenShot() {

    }

    override fun initView() {

    }
}