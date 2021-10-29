import 'package:flutter/material.dart';

import '../../../presenters/presenters.dart';

class BatteryInfoScreen extends StatelessWidget {
  static const String routeName = '/battery_info';

  final BatteryInfoPresenter presenter;

  BatteryInfoScreen({ Key? key, required this.presenter}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Theme(
      data: ThemeData(
        brightness: Brightness.dark,
      ),
      child: Scaffold(
        extendBodyBehindAppBar: true,
        appBar: _buildAppBar(),
        body: _buildBody(),
      ),
    );
  }

  PreferredSizeWidget _buildAppBar() {
    return AppBar(
      title: Text('Battery\'s Informations'),
    );
  }

  Widget _buildBody() {
    presenter.getBatteryInfos();

    return StreamBuilder<dynamic>(
      stream: presenter.batteryInfos,
      builder: (context, snapshot) {
        print(snapshot.data);
        int currentBatteryLevel = snapshot.data?['level'] ?? 0;
        double batteryLevelOnScreen = (MediaQuery.of(context).size.height - kToolbarHeight) * currentBatteryLevel / 100;
        return Stack(
          alignment: Alignment.bottomCenter,
          children: [
            AnimatedContainer(
              duration: Duration(seconds: 1),
              color: currentBatteryLevel > 75
                ? Colors.green
                : currentBatteryLevel > 20
                  ? Colors.orange
                  : Colors.red,
              constraints: BoxConstraints(
                maxHeight: snapshot.hasData
                  ? batteryLevelOnScreen
                  : 0,
              ),
            ),
            Center(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text(
                    '${snapshot.data?['level'] ?? 0}',
                    style: TextStyle(
                      fontSize: 34.0,
                    ),
                  ),
                  Text(
                    '${snapshot.data?['charging'] ?? 'UNKNOWN'}',
                    style: TextStyle(
                      fontSize: 24.0,
                    ),
                  ),
                ],
              ),
            ),
          ],
        );
      }
    );
  }
}
