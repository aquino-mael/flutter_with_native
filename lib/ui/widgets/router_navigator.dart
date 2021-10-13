import 'package:flutter/material.dart';

import '../../main/factories/factories.dart';
import '../screen/screen.dart';

/// Singleton to manage all routes in application
class RouterNavigator {
  const RouterNavigator._();

  static const RouterNavigator _instance = RouterNavigator._();

  factory RouterNavigator.getInstance() => _instance;
  
  static final navigatorKey = GlobalKey<NavigatorState>();

  static const String initialRoute = '/home_screen';

  static Map<String, Widget Function(BuildContext)> routes = {
    HomeScreen.routeName: makeHomeScreen,
  };

  Future<T?> navigateTo<T>(String routeName) async {
    return navigatorKey.currentState!.pushNamed<T>(routeName);
  }
}