//main7.dart

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
      appBar: AppBar(title: Text('PageView'),),
      body: PageView(
        children:[
          Container(color: Colors.red,),
          Container(color: Colors.blueGrey,),
          Container(color: Colors.blueAccent,),
        ]
      ),
    );
  }
}