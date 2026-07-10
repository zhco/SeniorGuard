package com.seniorguard.app.engine
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
class NetworkRepair(private val context: Context) : RepairModule {
    private val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    override suspend fun fixAll(): Int { var c=0; if(fixWifi())c++; if(fixBluetooth())c++; if(fixAirplaneMode())c++; if(fixHotspot())c++; return c }
    fun fixWifi(): Boolean = try { if(!wifi.isWifiEnabled){ wifi.isWifiEnabled=true; true } else false } catch(e:Exception){ false }
    fun fixBluetooth(): Boolean = try { val bt=BluetoothAdapter.getDefaultAdapter(); if(bt!=null&&!bt.isEnabled){ bt.enable(); true } else false } catch(e:Exception){ false }
    fun fixAirplaneMode(): Boolean = try { if(Settings.Global.getInt(context.contentResolver,Settings.Global.AIRPLANE_MODE_ON,0)==1){ Settings.Global.putInt(context.contentResolver,Settings.Global.AIRPLANE_MODE_ON,0); val intent=android.content.Intent(android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED); intent.putExtra("state",false); context.sendBroadcast(intent); true } else false } catch(e:Exception){ false }
    fun fixHotspot(): Boolean = try { val method=WifiManager::class.java.getMethod("isWifiApEnabled"); val enabled=method.invoke(wifi) as Boolean; if(enabled){ val m=WifiManager::class.java.getDeclaredMethod("setWifiApEnabled",android.net.wifi.WifiConfiguration::class.java,Boolean::class.javaPrimitiveType); m.invoke(wifi,null,false); true } else false } catch(e:Exception){ false }
}
