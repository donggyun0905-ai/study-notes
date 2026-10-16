//main1.dart
//flutter 기초 구조 분석

//flutter 앱의 ui 요소를 만들 때 기본으로 사용
import 'package:flutter/material.dart';
// 앱 시작 부분
void main(){
  runApp(new MyApp());
}

//람다스타일
void main1() => runApp(MyApp());

/*StatelessWidget은 상태가 변하지 않는 위잿을 의미
ui가 변경되지 않고 항상 동일하게 렌더링되는 class
만약 상태 변화를 감지하여 ui 변경해야 한다면 StatefulWidget을 사용
 */

//StatelessWidget을 상속받아서 생성된 Flutter 앱의 기본 클래스
class MyApp extends StatelessWidget{
  @override
  //위젯을 만들어서 리턴하는 메소드
  Widget build(BuildContext context) {
    //Material Design을 기반으로 한 Flutter 앱을 정의
    return MaterialApp(
      title:'Flutter App',
      //Scaffold(발판):기본적인 페이지 구조를 제공
      home: Scaffold(
        appBar: AppBar(title:Text('MyApp'),),
        body: Center(
          child: Text('반갑다 Flutter',style:TextStyle(fontSize: 24),),
        ),
      ),
    );
  }
}