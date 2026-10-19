//main7.dart

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ch05',
      home: Scaffold(
        appBar: AppBar(title: Text('Subject')),
        body: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    GestureDetector(
                      onTap: (){
                        print('GestureDetector 클릭됨....');
                      },
                      child: Text('클릭 Me!!', style: TextStyle(fontSize: 40),),
                    ),
                    SizedBox(height: 50,),
                    InkWell(
                      onTap: (){
                        print('InkWell 클릭됨....');
                      },
                      child: Text('클릭2 Me!!', style: TextStyle(fontSize: 40),),
                    )
                  ],
                ),
              )
      ),
    );
  }
}







