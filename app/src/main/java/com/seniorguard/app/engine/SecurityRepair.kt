package com.seniorguard.app.engine
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
class SecurityRepair(private val context: Context) : RepairModule {
    private val trustedSources = setOf("com.android.vending","com.huawei.appmarket","com.xiaomi.market","com.oppo.market","com.heytap.market","com.vivo.appstore","com.hihonor.appmarket")
    private val coreApps = setOf("com.tencent.mm","com.alibaba.android.rimet","com.tencent.mobileqq","com.android.phone","com.android.contacts","com.android.camera","com.seniorguard.app")
    override suspend fun fixAll(): Int { var c=0; if(fixUnknownSources())c++; return c }
    fun fixUnknownSources(): Boolean = try { if(Settings.Secure.getInt(context.contentResolver,Settings.Secure.INSTALL_NON_MARKET_APPS,0)==1){ Settings.Secure.putInt(context.contentResolver,Settings.Secure.INSTALL_NON_MARKET_APPS,0); true } else false } catch(e:Exception){ false }
    fun getSuspiciousApps(): List<String> { val suspect=mutableListOf<String>(); try{ val pm=context.packageManager; for(app in pm.getInstalledApplications(PackageManager.GET_META_DATA)){ if(app.packageName in coreApps)continue; val inst=pm.getInstallerPackageName(app.packageName); if(inst!=null&&inst !in trustedSources) suspect.add(app.packageName) } } catch(e:Exception){}; return suspect }
}
