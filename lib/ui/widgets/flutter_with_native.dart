import 'package:flutter/material.dart';

import 'router_navigator.dart';

class FlutterWithNative extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: RouterNavigator.navigatorKey,
      initialRoute: RouterNavigator.initialRoute,
      routes: RouterNavigator.routes,
    );
  }
}