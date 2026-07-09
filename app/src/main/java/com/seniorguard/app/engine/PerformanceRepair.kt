package com.seniorguard.app.engine
import android.app.ActivityManager
import android.content.Context
import android.provider.Settings
class PerformanceRepair(private val context: Context) : RepairModule {
    private val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    private val whitelist = setOf("com.tencent.mm","com.seniorguard.app","com.android.phone")
    override suspend fun fixAll(): Int { var c=0; if(killBackgroundProcesses())c++; if(fixPowerSaver())c++; if(fixAnimations())c++; return c }
    fun killBackgroundProcesses(): Boolean { var k=false; try{ for(p in (am.runningAppProcesses?:return false)){ if(p.processName !in whitelist&&p.importance>=ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE){ am.killBackgroundProcesses(p.processName); k=true } } } catch(e:Exception){}; return k }
    fun fixPowerSaver(): Boolean = try { Settings.Global.putString(context.contentResolver,"low_power","0"); true } catch(e:Exception){ false }
    fun fixAnimations(): Boolean { var f=false; try{ for(s in listOf(Settings.Global.ANIMATOR_DURATION_SCALE,Settings.Global.TRANSITION_ANIMATION_SCALE,Settings.Global.WINDOW_ANIMATION_SCALE)){ if(Settings.Global.getFloat(context.contentResolver,s,1.0f)!=1.0f){ Settings.Global.putFloat(context.contentResolver,s,1.0f); f=true } } } catch(e:Exception){}; return f }
}