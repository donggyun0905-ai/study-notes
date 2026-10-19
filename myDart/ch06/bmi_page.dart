//bmi_page.dart

//비만도 측정

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'BMI 측정',
      home: BmiMain(),
    );
  }
}

class BmiMain extends StatefulWidget {
  @override
  State<BmiMain> createState() => _BmiMainState();
}

//비만도 입력 페이지
class _BmiMainState extends State<BmiMain> {

  //유효성 검사
  final formKey = GlobalKey<FormState>();
  final heightController = TextEditingController();
  final weightController = TextEditingController();

  //불필요한 리소스 정리 메소드이고 화면이 사라질때 자동으로 호출
  @override
  void dispose() {
   heightController.dispose();
   weightController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('비만도 계산기'),),
      body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Form(
              key: formKey,
              child: Column(
                children: [
                  TextFormField(
                    decoration: InputDecoration(
                      border: OutlineInputBorder(),
                      hintText: '키'
                    ),
                    controller: heightController,
                    keyboardType: TextInputType.number,
                    validator: (value){
                      if(value != null && value.trim().isEmpty){
                        return '키를 입력하세요';
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: 16,),
                  TextFormField(
                    decoration: InputDecoration(
                        border: OutlineInputBorder(),
                        hintText: '몸무게'
                    ),
                    controller: weightController,
                    keyboardType: TextInputType.number,
                    validator: (value){
                      if(value != null && value.trim().isEmpty){
                        return '몸무게를 입력하세요';
                      }
                      return null;
                    },
                  ),
                  Container(
                    margin: const EdgeInsets.only(top: 16.0),
                    alignment: Alignment.centerRight,
                    child: ElevatedButton(
                        onPressed: () async{
                          //폼에 유효성 검사
                          if(formKey.currentState!.validate()){
                            await Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) =>
                                      BmiResult(
                                        double.parse(heightController.text.trim()),
                                        double.parse(weightController.text.trim())
                                      )
                              )
                            );
                            //돌아온 뒤 입력창 비우기
                            heightController.clear();
                            weightController.clear();
                          }
                        },
                        child: Text('결과')),
                  )
                ],
              )
          ),
      ),
    );
  }
}

//BMI 결과 페이지
class BmiResult extends StatelessWidget {

  final double height;
  final double weight;

  BmiResult(this.height, this.weight);

  @override
  Widget build(BuildContext context) {
    final bmi = weight / ((height / 100) * (height / 100));
    print('bmi: $bmi');
    return Scaffold(
      appBar: AppBar(title: Text('비만도 계산기'),),
      body: Center(
        child: Column(
          children: [
            Text(calcBmi(bmi), style: TextStyle(fontSize: 36),),
            SizedBox(height: 16,),
            buildIcon(bmi)
          ],
        ),
      ),
    );
  }

  //BMI 측정
  String calcBmi(double bmi) {
    var result = '저체중';
    if (bmi >= 35) {
      result = '고도 비만';
    } else if (bmi >= 30) {
      result = '2단계 비만';
    } else if (bmi >= 25) {
      result = '1단계 비만';
    } else if (bmi >= 23) {
      result = '과체중';
    } else if (bmi >= 18.5) {
      result = '정상';
    }
    return result;
  }

  //BMI 아이콘
  Widget buildIcon(double bmi){
    if(bmi >= 23){
      return Icon(Icons.sentiment_very_dissatisfied, color: Colors.red, size: 100,);
    }else if(bmi >= 18.5){
      return Icon(Icons.sentiment_satisfied, color: Colors.green, size: 100,);
    }else{
      return Icon(Icons.sentiment_dissatisfied, color: Colors.orange, size: 100,);
    }
  }
}


