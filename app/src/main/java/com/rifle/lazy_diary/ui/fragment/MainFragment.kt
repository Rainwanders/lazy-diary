package com.rifle.lazy_diary.ui.fragment

import android.animation.ObjectAnimator
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.rifle.lazy_diary.R
import com.rifle.lazy_diary.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    private var mMindMargin = 0
    private lateinit var mMindParam: FrameLayout.LayoutParams

    override fun setLayout(): Int {
        return R.layout.fragment_main
    }

    override fun initView() {

    }

//    private fun initAnim() {
//        mMindParam = imageView.layoutParams as FrameLayout.LayoutParams
//        val mindAnim = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 200f)
//        mindAnim.duration = 300
//        imageView.setOnClickListener {
//            mindAnim.start()
//        }
//    }
}