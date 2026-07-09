package com.seniorguard.app.engine
import android.content.Context
import android.provider.Settings
class DisplayRepair(private val context: Context) : RepairModule {
    override suspend fun fixAll(): Int {
        var count = 0
        if (fixFontSize()) count++
        if (fixBrightness()) count++
        if (fixScreenTimeout()) count++
        if (fixAutoRotate()) count++
        return count
    }
    fun fixFontSize(): Boolean = try {
        if (Settings.System.getFloat(context.contentResolver, Settings.System.FONT_SCALE, 1.0f) < 1.3f) {
            Settings.System.putFloat(context.contentResolver, Settings.System.FONT_SCALE, 1.3f); true
        } else false
    } catch (e: Exception) { false }
    fun fixBrightness(): Boolean = try {
        if (Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
            Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        val cur = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        if (cur < 128 || cur > 240) { Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 180); true } else false
    } catch (e: Exception) { false }
    fun fixScreenTimeout(): Boolean = try {
        if (Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT) < 120000) {
            Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 120000); true
        } else false
    } catch (e: Exception) { false }
    fun fixAutoRotate(): Boolean = try {
        if (Settings.System.getInt(context.contentResolver, Settings.System.ACCELEROMETER_ROTATION) == 0) {
            Settings.System.putInt(context.contentResolver, Settings.System.ACCELEROMETER_ROTATION, 1); true
        } else false
    } catch (e: Exception) { false }
}