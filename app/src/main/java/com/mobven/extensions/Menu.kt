package com.mobven.extensions;

import androidx.annotation.StringDef
import com.mobven.extensions.Menu.Companion.SINGLE_SELECT_LIST
import com.mobven.extensions.Menu.Companion.VIEW_EXT

@StringDef(SINGLE_SELECT_LIST, VIEW_EXT)
annotation class Menu {
    companion object {
        const val SINGLE_SELECT_LIST = "Single Selectable RecyclerView"
        const val VIEW_EXT = "View Ext."
        const val REQUEST_PERMISSIONS = "Request Permissions"
        const val COMPOSE_PLAYGROUND = "Compose Playground"
        const val LAYOUT_COMPOSE = "Compose Layout"
        const val CONCAT_ADAPTER = "Concat Adapter"
        const val DIFF_UTIL_LIST = "Diff Util RecyclerView"
    }
}