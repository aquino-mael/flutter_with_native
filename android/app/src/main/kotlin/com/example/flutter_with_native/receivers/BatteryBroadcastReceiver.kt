package com.example.flutter_with_native.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import io.flutter.plugin.common.EventChannel

class BatteryBroadcastReceiver(events: EventChannel.EventSink?): BroadcastReceiver() {
    private val events: EventChannel.EventSink? = events

    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryLevel: Int = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(
            BatteryManager.EXTRA_SCALE, -1);

        events!!.success(batteryLevel)
    }
}