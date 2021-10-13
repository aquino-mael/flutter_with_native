import '../../../ui/screen/home/home.dart';
import '../../../ui/widgets/router_navigator.dart';

HomePresenter makeHomePresenter() => HomePresenter(
  RouterNavigator.getInstance(),
);