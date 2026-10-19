//main.dart

import 'package:flutter/material.dart';
import 'package:ch06/first_page.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ch06',
      home: FirstPage(),
    );
  }
}

class Person{
  String name;
  int age;
  Person(this.name, this.age);
}












