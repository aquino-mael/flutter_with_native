import 'package:flutter/material.dart';

import 'battery_info.dart';

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

    return Stack(
      alignment: Alignment.bottomCenter,
      children: [
        StreamBuilder<dynamic>(
          stream: presenter.batteryInfos,
          builder: (context, snapshot) {
            return Builder(
              builder: (context) => AnimatedContainer(
                duration: Duration(seconds: 1),
                color: Colors.green,
                constraints: BoxConstraints(
                  maxHeight: snapshot.hasData
                    ? (MediaQuery.of(context).size.height / (snapshot.data?['level'] ?? 0)) * 100
                    : 0,
                ),
              ),
            );
          },
        ),
        Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              StreamBuilder<dynamic>(
                stream: presenter.batteryInfos,
                builder: (context, snapshot) {
                  return Text(
                    '${snapshot.data?["level"] ?? 0}',
                    style: TextStyle(
                      fontSize: 34.0,
                    ),
                  );
                },
              ),
              StreamBuilder<dynamic>(
                stream: presenter.batteryInfos,
                builder: (context, snapshot) {
                  return Text(
                    '${snapshot.data?['charging'] ?? 'UNKNOWN'}',
                    style: TextStyle(
                      fontSize: 24.0,
                    ),
                  );
                },
              ),
            ],
          ),
        ),
      ],
    );
  }
}