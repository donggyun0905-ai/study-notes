//main1.dart

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  bool isChecked = false;

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
              Checkbox(
                  value: isChecked,
                  onChanged: (value){
                    //setState: 상태 변수를 바꾸고 화면 다시 그리도록 요청
                    setState(() {
                      isChecked = value!;
                    });
                  }),
              SizedBox(height: 50,),
              Switch(
                  value: isChecked,
                  //람다함수, 익명함수를 화살표 함수 사용
                  onChanged: (value) => setState(() => isChecked = value))
            ],
          ),
        ),
      ),
    );
  }
}
