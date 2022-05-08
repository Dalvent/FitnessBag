package com.example.fitnessbag

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.NavController
import com.example.fitnessbag.databinding.ActivityMainBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var activity: WeakReference<Activity>
    }

    private val viewModel: MainActivityViewModel by viewModel()

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
        activity = WeakReference(this)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val uri = data?.data!!
            viewModel.imagePickerService.invokeResult(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
        }
    }
}
interface IOnHideKeyboardListener {
    fun keyboardHide()
}