package com.seniorguard.app.monitor
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.seniorguard.app.service.GuardianService
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED)
            context.startForegroundService(Intent(context, GuardianService::class.java))
    }
}