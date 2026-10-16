//main.dart

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ch05',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Subject'),),
      body: Column(
        children: [
          TextField(),
          TextField(decoration: InputDecoration(labelText: '여기에 입력하세요'),),
          SizedBox(height: 30,),
          TextField(
            decoration: InputDecoration(
                labelText: '여기에 입력하세요',
                border: OutlineInputBorder()),
          )
        ],
      ),
    );
  }
}










