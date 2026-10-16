//main4.dart

import 'package:flutter/material.dart';

//Flutter Builder: https://flutterstudio.app/

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  //다운로드 중인지 여부를 담는 상태 변수
  bool isDownloading = false;

  //버튼을 눌렀을 때 실행되는 함수
  void download() {
    setState(() {
      isDownloading = true;//버튼 -> 진행 표시기로 변경
    });

    //실제 다운로드 대신 3초 기다리기
    Future.delayed(const Duration(seconds: 3), () {
      if (!mounted) return;//화면이 사라진 뒤 setState 하면 에러
      setState(() {
        isDownloading = false;//다시 버튼으로 되돌리기
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title:'ch05',
      home:Scaffold(
        appBar: AppBar(title:Text('Subject')),
        body:Center(
          //상태 변수에 따라 보여줄 위젯을 바꿔준다
          child: isDownloading
              ? CircularProgressIndicator()
              : ElevatedButton(
                  onPressed: download,
                  child: Text('다운로드'),
                ),
        )
      )
    );
  }
}
