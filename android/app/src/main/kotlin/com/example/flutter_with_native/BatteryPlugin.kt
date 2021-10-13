package com.example.flutter_with_native

import android.content.*
import android.os.BatteryManager
import android.os.Build
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class BatteryPlugin(context: Context): MethodChannel.MethodCallHandler, EventChannel.StreamHandler {
    private val applicationContext: Context = context
    private var batteryLevel: Int = 0
    private var batteryLevelReceiver: BroadcastReceiver? = null

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if(call.method == "getBatteryLevel") {
            batteryLevel = getBatteryLevel()

            if(batteryLevel != -1) {
                result.success(batteryLevel)
            } else {
                result.error("UNAVAILABLE", "Battery level not available.", null)
            }
        } else {
            result.notImplemented()
        }
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        batteryLevelReceiver = makeBatteryLevelReceiver(events)
        applicationContext.registerReceiver(batteryLevelReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        batteryLevel = getBatteryLevel()

        if(batteryLevel != -1) {
            events!!.success(batteryLevel)
        } else {
            events!!.error("UNAVAILABLE", "Battery level not available.", null)
        }
    }

    override fun onCancel(arguments: Any?) {
        applicationContext.unregisterReceiver(batteryLevelReceiver)
        batteryLevelReceiver = null
    }

    private fun getBatteryLevel(): Int {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager = applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
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

    private fun makeBatteryLevelReceiver(events: EventChannel.EventSink?) : BroadcastReceiver {
        return MyBroadCast(events)
    }
}

class MyBroadCast(events: EventChannel.EventSink?): BroadcastReceiver() {
    val events: EventChannel.EventSink? = events

    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryLevel: Int = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(
            BatteryManager.EXTRA_SCALE, -1);

        events!!.success(batteryLevel)
    }

}