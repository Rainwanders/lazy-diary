package com.rifle.lazy_diary.widgets

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class StatusSelector : ViewGroup {

    private lateinit var mContext: Context

    private var mWidth = 0
    private var mHeight = 0

    /**
     * 图片尺寸大小
     */
    private var mImageSize = 0

    /**
     * 图片尺寸加上图片间尺寸
     */
    private var mWithMarginSize = 0

    /**
     * 属性动画的标尺
     */
    private var mIndicator = 0f

    /**
     * 是否已经添加了子View图片
     */
    private var mIsViewAdded = false

    /**
     * 展开属性动画
     */
    private var mAnimator: ObjectAnimator? = null

    /**
     * 图片间间隔尺寸
     */
    private val mInterval = 20

    /**
     * 图片list
     */
    private lateinit var mImageList: ArrayList<Int>

    /**
     * 选择图片监听
     */
    private var mSelectListener: OnSelectListener? = null

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


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = getFinalSize(300, widthMeasureSpec)
        mHeight = getFinalSize(100, heightMeasureSpec)

        mImageSize = (mWidth - 4 * mInterval) / 5
        mHeight = mImageSize
        mWithMarginSize = mImageSize + mInterval
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val left = (mIndicator * mWithMarginSize * i).toInt()
            val right = mImageSize + left
            getChildAt(i).layout(left, 0, right, mHeight)
        }
    }


    /**
     * 懒加载动画
     */
    private fun initAnimator() {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(this, "indicator", 0f)
            mAnimator?.duration = 500
        }
    }


    /**
     * 启动展开动画
     */
    fun startExpandAnim() {
        if (!mIsViewAdded) {
            addImages()
        }
        initAnimator()
        mAnimator?.setFloatValues(0f, 1f)
        mAnimator?.start()
        visibility = View.VISIBLE
    }


    /**
     * 设置选择监听
     */
    fun setSelectListener(mListener: OnSelectListener) {
        mSelectListener = mListener
    }


    /**
     * 添加图片
     */
    private fun addImages() {
        mIsViewAdded = true
        mImageList.forEachIndexed { index, image ->
            val imageView = ImageView(mContext)
            imageView.setImageResource(image)
            this.addView(imageView)
            val params = imageView.layoutParams
            params.width = mImageSize
            params.height = mImageSize
            imageView.layoutParams = params

            imageView.setOnClickListener {
                initAnimator()
                mSelectListener?.select(index)
                mAnimator?.setFloatValues(1f, 0f)
                mAnimator?.start()
                visibility = View.GONE
            }
        }
    }


    /**
     * 获取最终的尺寸
     */
    private fun getFinalSize(defaults: Int, measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        return if (mode == MeasureSpec.EXACTLY) size else defaults
    }


    /**
     * 添加图片list
     */
    fun setImageList(imageList: List<Int>) {
        this.mImageList = imageList as ArrayList<Int>
    }


    /**
     * 属性动画标尺
     */
    private fun setIndicator(indicator: Float) {
        this.mIndicator = indicator
        this.alpha = indicator
        requestLayout()
    }


    /**
     * 选择监听回调接口
     */
    interface OnSelectListener {
        fun select(index: Int)
    }
}