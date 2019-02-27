package com.rifle.simple_diary.ui.activity

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.rifle.simple_diary.R
import com.rifle.simple_diary.app.ApiLogic
import com.rifle.simple_diary.app.Constants
import com.rifle.simple_diary.model.ConfigBean
import com.rifle.simple_diary.model.WeatherBean
import com.rifle.simple_diary.ui.base.BaseActivity
import com.rifle.simple_diary.ui.fragment.ContentFragment
import com.rifle.simple_diary.ui.fragment.WriteDiaryFragment
import com.rifle.simple_diary.utils.DbUtils
import com.rifle.simple_diary.utils.LocationUtils
import com.rifle.simple_diary.utils.SimpleCallback
import com.rifle.simple_diary.utils.UIUtils
import io.codetail.animation.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import yalantis.com.sidemenu.interfaces.Resourceble
import yalantis.com.sidemenu.interfaces.ScreenShotable
import yalantis.com.sidemenu.model.SlideMenuItem
import yalantis.com.sidemenu.util.ViewAnimator


class MainActivity : BaseActivity(), ViewAnimator.ViewAnimatorListener{

    private val mContext = this@MainActivity
    private lateinit var menuList: ArrayList<SlideMenuItem>
    private var res = R.drawable.icon_music
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var viewAnimator: ViewAnimator<Resourceble>
//    private lateinit var linearLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        nav_view.setNavigationItemSelectedListener(this)
        menuList = createMenuList()

        initView()
        setClick()
        setActionBar()
    }


    private fun initView() {
        val contentFragment = ContentFragment.newInstance(R.drawable.icon_music)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit()

        drawer_layout.setScrimColor(Color.TRANSPARENT)
        left_drawer.setOnClickListener { drawer_layout.closeDrawers() }
        viewAnimator = ViewAnimator(this, menuList as List<Resourceble>?,
                contentFragment, drawer_layout, this)
    }


    private fun setActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle = object : ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                left_drawer.removeAllViews()
                left_drawer.invalidate()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                if (slideOffset > 0.6 && left_drawer.childCount == 0)
                    viewAnimator.showMenuContent()
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawerToggle.setToolbarNavigationClickListener {  }

        drawer_layout.addDrawerListener(drawerToggle)
    }





    private fun setClick() {
        LocationUtils.getCurrentLocation(mContext, object : SimpleCallback {
            override fun callback(result: Any) {
                var cityInfo = result as String
                if (cityInfo == "") {
                    cityInfo = DbUtils.getConfig(mContext, "city") ?: "上海"
                }else {
                    DbUtils.saveOrUpdateConfig(mContext, ConfigBean(
                            Constants.DB_CITY, cityInfo, 1))
                }
                runOnUiThread {
                    get_weather.text = cityInfo
                    get_weather.setOnClickListener {
                        ApiLogic.getWeather(mContext, cityInfo)
                    }
                }
            }
        })
    }


    private fun createMenuList(): ArrayList<SlideMenuItem> {
        val list = ArrayList<SlideMenuItem>()
        val menuItem0 = SlideMenuItem(ContentFragment.CLOSE, R.drawable.icon_activity)
        list.add(menuItem0)
        val menuItem = SlideMenuItem(ContentFragment.BUILDING, R.drawable.icon_brush)
        list.add(menuItem)
        val menuItem2 = SlideMenuItem(ContentFragment.BOOK, R.drawable.icon_collection)
        list.add(menuItem2)
        val menuItem3 = SlideMenuItem(ContentFragment.PAINT, R.drawable.icon_flag_purple)
        list.add(menuItem3)
        val menuItem4 = SlideMenuItem(ContentFragment.CASE, R.drawable.icon_remind)
        list.add(menuItem4)
        val menuItem5 = SlideMenuItem(ContentFragment.SHOP, R.drawable.icon_share)
        list.add(menuItem5)
        val menuItem6 = SlideMenuItem(ContentFragment.PARTY, R.drawable.icon_music)
        list.add(menuItem6)

        return list
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
//        UIUtils.showToast(mContext, "onPostCreate")
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
//        UIUtils.showToast(mContext, "onConfigurationChanged")
        drawerToggle.onConfigurationChanged(newConfig)
    }



    override fun disableHomeButton() {
//        UIUtils.showToast(mContext, "disableHomeButton")
        supportActionBar?.setHomeButtonEnabled(false)
    }

    override fun enableHomeButton() {
//        UIUtils.showToast(mContext, "enableHomeButton")
        supportActionBar?.setHomeButtonEnabled(true)
        drawer_layout.closeDrawers();
    }

    override fun addViewToContainer(view: View?) {
//        UIUtils.showToast(mContext, "addViewToContainer")
        left_drawer.addView(view)
    }

    override fun onSwitch(slideMenuItem: Resourceble?, screenShotable: ScreenShotable?, position: Int): ScreenShotable? {
//        UIUtils.showToast(mContext, "onSwitch")
        return when (slideMenuItem?.name) {
            "Close" -> screenShotable
            "Building" -> ContentFragment.newInstance(R.drawable.icon_music)
            else -> WriteDiaryFragment()
        }
    }




    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }


    private fun replaceFragment(screenShotable: ScreenShotable?, topPosition: Int): ScreenShotable {
        this.res = if (this.res == R.drawable.icon_music) R.drawable.icon_collection else R.drawable.icon_music
        val view = findViewById<View>(R.id.content_frame)
        val finalRadius = Math.max(view.width, view.height)
        val animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0f, finalRadius.toFloat())
        animator.interpolator = AccelerateInterpolator()
        animator.duration = ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION.toLong()

        content_overlay.setBackgroundDrawable(BitmapDrawable(resources, screenShotable?.bitmap))
        animator.start()
        val contentFragment = ContentFragment.newInstance(this.res)
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, contentFragment).commit()
        return contentFragment
    }



//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        // Handle navigation view item clicks here.
//        val id = item.itemId
//
//        when (id) {
//            R.id.nav_camera -> {
//                // Handle the camera action
//            }
//            R.id.nav_gallery -> {
//
//            }
//            R.id.nav_slideshow -> {
//
//            }
//            R.id.nav_manage -> {
//
//            }
//            R.id.nav_share -> {
//
//            }
//            R.id.nav_send -> {
//
//            }
//        }
//
//        drawer_layout.closeDrawer(GravityCompat.START)
//        return true
//    }

    override fun sucGetWeather(weatherBean: WeatherBean) {
        main_content.text = weatherBean.data.forecast[0].type
    }

    override fun failGetWeather() {
        UIUtils.showToast(mContext, "获取天气失败")
    }

    override fun failed() {
        UIUtils.showNetError(mContext)
    }
}
