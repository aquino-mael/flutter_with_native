import 'dart:async';

import 'package:flutter/services.dart';

class BatteryInfoPresenter {
  late Stream batteryInfos;
  final batteryInfosChannel = EventChannel('battery_info');

  void getBatteryInfos() {
    try {
      batteryInfos = batteryInfosChannel.receiveBroadcastStream();
    } on PlatformException catch (e) {
      print('$e');
    }
  }
}