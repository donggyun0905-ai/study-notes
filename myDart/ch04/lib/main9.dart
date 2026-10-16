//main9.dart

import 'package:flutter/material.dart';

//Flutter Builder: https://flutterstudio.app/

void main() => runApp(MyApp());

class MyApp extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ch04',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Subject'),),
      bottomNavigationBar:BottomNavigationBar(items:[
        BottomNavigationBarItem(icon: Icon(Icons.home),label:'Home'),
        BottomNavigationBarItem(icon: Icon(Icons.person),label:'Profile'),
        BottomNavigationBarItem(icon: Icon(Icons.notifications),label:'Notification'),
      ]),
    );
  }
}
