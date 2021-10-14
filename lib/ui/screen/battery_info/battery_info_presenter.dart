import 'dart:async';

import 'package:flutter/services.dart';

class BatteryInfoPresenter {
  Map<String, Stream> batteryInfos = {};
  final batteryInfoChannel = EventChannel('battery_info');

  void getBatteryLevel() {
    try {
      batteryInfos['level'] = batteryInfoChannel.receiveBroadcastStream('battery_level');
    } on PlatformException catch (e) {
      print('$e');
    }
  }

  void getBatteryChargingStatus() {
    try {
      batteryInfos['charging'] = batteryInfoChannel.receiveBroadcastStream('battery_charging');
    } on PlatformException catch (e) {
      print('$e');
    }
  }
}