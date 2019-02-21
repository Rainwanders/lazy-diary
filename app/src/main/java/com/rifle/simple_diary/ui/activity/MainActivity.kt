package com.rifle.simple_diary.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.rifle.simple_diary.R
import com.rifle.simple_diary.app.ApiLogic
import com.rifle.simple_diary.app.Constants
import com.rifle.simple_diary.model.ConfigBean
import com.rifle.simple_diary.model.WeatherBean
import com.rifle.simple_diary.ui.base.BaseActivity
import com.rifle.simple_diary.ui.fragment.ContentFragment
import com.rifle.simple_diary.utils.DbUtils
import com.rifle.simple_diary.utils.LocationUtils
import com.rifle.simple_diary.utils.SimpleCallback
import com.rifle.simple_diary.utils.UIUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import yalantis.com.sidemenu.interfaces.Resourceble
import yalantis.com.sidemenu.interfaces.ScreenShotable
import yalantis.com.sidemenu.model.SlideMenuItem
import yalantis.com.sidemenu.util.ViewAnimator

class MainActivity : BaseActivity(), ViewAnimator.ViewAnimatorListener{

    private val mContext = this@MainActivity
    private val menuList = ArrayList<SlideMenuItem>()
    private lateinit var drawerToggle: ActionBarDrawerToggle
//    private lateinit var viewAnimator: ViewAnimator
    private lateinit var linearLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        nav_view.setNavigationItemSelectedListener(this)

        setClick()
    }


    private fun initView() {
        val contentFragment = ContentFragment.newInstance(R.drawable.icon_music)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit()

        drawer_layout.setScrimColor(Color.TRANSPARENT)
        left_drawer.setOnClickListener { drawer_layout.closeDrawers() }
//        val viewAnimator = ViewAnimator<>(this, ArrayList<Resourceble>(), left_drawer, content_frame, drawer_layout)
    }


    fun setActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawerToggle.setToolbarNavigationClickListener {  }

        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
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


    override fun disableHomeButton() {

    }

    override fun enableHomeButton() {

    }

    override fun addViewToContainer(view: View?) {

    }

    override fun onSwitch(slideMenuItem: Resourceble?, screenShotable: ScreenShotable?, position: Int): ScreenShotable {
        return screenShotable!!
    }




    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//
//        return if (id == R.id.action_settings) {
//            true
//        } else super.onOptionsItemSelected(item)
//
//    }

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
