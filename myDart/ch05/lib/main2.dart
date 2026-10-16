//main2.dart

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

enum Gender {MAN, WOMEN}

class _MyAppState extends State<MyApp> {

  Gender gender = Gender.MAN;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ch05',
      home: Scaffold(
        appBar: AppBar(title: Text('Subject')),
        body: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  ListTile(
                    title: Text('남자'),
                    leading: Radio(
                        value: Gender.MAN,
                        groupValue: gender,
                        onChanged: (Gender? value){
                          setState(() {
                            gender = value!;
                          });
                        },
                    ),
                  ),
                  ListTile(
                    title: Text('여자'),
                    leading: Radio(
                      value: Gender.WOMEN,
                      groupValue: gender,
                      onChanged: (Gender? value){
                        setState(() {
                          gender = value!;
                        });
                      },
                    ),
                  ),
                  SizedBox(height: 100,),
                  RadioListTile(
                      title: Text('남자'),
                      value: Gender.MAN,
                      groupValue: gender,
                      onChanged: (Gender? value){
                        setState(() {
                          gender = value!;
                        });
                    },
                  ),
                  RadioListTile(
                    title: Text('여자'),
                    value: Gender.WOMEN,
                    groupValue: gender,
                    onChanged: (Gender? value){
                      setState(() {
                        gender = value!;
                      });
                    },
                  ),
                ],
              ),
            ),
        ),
      ),
    );
  }
}







