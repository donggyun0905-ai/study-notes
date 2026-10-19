//carousel_page.dart

//https://pub.dev/packages?q=carousel_slider
/*
pubspec.yaml
 dependencies:
  flutter:
    sdk: flutter
  carousel_slider: ^5.0.0 # 최신 버전으로 업데이트

  flutter:
  assets:
    - assets/
*/


import 'package:flutter/material.dart';
import 'package:carousel_slider/carousel_slider.dart';

final dummyItems = [
  'assets/image1.jpg',
  'assets/image2.jpg',
  'assets/image3.jpg'
];

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Carousel Slider',
      theme: ThemeData(
        appBarTheme: AppBarTheme(
          backgroundColor: Colors.blue,
          foregroundColor: Colors.white,
        )
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  var _index = 0;//1,2,3
  var _pages = [Page1(), Page2(), Page3()];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('복잡한 UI', style: TextStyle(color: Colors.black),),
        centerTitle: true,//중앙에 위치
        actions: [
          IconButton(onPressed: (){}, icon: Icon(Icons.add, color: Colors.black,))
        ],
      ),
      body: _pages[_index],
      bottomNavigationBar:
        BottomNavigationBar(
            onTap: (index){
              setState(() {
                _index = index;
              });
            },
            currentIndex: _index,
            items: [
              BottomNavigationBarItem(icon: Icon(Icons.home), label: '홈'),
              BottomNavigationBarItem(icon: Icon(Icons.assessment), label: '이용서비스'),
              BottomNavigationBarItem(icon: Icon(Icons.account_circle), label: '내 정보'),
            ]),
    );
  }
}

class Page1 extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return ListView(
      children: [buildTop(), buildMiddle(),buildBottom()],
    );
  }

    //상단
  Widget buildTop(){
      return Padding(
          padding: const EdgeInsets.only(top: 20, bottom: 20),
          child: Column(
            children: [
              Row(
                //모든 자식 사이의 간격을 균등하게 정렬
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                 GestureDetector(
                   onTap: (){
                     print('클릭');
                   },
                   child: Column(children:[Icon(Icons.local_taxi, size: 40,), Text('택시')],),
                 ),
                  Column(children: [Icon(Icons.local_taxi, size: 40,), Text('블랙')],),
                  Column(children: [Icon(Icons.local_taxi, size: 40,), Text('바이크')],),
                  Column(children: [Icon(Icons.local_taxi, size: 40,), Text('대리')],),
                ],
              ),
              SizedBox(height: 20,),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Column(children: [Icon(Icons.local_taxi, size: 40,), Text('택시')],),
                  Column(children: [Icon(Icons.local_taxi, size: 40,), Text('블랙')],),
                  Column(children: [Icon(Icons.local_taxi, size: 40,), Text('바이크')],),
                  Opacity(
                      opacity: 0.5,
                      child: Column(children: [Icon(Icons.local_taxi, size: 40,), Text('바이크')],),
                  )
                ],
              )
            ],
          ),

      );
  }

    //중단
  Widget buildMiddle() {
    return CarouselSlider(
        options: CarouselOptions(height: 150, autoPlay: true),
        items: dummyItems.map((path){
          return Builder(builder: (BuildContext context){
            return Container(
              width: MediaQuery.of(context).size.width,//현재화면 너비
              margin: EdgeInsets.symmetric(horizontal: 5.0),//좌우여백 5
              decoration: BoxDecoration(color: Colors.amber),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(8.0),//모서리를 둥글기
                child: Image.asset(path, fit: BoxFit.cover,),//크기는 화면에 맞게
              ),
            );
          });
        }).toList(),
    );
  }

    //하단
    Widget buildBottom() {
      final items = List.generate(10, (i){
        return ListTile(
          leading: Icon(Icons.notifications_none),
          title: Text('[이벤트] 이것은 공지사항입니다'),
        );
      });

      return ListView(
        //상위 위젯이 스크롤 기능이 있으면 자신은 스크롤 비활성화
        physics: NeverScrollableScrollPhysics(),
        shrinkWrap: true, //상위 위젯과 크기 동일하게 세팅
        children: items,
      );
    }
}



class Page2 extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Center(child: Text('이용서비스', style: TextStyle(fontSize: 40),),);
  }
}

class Page3 extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Center(child: Text('내 정보', style: TextStyle(fontSize: 40),),);
  }
}






































