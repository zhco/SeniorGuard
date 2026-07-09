package com.seniorguard.app.monitor
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.seniorguard.app.engine.RepairEngine
class InstallMonitor(private val context: Context, private val engine: RepairEngine) {
    private val receiver = PackageReceiver()
    fun register() {
        val filter = IntentFilter().apply { addAction(Intent.ACTION_PACKAGE_ADDED); addAction(Intent.ACTION_PACKAGE_REMOVED); addDataScheme("package") }
        context.registerReceiver(receiver, filter)
    }
    fun unregister() { try { context.unregisterReceiver(receiver) } catch (_: Exception) {} }
    companion object {
        fun handlePackageEvent(context: Context, intent: Intent, engine: RepairEngine) {
            if (intent.action == Intent.ACTION_PACKAGE_ADDED) {
                kotlinx.coroutines.runBlocking { engine.fullScan() }
            }
        }
    }
}