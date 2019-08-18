package com.rifle.lazy_diary.ui.activity

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.rifle.lazy_diary.R
import com.rifle.lazy_diary.ui.fragment.ContentFragment
import com.rifle.lazy_diary.ui.fragment.MainFragment
import yalantis.com.sidemenu.interfaces.Resourceble
import yalantis.com.sidemenu.interfaces.ScreenShotable
import yalantis.com.sidemenu.model.SlideMenuItem
import yalantis.com.sidemenu.util.ViewAnimator
import java.util.*
import kotlin.math.max

class MainActivity : AppCompatActivity(), ViewAnimator.ViewAnimatorListener {

    private var drawerLayout: DrawerLayout? = null
    private var drawerToggle: ActionBarDrawerToggle? = null
    private val list = ArrayList<SlideMenuItem>()
    private var viewAnimator: ViewAnimator<*>? = null
    private var res = R.drawable.icon_music
    private var linearLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contentFragment = MainFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit()
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout!!.setScrimColor(Color.TRANSPARENT)
        linearLayout = findViewById(R.id.left_drawer)
        linearLayout!!.setOnClickListener { drawerLayout!!.closeDrawers() }


        setActionBar()
        createMenuList()
        viewAnimator = ViewAnimator(this, list, contentFragment, drawerLayout, this)

    }


    private fun createMenuList() {
        val menuItem0 = SlideMenuItem(ContentFragment.CLOSE, R.drawable.close)
        list.add(menuItem0)
        val menuItem = SlideMenuItem(ContentFragment.BUILDING, R.drawable.icon_music)
        list.add(menuItem)
        val menuItem2 = SlideMenuItem(ContentFragment.BOOK, R.drawable.icon_brush)
        list.add(menuItem2)
        val menuItem3 = SlideMenuItem(ContentFragment.PAINT, R.drawable.icon_activity)
        list.add(menuItem3)
        val menuItem4 = SlideMenuItem(ContentFragment.CASE, R.drawable.icon_collection)
        list.add(menuItem4)
        val menuItem5 = SlideMenuItem(ContentFragment.SHOP, R.drawable.icon_flag_purple)
        list.add(menuItem5)
        val menuItem6 = SlideMenuItem(ContentFragment.PARTY, R.drawable.icon_flag_red)
        list.add(menuItem6)
        val menuItem7 = SlideMenuItem(ContentFragment.MOVIE, R.drawable.icon_remind)
        list.add(menuItem7)
    }


    private fun setActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        drawerToggle = object : ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                linearLayout!!.removeAllViews()
                linearLayout!!.invalidate()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                if (slideOffset > 0.6 && linearLayout!!.childCount == 0)
                    viewAnimator!!.showMenuContent()
            }
        }
        drawerLayout!!.addDrawerListener(drawerToggle!!)
    }


    private fun replaceFragment(screenShotable: ScreenShotable, topPosition: Int): ScreenShotable {
        this.res = if (this.res == R.drawable.icon_music) R.drawable.icon_brush else R.drawable.icon_music
        val view = findViewById<View>(R.id.content_frame)
        val finalRadius = max(view.width, view.height)
        val animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0f, finalRadius.toFloat())
        animator.interpolator = AccelerateInterpolator()
        animator.duration = ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION.toLong()

        findViewById<View>(R.id.content_overlay).background = BitmapDrawable(resources, screenShotable.bitmap)
        animator.start()
        val contentFragment = ContentFragment.newInstance(this.res)
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, contentFragment).commit()
        return contentFragment
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle!!.syncState()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle!!.onConfigurationChanged(newConfig)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle!!.onOptionsItemSelected(item)) {
            return true
        }
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onSwitch(slideMenuItem: Resourceble, screenShotable: ScreenShotable, position: Int): ScreenShotable {
        when (slideMenuItem.name) {
            ContentFragment.CLOSE -> return screenShotable
            else -> return replaceFragment(screenShotable, position)
        }
    }

    override fun disableHomeButton() {
        supportActionBar!!.setHomeButtonEnabled(false)
    }

    override fun enableHomeButton() {
        supportActionBar!!.setHomeButtonEnabled(true)
        drawerLayout!!.closeDrawers()
    }

    override fun addViewToContainer(view: View) {
        linearLayout!!.addView(view)
    }

}
