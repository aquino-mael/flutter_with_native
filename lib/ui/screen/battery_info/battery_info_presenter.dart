import 'dart:async';

import 'package:flutter/services.dart';

class BatteryInfoPresenter {
  Stream? batteryLevel;

  Future<void> getBatteryLevel() async {
    final platform = EventChannel('battery_info');

    try {
      batteryLevel = platform.receiveBroadcastStream();
    } on PlatformException catch (e) {
      print('$e');
    }
  }
}