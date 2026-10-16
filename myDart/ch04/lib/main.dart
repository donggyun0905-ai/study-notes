//main.dart
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
      body: Container(
        color:Colors.red,
        width: 100,
        height: 100,
        //EdgeInsets.all: 상 하 좌 우
        //padding : 안쪽이 여백 margin : 바깥쪽 여백
        padding: const EdgeInsets.all(8.0),
        margin: EdgeInsets.all(8.0),
      ),
    );
  }
}