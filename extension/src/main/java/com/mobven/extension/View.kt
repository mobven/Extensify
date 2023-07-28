package com.mobven.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.core.widget.NestedScrollView
import com.google.android.material.tabs.TabLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

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
inline fun View.showIf(condition: () -> Boolean): View {
    if (visibility != View.VISIBLE && condition()) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Hide the view if [predicate] returns true
 * (visibility = View.INVISIBLE)
 */
inline fun View.hideIf(predicate: () -> Boolean): View {
    if (visibility != View.INVISIBLE && predicate()) {
        visibility = View.INVISIBLE
    }
    return this
}

/**
 * Remove the view if [predicate] returns true
 * (visibility = View.GONE)
 */
inline fun View.removeIf(predicate: () -> Boolean): View {
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
 * Extension method for callback actions of tab selected, tab reselected and tab unselected events
 */
fun TabLayout.onTabSelectedListener(
    onSelected: (TabLayout.Tab?) -> Unit,
    onReselected: (TabLayout.Tab?) -> Unit,
    onUnselected: (TabLayout.Tab?) -> Unit
) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            onSelected.invoke(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
            onReselected.invoke(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            onUnselected.invoke(tab)
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
 * Returns the scroll position for the vertical RecyclerView.
 * @param onScroll : Lambda ScrollPosition param
 */

fun RecyclerView.addScrollListener(onScroll: (position: Int) -> Unit) {
    var lastPosition = 0
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (layoutManager is LinearLayoutManager) {
                val currentVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (lastPosition != currentVisibleItemPosition && currentVisibleItemPosition != RecyclerView.NO_POSITION) {
                    onScroll.invoke(currentVisibleItemPosition)
                    lastPosition = currentVisibleItemPosition
                }
            }
        }
    })
}


/**
 @see @@@ Sample Usage @@@
 -------

 suspend fun renderSinglePage(filePath: String, width: Int) = withContext(Dispatchers.IO) {
    PdfRenderer(ParcelFileDescriptor.open(File(filePath), ParcelFileDescriptor.MODE_READ_ONLY)).use { renderer ->
    renderer.openPage(0).renderAndClose(width)
    }
 }

 */

fun PdfRenderer.Page.renderAndClose(width: Int) = use {
    val bitmap = createBitmap(width)
    render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
    bitmap
}

private fun PdfRenderer.Page.createBitmap(bitmapWidth: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(
            bitmapWidth, (bitmapWidth.toFloat() / width * height).toInt(), Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.WHITE)
    canvas.drawBitmap(bitmap, 0f, 0f, null)

    return bitmap
}

fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}