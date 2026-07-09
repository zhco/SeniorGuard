package com.seniorguard.app.service
import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import com.seniorguard.app.engine.RepairEngine
import com.seniorguard.app.monitor.CallSmsMonitor
import com.seniorguard.app.monitor.InstallMonitor
import com.seniorguard.app.monitor.StateMonitor
import kotlinx.coroutines.*
class GuardianService : LifecycleService() {
    private lateinit var repairEngine: RepairEngine
    private lateinit var stateMonitor: StateMonitor
    private lateinit var installMonitor: InstallMonitor
    private lateinit var callSmsMonitor: CallSmsMonitor
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, buildNotification())
        val app = application as com.seniorguard.app.SeniorGuardApp
        repairEngine = app.repairEngine
        stateMonitor = StateMonitor(this, repairEngine)
        installMonitor = InstallMonitor(this, repairEngine)
        callSmsMonitor = CallSmsMonitor(this, repairEngine)
        stateMonitor.start(scope)
        installMonitor.register()
        callSmsMonitor.register()
        scope.launch { repairEngine.fullScan() }
    }
    override fun onDestroy() { scope.cancel(); installMonitor.unregister(); callSmsMonitor.unregister(); super.onDestroy() }
    override fun onBind(intent: Intent?): IBinder? = null
    private fun createNotificationChannel() {
        getSystemService(NotificationManager::class.java).createNotificationChannel(
            NotificationChannel("guardian", "守护服务", NotificationManager.IMPORTANCE_LOW))
    }
    private fun buildNotification() = Notification.Builder(this, "guardian")
        .setContentTitle("SeniorGuard").setContentText("智能守护运行中")
        .setSmallIcon(android.R.drawable.ic_lock_idle_lock).build()
}