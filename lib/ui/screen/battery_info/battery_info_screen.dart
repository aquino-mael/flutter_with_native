import 'package:flutter/material.dart';

import 'battery_info.dart';

class BatteryInfoScreen extends StatefulWidget {
  static const String routeName = '/battery_info';

  final BatteryInfoPresenter presenter;

  BatteryInfoScreen({ Key? key, required this.presenter}) : super(key: key);

  @override
  _BatteryInfoScreenState createState() => _BatteryInfoScreenState();
}

class _BatteryInfoScreenState extends State<BatteryInfoScreen> {
  BatteryInfoPresenter get _presenter => widget.presenter;

  @override
  void initState() {
    super.initState();

    _presenter.getBatteryLevel();
  }

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
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          StreamBuilder(
            stream: _presenter.batteryInfos['level'],
            builder: (context, snapshot) {
              return Text(
                '${snapshot.data ?? 0}',
                style: TextStyle(
                  fontSize: 34.0
                ),
              );
            },
          ),
          StreamBuilder(
            stream: _presenter.batteryInfos['charging'],
            builder: (context, snapshot) {
              return Text(
                '${snapshot.data}',
                style: TextStyle(
                  fontSize: 24.0
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}