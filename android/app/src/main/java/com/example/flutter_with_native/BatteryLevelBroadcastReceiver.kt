package com.example.flutter_with_native

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import io.flutter.plugin.common.EventChannel.EventSink

class BatteryLevelBroadcastReceiver(): BroadcastReceiver() {
    private var events: EventSink? = null

    constructor(eventSink: EventSink?) : this() {
        this.events = eventSink
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryLevel: Int = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(
            BatteryManager.EXTRA_SCALE, -1);
        events!!.success(batteryLevel)
    }
}