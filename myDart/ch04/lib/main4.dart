//main4.dart

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
  //0~99까지 번호를 리스트로 만듬
  final items = List.generate(100, (i) => i).toList();


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Subject'),),
      body: SingleChildScrollView(
        child: ListBody(
          //map 함수 : 반복되는 값을 다른 형태로 리턴
          children: items.map((i) => Text('$i')).toList(),
        ),
      ),
    );
  }
}