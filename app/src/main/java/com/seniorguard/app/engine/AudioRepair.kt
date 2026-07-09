package com.seniorguard.app.engine
import android.content.Context
import android.media.AudioManager
import android.app.NotificationManager
class AudioRepair(private val context: Context) : RepairModule {
    private val audio = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    override suspend fun fixAll(): Int { var c=0; if(fixVolume())c++; if(fixMute())c++; if(fixDnd())c++; return c }
    fun fixVolume(): Boolean {
        var fixed=false
        for(s in listOf(AudioManager.STREAM_RING,AudioManager.STREAM_MUSIC,AudioManager.STREAM_NOTIFICATION,AudioManager.STREAM_ALARM)){
            if(audio.getStreamVolume(s)<audio.getStreamMaxVolume(s)*0.7){ audio.setStreamVolume(s,(audio.getStreamMaxVolume(s)*0.8).toInt(),0); fixed=true }
        }
        return fixed
    }
    fun fixMute(): Boolean = try { if(audio.ringerMode in listOf(AudioManager.RINGER_MODE_SILENT,AudioManager.RINGER_MODE_VIBRATE)){ audio.ringerMode=AudioManager.RINGER_MODE_NORMAL; true } else false } catch(e:Exception){ false }
    fun fixDnd(): Boolean = try { val nm=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager; if(nm.currentInterruptionFilter!=NotificationManager.INTERRUPTION_FILTER_ALL){ nm.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL); true } else false } catch(e:Exception){ false }
}