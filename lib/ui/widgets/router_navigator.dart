import 'package:flutter/material.dart';

import '../../main/factories/factories.dart';
import '../screen/screen.dart';

class RouterNavigator {
  static final navigatorKey = GlobalKey<NavigatorState>();

  static const String initialRoute = '/home_screen';

  static Map<String, Widget Function(BuildContext)> routes = {
    HomeScreen.routeName: makeHomeScreen,
  };
}