import 'package:flutter_with_native/ui/widgets/router_navigator.dart';

import '../ui/screen/screen.dart';

class HomePresenter {
  const HomePresenter();

  void navigateToBatteryInfoPage() async {
    return RouterNavigator.navigateTo(BatteryInfoScreen.routeName);
  }
}