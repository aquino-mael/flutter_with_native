package com.example.flutter_with_native.plugins

import android.content.*
import android.os.BatteryManager
import android.os.Build
import com.example.flutter_with_native.BatteryChargingBroadcastReceiver
import com.example.flutter_with_native.BatteryLevelBroadcastReceiver
import io.flutter.plugin.common.EventChannel

class BatteryPlugin(context: Context) : EventChannel.StreamHandler {
    private val applicationContext: Context = context
    private var batteryLevel: Int = 0
    private var batteryCharging: Int = 0
    private var batteryLevelReceiver: BroadcastReceiver? = null
    private var batteryChargingReceiver: BroadcastReceiver? = null
    private val batteryManager = applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        if(arguments!! == "battery_level") {
            batteryLevelReceiver = makeBatteryLevelReceiver(events)
            applicationContext.registerReceiver(batteryLevelReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryLevel = getBatteryLevel()

            sendBatteryLevel(events, batteryLevel)
        } else if(arguments!! == "battery_charging") {
            batteryChargingReceiver = makeBatteryChargingReceiver(events)
            applicationContext.registerReceiver(batteryChargingReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryCharging = getBatteryChargingStatus()

            sendBatteryChargingStatus(events, batteryCharging)
        } else {
            events!!.error("NOT IMPLEMENTED", "Channel not implemented.", arguments)
        }
    }

    override fun onCancel(arguments: Any?) {
        applicationContext.unregisterReceiver(batteryLevelReceiver)
        applicationContext.unregisterReceiver(batteryChargingReceiver)
        batteryLevelReceiver = null
        batteryChargingReceiver = null
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

    private fun sendBatteryLevel(events: EventChannel.EventSink?, batteryLevel: Int) {
        if(batteryLevel != -1) {
            events!!.success(batteryLevel)
        } else {
            events!!.error("UNAVAILABLE", "Battery level not available.", null)
        }
    }

    private fun makeBatteryLevelReceiver(events: EventChannel.EventSink?) : BroadcastReceiver {
        return BatteryLevelBroadcastReceiver(events)
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

    private fun sendBatteryChargingStatus(events: EventChannel.EventSink?, status: Int) {
        when(status) {
            BatteryManager.BATTERY_STATUS_CHARGING ->
                events!!.success("charging")
            BatteryManager.BATTERY_STATUS_FULL ->
                events!!.success("full")
            BatteryManager.BATTERY_STATUS_NOT_CHARGING ->
                events!!.success("isn't charging")
            BatteryManager.BATTERY_STATUS_DISCHARGING ->
                events!!.success("discharging")
            BatteryManager.BATTERY_STATUS_UNKNOWN ->
                events!!.success("unknown")
        }
    }

    private fun makeBatteryChargingReceiver(events: EventChannel.EventSink?) : BroadcastReceiver {
        return BatteryChargingBroadcastReceiver(events, ::sendBatteryChargingStatus)
    }
}
