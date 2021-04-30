package com.mobven.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.core.widget.NestedScrollView
import com.google.android.material.tabs.TabLayout

/**
 * Set an onClick listener
 */
inline fun <reified T : View> T.click(crossinline block: (T) -> Unit) = setOnClickListener {
    block(it as T)
}

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

/**
 * Show the view if [condition] returns true
 * (visibility = View.VISIBLE)
 */
inline fun View.showIf(condition: () -> Boolean) : View {
    if (visibility != View.VISIBLE && condition()) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Hide the view if [predicate] returns true
 * (visibility = View.INVISIBLE)
 */
inline fun View.hideIf(predicate: () -> Boolean) : View {
    if (visibility != View.INVISIBLE && predicate()) {
        visibility = View.INVISIBLE
    }
    return this
}

/**
 * Remove the view if [predicate] returns true
 * (visibility = View.GONE)
 */
inline fun View.removeIf(predicate: () -> Boolean) : View {
    if (visibility != View.GONE && predicate()) {
        visibility = View.GONE
    }
    return this
}

/**
 * Extension method for support multiple click
 */
fun multipleOnClick(vararg view: View, onClick: () -> Unit) {
    view.forEach {
        it.setOnClickListener {
            onClick.invoke()
        }
    }
}

/**
 * Extension method for callback action on tab selected
 */
fun TabLayout.doOnTabSelected(onSelected: (TabLayout.Tab?) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            onSelected.invoke(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
            // Handle tab reselect
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            // Handle tab unselect
        }
    })
}

/**
 * Extension method Provides editing for NestedScrollView in CoordinatorLayout.
 * INFO: Updates this NestedScrollView bottom-padding. It removes the empty-space.
 * Insets are areas of your view that you should not put elements, like behind the status bar or navigation bar.
 */
fun NestedScrollView.setInsetListener() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        this.updatePadding(bottom = insets.systemWindowInsetBottom)
        insets.consumeSystemWindowInsets()
    }
}

/**
 * Extension method for callback action on tab reselected
 */
fun TabLayout.doOnTabReselected(onReselected: (TabLayout.Tab?) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            //Handle tab select
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
            onReselected.invoke(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            // Handle tab unselect
        }
    })
}

/**
 * Extension method for callback action on tab unselected
 */
fun TabLayout.doOnTabUnselected(onUnselected: (TabLayout.Tab?) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            //Handle tab select
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
            // Handle tab reselect
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            onUnselected.invoke(tab)
        }
    })
}






