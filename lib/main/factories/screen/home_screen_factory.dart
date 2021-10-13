import 'package:flutter/material.dart';

import '../../../ui/screen/screen.dart';
import '../factories.dart';

Widget makeHomeScreen(BuildContext context) => HomeScreen(
  presenter: makeHomePresenter(),
);