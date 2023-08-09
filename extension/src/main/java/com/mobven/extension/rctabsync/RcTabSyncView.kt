package com.mobven.extension.rctabsync

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.mobven.extension.smoothSnapToPosition

class RcTabSyncView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr), TabLayout.OnTabSelectedListener {

    private var itemsTabIndex: List<Int> = emptyList()
    private var attachedTabLayout: TabLayout? = null
    private var ignoreScroll: Boolean = false
    private var ignoreTabSelect: Boolean = false
    private var smoothScroll: Boolean = true
    private var linearLayoutManagerSet : Boolean? = false
    private var gridLayoutManagerSet : Boolean? = false

    fun setLinearLayoutManager(isActive: Boolean){
        this.linearLayoutManagerSet = isActive
    }

    fun setGridLayoutManager(isActive: Boolean){
        this.gridLayoutManagerSet = isActive
    }

    fun setSmoothScroll(smoothScroll: Boolean) {
        this.smoothScroll = smoothScroll
    }

    fun setItemsByTabIndex(listOfCounts: List<Int>) {
        itemsTabIndex = listOfCounts
    }

    fun setTabLayout(tabLayout: TabLayout) {
        attachedTabLayout?.removeOnTabSelectedListener(this)
        attachedTabLayout = tabLayout
        tabLayout.addOnTabSelectedListener(this)
    }

    override fun addOnScrollListener(listener: OnScrollListener) {
        if (listener !is RecyclerViewScrollListener) {
            throw UnsupportedOperationException("onScrollListener must be of type TabSyncedScrollListener")
        }
        super.addOnScrollListener(listener)
    }

    private fun getFirstDataPosInTab(tabIndex: Int): Int {
        if (tabIndex == 0 || itemsTabIndex.isEmpty()) {
            return 0
        }

        var prevTabDataPos: Int = itemsTabIndex[0]

        for (i in 1 until itemsTabIndex.size) {
            if (i == tabIndex) {
                break
            } else {
                prevTabDataPos += itemsTabIndex[i]
            }
        }

        return prevTabDataPos + 2
    }

    private fun getTabIndexForPosition(pos: Int): Int {
        if (itemsTabIndex.isEmpty()) {
            return 0
        }

        var tabIndex = 0
        var currentTraversedItems = 0

        for (i in itemsTabIndex.indices) {
            tabIndex = i
            currentTraversedItems += itemsTabIndex[i]
            if (pos < currentTraversedItems) {
                break
            }
        }
        return tabIndex
    }

    private fun updateTabsFromScrollEvent(firstCompletelyVisibleItemPosition: Int) {
        if (firstCompletelyVisibleItemPosition == NO_POSITION) {
            return
        }
        val tabIndexForPos = getTabIndexForPosition(firstCompletelyVisibleItemPosition)
        attachedTabLayout?.let {
            if (tabIndexForPos != it.selectedTabPosition) {
                ignoreTabSelect = true
                val tab = it.getTabAt(tabIndexForPos)
                tab?.let { tabs ->
                    it.selectTab(tab)
                }
            }
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        // not used
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        // not used
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        if (!ignoreTabSelect) {
            val currSelectedTabPos = tab.position
            val index = getFirstDataPosInTab(currSelectedTabPos)
            ignoreScroll = true
            if (smoothScroll) {
                smoothSnapToPosition(index)
            }
            else {
                if(this.linearLayoutManagerSet == true){
                    (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(index, 0)
                }
            }
        } else {
            ignoreTabSelect = false
        }
    }

    inner class RecyclerViewScrollListener : OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == SCROLL_STATE_IDLE) {
                ignoreScroll = false
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dx == 0 && dy == 0) {
                return
            }
            if (!ignoreScroll) {
                when (val layoutManager = recyclerView.layoutManager) {
                    is LinearLayoutManager -> updateTabsFromScrollEvent(
                        layoutManager.findFirstCompletelyVisibleItemPosition()
                    )
                    // Your Layout Manager added.
                    else -> throw IllegalArgumentException("The layoutManager:$layoutManager is unsupported")
                }
            }
        }
    }
}