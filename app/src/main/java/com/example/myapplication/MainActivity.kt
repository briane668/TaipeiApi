package com.example.myapplication

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)



        val navController = findNavController(R.id.NavHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupNavController()

    }


    private fun setupNavController() {
        findNavController(R.id.NavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
             when (navController.currentDestination?.id) {
                R.id.HomeFragment -> binding.title.text ="悠遊台北"
                R.id.webFragment -> binding.title.text ="最新消息"
                else -> ""
            }
        }
    }




    public fun changeTitle (title :String){
        binding.title.text = title

    }
}