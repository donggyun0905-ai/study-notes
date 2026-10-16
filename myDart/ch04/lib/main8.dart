//main8.dart

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
    return DefaultTabController(
        length: 3,
        child: Scaffold(
          appBar: AppBar(
            title:Text('Tab'),
            bottom:TabBar(
                tabs:[
                  Tab(icon:Icon(Icons.tag_faces),),
                  Tab(text:'메뉴2',),
                  Tab(icon: Icon(Icons.info),text: '메뉴3',)
                ]),
          ),
          body:TabBarView(
            children: [
              Container(color:Colors.yellow,),
              Container(color:Colors.brown,),
              Container(color:Colors.cyan,),
            ]),
      )
    );
  }
}