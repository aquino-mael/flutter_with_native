import 'package:flutter/material.dart';

import 'home.dart';

class HomeScreen extends StatelessWidget {
  static const String routeName = '/home_screen';

  final HomePresenter presenter;

  const HomeScreen({ Key? key, required this.presenter }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildAppBar(),
      body: _buildBody(),
    );
  }

  PreferredSizeWidget _buildAppBar() {
    return AppBar(
      title: Text('Flutter with native'),
    );
  }

  Widget _buildBody() {
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          ElevatedButton(
            onPressed: presenter.navigateToBatteryInfoPage,
            child: Text('Check Battery infos'),
          ),
        ],
      ),
    );
  }
}