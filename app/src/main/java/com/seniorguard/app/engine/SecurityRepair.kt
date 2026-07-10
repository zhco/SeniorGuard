package com.seniorguard.app.engine
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
data class SuspiciousApp(val pkgName: String, val appName: String, val reasons: List<String>)
class SecurityRepair(private val context: Context) : RepairModule {
    companion object {
        private val KEYWORD_RISK = listOf("清理","加速","大师","破解","wifi","免费上网","贷款","借钱","赚钱","红包","棋牌","彩票","娱乐","交友","极速版")
        private val SENSITIVE_PERMS = listOf("android.permission.READ_CONTACTS","android.permission.READ_SMS","android.permission.ACCESS_FINE_LOCATION","android.permission.RECORD_AUDIO","android.permission.CAMERA","android.permission.READ_CALL_LOG")
        private val TRUSTED_SOURCES = setOf("com.android.vending","com.huawei.appmarket","com.xiaomi.market","com.oppo.market","com.heytap.market","com.vivo.appstore","com.hihonor.appmarket")
        private val CORE_APPS = setOf("com.tencent.mm","com.alibaba.android.rimet","com.tencent.mobileqq","com.android.phone","com.android.contacts","com.android.camera","com.android.systemui","com.android.launcher","com.seniorguard.app")
    }
    override suspend fun fixAll(): Int { var c=0; if(fixUnknownSources())c++; return c }
    fun fixUnknownSources(): Boolean = try {
        if(Settings.Secure.getInt(context.contentResolver,Settings.Secure.INSTALL_NON_MARKET_APPS,0)==1){
            Settings.Secure.putInt(context.contentResolver,Settings.Secure.INSTALL_NON_MARKET_APPS,0); true
        } else false
    } catch(e:Exception){ false }
    fun scanSuspiciousApps(): List<SuspiciousApp> = try {
        val pm = context.packageManager
        val apps = mutableListOf<SuspiciousApp>()
        for(app in pm.getInstalledApplications(PackageManager.GET_META_DATA)){
            if(app.packageName in CORE_APPS) continue
            val reasons = mutableListOf<String>()
            val appName = pm.getApplicationLabel(app).toString().lowercase()
            val installer = pm.getInstallerPackageName(app.packageName)
            val matchedKw = KEYWORD_RISK.any { it.lowercase() in appName }
            if(matchedKw) reasons.add("名称含可疑关键词")
            val perms = try { pm.getPackageInfo(app.packageName,PackageManager.GET_PERMISSIONS).requestedPermissions?.toList()?:emptyList() } catch(e:Exception){ emptyList() }
            if(perms.size > 15) reasons.add("权限过多("+perms.size.toString()+"个)")
            val hasSensitive = perms.any { it in SENSITIVE_PERMS }
            if(hasSensitive) reasons.add("含敏感权限")
            if(installer!=null && installer !in TRUSTED_SOURCES) reasons.add("非应用商店安装")
            if(installer==null && matchedKw) reasons.add("未知来源+风险名称")
            if(reasons.isNotEmpty()) apps.add(SuspiciousApp(app.packageName,pm.getApplicationLabel(app).toString(),reasons))
        }
        apps
    } catch(e:Exception){ emptyList() }
}
