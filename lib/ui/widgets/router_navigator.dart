import 'package:flutter/material.dart';

class RouterNavigator {
  static final navigatorKey = GlobalKey<NavigatorState>();

  static const String initialRoute = '/home_screen';

  static const Map<String, Widget Function(BuildContext)> routes = {};
}