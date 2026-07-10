package com.seniorguard.app
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.seniorguard.app.engine.SecurityRepair
import com.seniorguard.app.service.GuardianService
import com.seniorguard.app.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, GuardianService::class.java))
        val sec = SecurityRepair(this)
        val apps = sec.scanSuspiciousApps()
        setContent {
            MaterialTheme {
                MainScreen(
                    suspiciousApps = apps,
                    onOpenAccessibility = { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) },
                    onOpenUsageStats = { startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)) },
                    onUninstall = { pkg ->
                        val intent = Intent(Intent.ACTION_DELETE)
                        intent.data = Uri.parse("package:$pkg")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
