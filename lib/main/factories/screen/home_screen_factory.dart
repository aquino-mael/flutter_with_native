import 'package:flutter/material.dart';

import '../../../presenters/presenters.dart';
import '../../../ui/screen/screen.dart';

Widget makeHomeScreen(BuildContext context) => HomeScreen(
  presenter: HomePresenter(),
);