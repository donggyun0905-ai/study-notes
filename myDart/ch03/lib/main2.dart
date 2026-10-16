//main2.dart

import 'package:flutter/material.dart';
/*
void main(){
  runApp(MyApp());
}
*/
void main() => runApp(MyApp());

class MyApp extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title:'Flutter Demo',
      //기본색상
      theme: ThemeData(primarySwatch: Colors.blue),
      home : MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Hello World'),),
      body: Text('반갑다 Flutter',style: TextStyle(fontSize: 50),),
    );
  }
}