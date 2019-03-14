package com.rifle.lazy_diary.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.rifle.lazy_diary.R
import com.rifle.lazy_diary.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_test.*
import yalantis.com.sidemenu.interfaces.ScreenShotable

class Test : BaseFragment(), ScreenShotable {
    private val bitmap: Bitmap? = null

    fun getInstance(res: Int) : Test{
        val test = Test()
        val data = Bundle()
        data.putInt("image", res)
        test.arguments = data
        return test
    }


    override fun setLayout(): Int {
        return R.layout.fragment_test
    }


    override fun initView() {
        val data = arguments
        val imageRes = data?.getInt("image") ?: 0
        test_images.setImageResource(imageRes)
    }



    override fun getBitmap(): Bitmap {
        return bitmap!!
    }

    override fun takeScreenShot() {

    }
}