package com.example.fitnessbag

import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.NavController
import com.example.fitnessbag.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var isShowKeyboard = false
    private var navController: NavController? = null
    private lateinit var binding: ActivityMainBinding
    

    private var _onHideKeyboardListeners = ArrayList<IOnHideKeyboardListener>()

    fun addHideKeyboardListener(listener: IOnHideKeyboardListener) {
        _onHideKeyboardListeners.add(listener)
    }
    
    fun removeHideKeyboardListener(listener: IOnHideKeyboardListener) {
        _onHideKeyboardListeners.remove(listener)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController!!)

        initListeners()
    }

    private fun initListeners() {
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            window.decorView.getWindowVisibleDisplayFrame(r)

            val height = window.decorView.height
            if (height - r.bottom > height * 0.1399) {
                isShowKeyboard = true
            } else if(isShowKeyboard) {
                isShowKeyboard = false
                _onHideKeyboardListeners.forEach {
                    it.keyboardHide()
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController!!.navigateUp()
                || super.onSupportNavigateUp()
    }
}
interface IOnHideKeyboardListener {
    fun keyboardHide()
}
    