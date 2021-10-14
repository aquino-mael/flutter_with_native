package com.example.flutter_with_native

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import io.flutter.plugin.common.EventChannel

class BatteryChargingBroadcastReceiver() : BroadcastReceiver() {
    private var events: EventChannel.EventSink? = null
    private lateinit var send: (events: EventChannel.EventSink?, status: Int) -> Unit

    constructor(eventSink: EventChannel.EventSink?, successEvent: (events: EventChannel.EventSink?, status: Int) -> Unit) : this() {
        this.events = eventSink
        this.send = successEvent
    }

    override fun onReceive(context: Context, intent: Intent) {
        val chargingStatus: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        send(events, chargingStatus)
    }
}