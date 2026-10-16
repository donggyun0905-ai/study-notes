//main2.dart

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
      body: Row(
        //정렬세팅
        mainAxisSize:MainAxisSize.max,//가로로 꽉 채우기
        crossAxisAlignment: CrossAxisAlignment.center,//가로방향으로 가운데 정렬하기
        //mainAxisAlignment: ,
        children: [
          //현재 커서 복사는 ctrl + d
          Container(
            color: Colors.orange,
            width: 100,
            height: 100,
            padding: const EdgeInsets.all(8.0),
            margin: const EdgeInsets.all(8.0),
          ),
          Container(
            color: Colors.brown,
            width: 100,
            height: 100,
            padding: const EdgeInsets.all(8.0),
            margin: const EdgeInsets.all(8.0),
          ),
          Container(
            color: Colors.cyan,
            width: 100,
            height: 100,
            padding: const EdgeInsets.all(8.0),
            margin: const EdgeInsets.all(8.0),
          ),
        ],
      ),
    );
  }
}