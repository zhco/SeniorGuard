package com.seniorguard.app.engine
import android.content.Context
import kotlinx.coroutines.*
class RepairEngine(private val context: Context) {
    private val displayRepair = DisplayRepair(context)
    private val audioRepair = AudioRepair(context)
    private val networkRepair = NetworkRepair(context)
    private val storageRepair = StorageRepair(context)
    private val securityRepair = SecurityRepair(context)
    private val performanceRepair = PerformanceRepair(context)
    var fixCount = 0; private set
    suspend fun fullScan() = coroutineScope {
        listOf(launch { displayRepair.fixAll() }, launch { audioRepair.fixAll() },
            launch { networkRepair.fixAll() }, launch { storageRepair.fixAll() },
            launch { securityRepair.fixAll() }, launch { performanceRepair.fixAll() }).joinAll()
    }
    fun incrementFix() { fixCount++ }
}
interface RepairModule { suspend fun fixAll(): Int }
