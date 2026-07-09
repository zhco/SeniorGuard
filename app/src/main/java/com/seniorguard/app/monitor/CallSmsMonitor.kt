package com.seniorguard.app.monitor
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.TelephonyManager
import com.seniorguard.app.engine.RepairEngine
class CallSmsMonitor(private val context: Context, private val engine: RepairEngine) {
    private val fraudKeywords = listOf("转账","汇款","安全账户","公安局","检察院","法院","中奖","退税","补贴","验证码","密码","银行卡","贷款","代办","额度","刷单","兼职","返利")
    fun register() {
        context.registerReceiver(smsReceiver, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
        context.registerReceiver(callReceiver, IntentFilter("android.intent.action.PHONE_STATE"))
    }
    fun unregister() { try { context.unregisterReceiver(smsReceiver); context.unregisterReceiver(callReceiver) } catch (_: Exception) {} }
    private val smsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val msgs = android.provider.Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (msg in msgs) {
                if (isFraud((msg.messageBody?:"")+(msg.originatingAddress?:""))) {
                    abortBroadcast()
                    context.contentResolver.delete(android.provider.Telephony.Sms.Inbox.CONTENT_URI,"address = ? AND body = ?", arrayOf(msg.originatingAddress, msg.messageBody))
                }
            }
        }
    }
    private val callReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_RINGING) {
                if (isFraud(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)?:""))
                    (context.getSystemService(Context.TELECOM_SERVICE) as android.telecom.TelecomManager).endCall()
            }
        }
    }
    private fun isFraud(text: String) = fraudKeywords.any { text.contains(it) }
}