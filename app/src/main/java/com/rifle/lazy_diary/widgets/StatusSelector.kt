package com.rifle.lazy_diary.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import com.rifle.lazy_diary.R

class StatusSelector : ViewGroup {

    private lateinit var mContext: Context

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mIndicatorPoint = 0

    private val mPositionList: List<IntArray> = ArrayList()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defaults: Int)
            : super(context, attrs, defaults) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context
    }

    private fun addImage() {
        val imageView = ImageView(mContext)
        imageView.setImageResource(R.drawable.ic_status_normal)
        this.addView(imageView)
        val layoutParams = imageView.layoutParams
        layoutParams.width = mHeight
        layoutParams.height = mHeight
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        addImage()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            getChildAt(i).layout(0, 0, 0, 0)
        }
    }

}