package com.example.flutter_with_native

import androidx.annotation.NonNull
import com.example.flutter_with_native.plugins.BatteryPlugin
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel

class MainActivity: FlutterActivity() {
  private val CHANNEL = "battery_info"

  override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
    super.configureFlutterEngine(flutterEngine)
    val eventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL);
    eventChannel.setStreamHandler(
      BatteryPlugin(context)
    )
  }
}