package com.example.flutter_with_native.plugins

import android.content.*
import android.os.BatteryManager
import android.os.Build
import com.example.flutter_with_native.BatteryBroadcastReceiver
import io.flutter.plugin.common.EventChannel

class BatteryPlugin(context: Context): EventChannel.StreamHandler {
    private val applicationContext: Context = context
    private var batteryLevel: Int = 0
    private var batteryCharging: Int = 0
    private var batteryReceiver: BroadcastReceiver? = null
    private val batteryManager = applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        batteryReceiver = makeBatteryChargingReceiver(events)
        applicationContext.registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        batteryCharging = getBatteryChargingStatus()
        batteryLevel = getBatteryLevel()

        sendDataToDart(events, batteryCharging, batteryLevel)
    }

    override fun onCancel(arguments: Any?) {
        applicationContext.unregisterReceiver(batteryReceiver)
        batteryReceiver = null
    }

    private fun getBatteryLevel(): Int {
        var batteryLevel: Int = 0;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(
                Intent.ACTION_BATTERY_CHANGED)
            )
            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(
                BatteryManager.EXTRA_SCALE, -1)
        }

        return batteryLevel
    }

    private fun getBatteryChargingStatus(): Int {
        var chargingStatus: Int

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            chargingStatus = batteryManager.getIntProperty(BatteryManager.BATTERY_STATUS_CHARGING)
        } else {
            chargingStatus = BatteryManager.BATTERY_STATUS_UNKNOWN
        }

        return chargingStatus
    }

    private fun sendDataToDart(events: EventChannel.EventSink?, batteryCharging: Int, batteryLevel: Int) {
        var currentBatteryChargingStatus: String = "";
        when(batteryCharging) {
            BatteryManager.BATTERY_STATUS_CHARGING ->
                currentBatteryChargingStatus = "charging"
            BatteryManager.BATTERY_STATUS_FULL ->
                currentBatteryChargingStatus = "full"
            BatteryManager.BATTERY_STATUS_NOT_CHARGING ->
                currentBatteryChargingStatus = "isn't charging"
            BatteryManager.BATTERY_STATUS_DISCHARGING ->
                currentBatteryChargingStatus = "discharging"
            BatteryManager.BATTERY_STATUS_UNKNOWN ->
                currentBatteryChargingStatus = "unknown"
        }

        val result: MutableMap<String, Any?> = mutableMapOf<String, Any?>()

        result["charging"] = currentBatteryChargingStatus
        result["level"] = batteryLevel

        events!!.success(result)
    }

    private fun makeBatteryChargingReceiver(events: EventChannel.EventSink?) : BroadcastReceiver {
        return BatteryBroadcastReceiver(events, ::sendDataToDart)
    }
}
