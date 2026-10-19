//second_page.dart

import 'package:ch06/main.dart';
import 'package:flutter/material.dart';

class SecondPage extends StatelessWidget {

  final Person person;

  const SecondPage({required this.person});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Second'),),
      //화면에 넘겨 받은 person:name, age 표시. 정렬은 중앙
      //Hint: Column 사용
      body: Center(
        child: Column(
          children: [
            //넘겨받은 person 표시
            Text('이름: ${person.name}', style: TextStyle(fontSize: 24),),
            SizedBox(height: 8,),
            Text('나이: ${person.age}', style: TextStyle(fontSize: 24),),
            SizedBox(height: 30,),
            ElevatedButton(
                onPressed: () {
                  //현재 페이지를 네이게이션 스택에서 제거하고 이전 페이지로 전환
                  Navigator.pop(context);
                },
                child: Text('이전 페이지로'))
          ],
        ),
      )
    );
  }
}










