//main3.dart

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  var text = 'Hello';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title:Text('Hello World!!!'),),
      body: Text(text,style: TextStyle(fontSize: 40),),
      floatingActionButton: FloatingActionButton(onPressed: (){
          setState(() {
            //text='World';
            text =(text=='World')?'Hello':'World';
          });
      }),
    );
  }
}
