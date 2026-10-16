//main10.dart

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
      body: Center(
          //child:ElevatedButton(onPressed: (){}, child: Text('ElevatedButton')),
          //child:TextButton(onPressed: (){},child:Text('TextButton')),
          child: IconButton(
            //onPressed: (){print('Icon 버튼이 클릭되었습니다.');},
            onPressed: iconButtonOnPressed,
            icon:Icon(Icons.add),
            color:Colors.red,
            iconSize:100.0,
          ),
      )
    );
  }

  void iconButtonOnPressed(){
    print('Icon 버튼이 클릭되었습니다.');
  }
}