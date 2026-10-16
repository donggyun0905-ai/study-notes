//main3.dart

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  final valueList = ['첫번째', '두번째', '세번째'];
  var selectedValue = '첫번째';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ch05',
      home: Scaffold(
        appBar: AppBar(title: Text('Subject')),
        body: Center(
          child: DropdownButton(
            value: selectedValue,//현재 선택된 항목값
              items: valueList.map((value){//드룹다운에 표시될 항목 리스트
                return DropdownMenuItem(value: value, child: Text(value));
            }).toList(),
              onChanged: (value){
                setState(() {
                  selectedValue = value!;//null값이 아닐때 사용(부정의 의미는 아님)
                });
              })
        ),
      ),
    );
  }
}