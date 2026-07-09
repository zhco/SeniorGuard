package com.seniorguard.app.monitor
import android.content.Context
import com.seniorguard.app.engine.RepairEngine
import kotlinx.coroutines.*
class StateMonitor(private val context: Context, private val engine: RepairEngine) {
    fun start(scope: CoroutineScope) {
        scope.launch {
            while (isActive) {
                engine.fullScan()
                delay(60_000)
            }
        }
    }
}