package com.seniorguard.app.monitor
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
class PackageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val app = context.applicationContext as com.seniorguard.app.SeniorGuardApp
        InstallMonitor.handlePackageEvent(context, intent, app.repairEngine)
    }
}