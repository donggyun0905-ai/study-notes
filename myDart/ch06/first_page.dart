//first_page.dart

import 'package:flutter/material.dart';
import 'package:ch06/main.dart';
import 'package:ch06/second_page.dart';

class FirstPage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('first'),),
      body: ElevatedButton(
          onPressed: (){
            final person = Person('홍길동', 28);
            //새로운 화면(Route)를 Stack에 쌓는다.
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => SecondPage(person: person))
            );
          }, 
          child: Text('다음 페이지로')),
    );
  }
}
