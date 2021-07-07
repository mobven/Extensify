package com.mobven.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Extension method for observing livedata
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, observer: (T) -> Unit) {
    liveData?.observe(this, Observer(observer))
}

/**
 * Extension method for observing events
 */
fun <T> LifecycleOwner.eventObserve(liveData: LiveData<Event<T>>?, observer: (T) -> Unit) {
    liveData?.observe(this, EventObserver(observer))
}