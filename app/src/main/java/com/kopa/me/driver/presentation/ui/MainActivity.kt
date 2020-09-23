package com.kopa.me.driver.presentation.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.kopa.me.driver.R
import com.kopa.me.driver.databinding.ActivityMainBinding
import com.kopa.me.driver.presentation.ui.common.LoadingState
import com.kopa.me.driver.presentation.ui.home.HomeFragment
import com.kopa.me.driver.presentation.ui.home.ProfileFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModel()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.profileFragment, R.id.settingsFragment
            ), binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        setupDrawerContent(binding.navView);

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fab_location))
        fab.setOnClickListener { view ->

        }

        val loadingDialog = setLoadingDialog(this@MainActivity)
        sharedViewModel.showLoading.observe(this, Observer {
            if (!isFinishing) {
                if (it == LoadingState.SHOW)
                    loadingDialog.show()
                else Timer("Hide loading", false).schedule(700) {
                    loadingDialog.dismiss()
                }
            }
        })


    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener(
            object : NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    selectDrawerItem(item)
                    return true
                }
            })
    }


    fun selectDrawerItem(menuItem: MenuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        val fragment: Fragment
        val fragmentClass: Class<*>

        fragmentClass = when (menuItem.itemId) {
            R.id.nav_home -> HomeFragment::class.java
            R.id.nav_profile -> ProfileFragment::class.java
            R.id.nav_help -> ProfileFragment::class.java
            else -> HomeFragment::class.java
        }
        fragment = fragmentClass.newInstance() as Fragment

        // Insert the fragment by replacing any existing fragment
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()

        // Highlight the selected item has been done by NavigationView
        menuItem.isChecked = true
        // Set action bar title
        title = menuItem.title
        // Close the navigation drawer
        binding.drawerLayout.closeDrawer(Gravity.LEFT)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setLoadingDialog(context: Context): Dialog {
        val dialog = Dialog(context)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0.9f)
        }
        return dialog
    }
}