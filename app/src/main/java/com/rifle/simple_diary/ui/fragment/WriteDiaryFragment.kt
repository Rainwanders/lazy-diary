package com.rifle.simple_diary.ui.fragment

import android.graphics.Bitmap
import com.rifle.simple_diary.R
import com.rifle.simple_diary.ui.base.BaseFragment
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
}