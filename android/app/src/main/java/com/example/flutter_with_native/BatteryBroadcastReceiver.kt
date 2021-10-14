package com.example.flutter_with_native

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import io.flutter.plugin.common.EventChannel

class BatteryBroadcastReceiver() : BroadcastReceiver() {
    private var events: EventChannel.EventSink? = null
    private lateinit var send: (events: EventChannel.EventSink?, batteryCharging: Int, batteryLevel: Int) -> Unit

    constructor(eventSink: EventChannel.EventSink?, successEvent: (events: EventChannel.EventSink?, batteryCharging: Int, batteryLevel: Int) -> Unit) : this() {
        this.events = eventSink
        this.send = successEvent
    }

    override fun onReceive(context: Context, intent: Intent) {
        val chargingStatus: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val batteryLevel: Int = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(
            BatteryManager.EXTRA_SCALE, -1);

        send(events, chargingStatus, batteryLevel)
    }
}