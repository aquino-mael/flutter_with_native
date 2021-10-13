import '../../widgets/router_navigator.dart';
import '../screen.dart';

class HomePresenter {
  final RouterNavigator routerNavigator;

  const HomePresenter(this.routerNavigator);

  void navigateToBatteryInfoPage() async {
    return routerNavigator.navigateTo(BatteryInfoScreen.routeName);
  }
}