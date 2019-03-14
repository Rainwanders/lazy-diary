package com.rifle.lazy_diary.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.rifle.lazy_diary.R
import com.rifle.lazy_diary.ui.base.BaseActivity
import com.rifle.lazy_diary.ui.fragment.Test
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : BaseActivity() {
    override fun setLayout(): Int {
        return R.layout.activity_main2
    }

    override fun initView() {

        val titleList = ArrayList<String>()
        titleList.add("今日总结")
        titleList.add("明日计划")
        titleList.add("往日流沙")
        titleList.add("大事件")
        titleList.add("超级大事件")
//        titleList.add("关于作者")

        main_container.setTitles(titleList)
        main_container.setMenuIcon(R.drawable.menu)

        setAdapter()
    }

    override fun setOnclick() {

    }

    private fun setAdapter() {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(Test().getInstance(R.drawable.icon_activity))
        fragmentList.add(Test().getInstance(R.drawable.icon_brush))
        fragmentList.add(Test().getInstance(R.drawable.icon_collection))
        fragmentList.add(Test().getInstance(R.drawable.icon_flag_purple))
        fragmentList.add(Test().getInstance(R.drawable.icon_remind))
//        fragmentList.add(Test().getInstance(R.drawable.icon_music))

        val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int): Fragment {
                return fragmentList[p0]
            }

            override fun getCount(): Int {
                return fragmentList.size
            }
        }

        main_container.setAdapter(adapter)
    }
}