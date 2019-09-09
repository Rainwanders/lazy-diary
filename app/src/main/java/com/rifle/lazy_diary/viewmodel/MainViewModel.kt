package com.rifle.lazy_diary.viewmodel

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifle.lazy_diary.R

class MainViewModel : ViewModel() {

    var moodImage = MutableLiveData<Int>()
    var spiritImage = MutableLiveData<Int>()

    lateinit var mMoodList: ArrayList<Int>
    lateinit var mSpiritList: ArrayList<Int>

    init {
        moodImage.value = R.drawable.ic_mood_normal
        spiritImage.value = R.drawable.ic_spirit_boring

        initStatusList()
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

    @BindingAdapter("android:src")
    fun setMoodImage(index: Int) {
        moodImage.value = mMoodList[index]
    }

    fun setSpiritImage(index: Int) {
        spiritImage.value = mSpiritList[index]
    }
}