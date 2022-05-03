package com.example.fitnessbag.presentation.create_workout

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessbag.databinding.ItemEditableTagBinding

class EditableTagViewHolder(
    val binding: ItemEditableTagBinding,
    val onComplete: (text: String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    
    var _isActive: Boolean = false
    val isActive: Boolean
        get() = _isActive

    private val inputMethodManager: InputMethodManager = ContextCompat.getSystemService(
        binding.root.context,
        InputMethodManager::class.java
    ) as InputMethodManager

    init {
        binding.root.visibility = View.INVISIBLE

        binding.titleEditText.setOnFocusChangeListener { view: View, focus: Boolean ->
            if(focus)
                return@setOnFocusChangeListener
            
            invokeComplete()
            hide()
        }
        
        binding.titleEditText.setOnEditorActionListener { textView: TextView, i: Int, keyEvent: KeyEvent? ->
            if(i != EditorInfo.IME_ACTION_GO)
                return@setOnEditorActionListener false

            invokeComplete()
            hide()

            return@setOnEditorActionListener true
        }
    }
    
    fun show() {
        binding.root.visibility = View.VISIBLE
        binding.titleEditText.requestFocus()
        showKeyboard()

        _isActive = true
    }
    
    fun hide() {
        binding.root.visibility = View.INVISIBLE
        hideKeyboard()

        _isActive = false
    }
    
    private fun haveText(): Boolean {
        return !binding.titleEditText.text.isEmpty()
    }
    
    fun invokeComplete() {
        if(!haveText())
            return
        
        onComplete.invoke(binding.titleEditText.text.toString())
        binding.titleEditText.text.clear()
    }

    private fun hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(binding.root.applicationWindowToken, 0)
    }

    private fun showKeyboard() {
        inputMethodManager.showSoftInput(binding.titleEditText, InputMethodManager.SHOW_IMPLICIT)
    }
}