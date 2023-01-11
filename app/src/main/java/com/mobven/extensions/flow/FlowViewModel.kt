package com.mobven.extensions.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class FlowViewModel : ViewModel() {

    private fun api1() = flow {
        delay(5000)
        emit("API 1 Response")
    }

    private fun api2() = flow {
        delay(3000)
        emit("API 2 Response")
    }

    private fun api3() = flow {
        delay(1000)
        emit("API 3 Response")
    }

    private suspend fun api3Suspend(): String {
        delay(1000)
        return "API 3 Response"
    }

    private fun callApiSynchronously() {
        var startTime = 0L
        var isLoading = false

        api1().onStart {
            startTime = System.currentTimeMillis()
            isLoading = true
            println("Loading: $isLoading")
            println("APIs Called ->")
        }.onEach {
            println(it)
        }.onCompletion {
            api2().onEach {
                println(it)
            }.retry(3000) {
                println("Api 2 Retry")
                true
            }.catch {
                println("Api 2 Catch")
            }.onCompletion {
                api3().onEach {
                    println(it)
                }.onCompletion {
                    println("APIs Complete: ${System.currentTimeMillis() - startTime}ms")
                    println("Loading: $isLoading")
                }.launchIn(viewModelScope)
            }.launchIn(viewModelScope)
        }.retry(3000) {
            println(it.message)
            false
        }.catch {
            println(it.message)
        }.launchIn(viewModelScope)
    }

    private fun callApiWithMerge() {
        var startTime = 0L
        var isLoading = false
        val mergedApiCall = merge(api1(), api2(), api3())
        mergedApiCall.onStart {
            isLoading = true
            println("Loading: $isLoading")
            startTime = System.currentTimeMillis()
            println("Merged APIs Called ->")
        }.onEach {
            println(it)
        }.onCompletion {
            println("Merged APIs Complete: ${System.currentTimeMillis() - startTime}ms")
            println("Loading: $isLoading")
        }.launchIn(viewModelScope)
    }

    private fun callApiWithZip() {
        val zippedCall = api1().zip(api2()) { api1Value, api2Value ->
            println(api1Value)
            //println(api2Value)
        }
        var startTime = 0L
        var isLoading = false
        zippedCall.onStart {
            isLoading = true
            println("Loading: $isLoading")
            startTime = System.currentTimeMillis()
            println("Merged APIs Called ->")
        }.onCompletion {
            println("Merged APIs Complete: ${System.currentTimeMillis() - startTime}ms")
            println("Loading: $isLoading")
        }.launchIn(viewModelScope)
    }

    private fun callApiWithFlattenMerge() {
        var startTime = 0L
        flowOf(api2(), api1(), api3()).flattenMerge()
            .onStart {
                println("Loading: True")
                startTime = System.currentTimeMillis()
            }
            .onEach {
                println("$it ${System.currentTimeMillis() - startTime}ms")
            }.onCompletion {
                println("FlattenMerge APIs Complete: ${System.currentTimeMillis() - startTime}ms")
                println("Loading: False")
            }.launchIn(viewModelScope)
    }

    init {
        //callApiWithFlattenMerge()
        //callApiSynchronously()
        callApiWithMerge()
        //callApiWithZip()
    }
}