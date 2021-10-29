package com.example.flutter_with_native

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.flutter_with_native.plugins.BatteryInfo
import io.flutter.plugin.common.EventChannel

class BatteryBroadcastReceiver() : BroadcastReceiver() {
    private var batteryInfo : BatteryInfo? = null
    private var events: EventChannel.EventSink? = null
    private lateinit var send: (events: EventChannel.EventSink?) -> Unit

    constructor(eventSink: EventChannel.EventSink?, successEvent: (events: EventChannel.EventSink?) -> Unit, batteryInfo : BatteryInfo) : this() {
        this.events = eventSink
        this.send = successEvent
        this.batteryInfo = batteryInfo
    }

    override fun onReceive(context: Context, intent: Intent) {
        batteryInfo?.batteryCharging = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        batteryInfo?.batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(
            BatteryManager.EXTRA_SCALE, -1);

        send(events)
    }
}