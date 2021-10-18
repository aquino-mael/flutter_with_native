import 'package:flutter/material.dart';

import '../../../presenters/presenters.dart';
import '../../../ui/screen/screen.dart';

Widget makeBatteryInfoScreen(BuildContext context) => BatteryInfoScreen(
  presenter: BatteryInfoPresenter(),
);