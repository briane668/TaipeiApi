package com.example.myapplication


import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.Home.HomeFragment
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val languages = arrayOf("zh-tw", "zh-cn", "en", "ja", "ko", "es", "id", "th", "vi")
    private val languagesForSelect =
        arrayOf("正體中文", "簡體中文", "英文", "日文", "韓文", "西班牙文", "印尼文", "泰文", "越南文")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.NavHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupNavController()
        binding.backButton.setOnClickListener {
            findNavController(R.id.NavHostFragment).navigateUp()
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
        binding.language.setOnClickListener {
            showLanguageSelectionDialog(currentFragment)
        }



// 添加監聽器

// 添加監聽器
        binding.lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {
                binding.lottieAnimationView.visibility = View.GONE // 隱藏 LottieAnimationView
            }

            override fun onAnimationCancel(p0: Animator) {

            }

            override fun onAnimationRepeat(p0: Animator) {

            }
        })

        binding.lottieAnimationView.playAnimation()
    }

    private fun setupNavController() {
        findNavController(R.id.NavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            when (navController.currentDestination?.id) {
                R.id.HomeFragment -> {
                    binding.backButton.visibility = View.GONE
                    binding.title.text = "悠遊台北"
                    binding.language.visibility = View.VISIBLE
                }
                R.id.webFragment -> {
                    binding.backButton.visibility = View.VISIBLE
                    binding.title.text = "最新消息"
                    binding.language.visibility = View.GONE
                }
                R.id.DetailFragment -> {
                    binding.backButton.visibility = View.VISIBLE
                    binding.language.visibility = View.GONE
                }
                else -> ""
            }
        }
    }


    private fun showLanguageSelectionDialog(fragment: Fragment?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Language")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, languagesForSelect)
        builder.setAdapter(adapter) { dialog, which ->
            val selectedLanguage = languages[which]
            var homeFragment = fragment as HomeFragment
            homeFragment.callData(selectedLanguage)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun changeTitle(title: String) {
        binding.title.text = title

    }

    fun endAnimation() {
        binding.lottieAnimationView.visibility = View.GONE
    }
}