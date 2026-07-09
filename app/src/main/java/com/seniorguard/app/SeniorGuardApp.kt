package com.seniorguard.app
import android.app.Application
import com.seniorguard.app.engine.RepairEngine
class SeniorGuardApp : Application() {
    lateinit var repairEngine: RepairEngine; private set
    override fun onCreate() { super.onCreate(); repairEngine = RepairEngine(this) }
}