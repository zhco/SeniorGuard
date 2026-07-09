package com.seniorguard.app.engine
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
class SecurityRepair(private val context: Context) : RepairModule {
    private val trustedSources = setOf("com.android.vending","com.huawei.appmarket","com.xiaomi.market","com.oppo.market","com.heytap.market","com.vivo.appstore","com.hihonor.appmarket")
    private val coreApps = setOf("com.tencent.mm","com.alibaba.android.rimet","com.tencent.mobileqq","com.android.phone","com.android.contacts","com.android.camera","com.seniorguard.app")
    override suspend fun fixAll(): Int { var c=0; if(fixUnknownSources())c++; if(checkSuspiciousApps())c++; return c }
    fun fixUnknownSources(): Boolean = try { Settings.Secure.putInt(context.contentResolver,Settings.Secure.INSTALL_NON_MARKET_APPS,0); true } catch(e:Exception){ false }
    fun checkSuspiciousApps(): Boolean { var u=false; try{ val pm=context.packageManager; for(app in pm.getInstalledApplications(PackageManager.GET_META_DATA)){ if(app.packageName in coreApps)continue; val inst=pm.getInstallerPackageName(app.packageName); if(inst!=null&&inst !in trustedSources){ val intent=android.content.Intent(android.content.Intent.ACTION_DELETE); intent.data=android.net.Uri.parse("package:${app.packageName}"); intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK); context.startActivity(intent); u=true } } } catch(e:Exception){}; return u }
}