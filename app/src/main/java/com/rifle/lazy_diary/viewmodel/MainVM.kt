package com.rifle.lazy_diary.viewmodel

import android.animation.ObjectAnimator
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

class MainVM(viewList: ArrayList<View>) {

    private var mLeftMargin: Float = 0f

    var percent: Float = 0f

    private var mViewList: ArrayList<View> = viewList
    private var mParamsList: ArrayList<ConstraintLayout.LayoutParams>

    init {
        mParamsList = ArrayList(mViewList.size)
        mViewList.forEachIndexed { index, view ->
            mParamsList[index] = view.layoutParams as ConstraintLayout.LayoutParams
        }
    }


    fun startAnim() {
        val anim = ObjectAnimator.ofFloat(this, "marginPercent", 0f, 1f)
        anim.duration = 500
        anim.start()
    }

    fun setMarginPercent(marginPercent: Float) {
        mParamsList.forEachIndexed { index, params ->
            params.leftMargin = (16 + 48 * index * marginPercent).toInt()
        }

        mViewList[0].parent.requestLayout()
    }
}