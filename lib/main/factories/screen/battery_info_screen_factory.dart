import 'package:flutter/material.dart';

import '../../../ui/screen/screen.dart';
import '../factories.dart';

Widget makeBatteryInfoScreen(BuildContext context) => BatteryInfoScreen(
  presenter: makeBatteryInfoPresenter(),
);