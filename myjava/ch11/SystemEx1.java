package ch11;

public class SystemEx1 {
    public static void main(String[] args) {
        //System: 현재 시스템을 객체화 시킨 클래스
        System.out.println("출력");
        //1970년 1월 1일부터 현재까지 1초를 밀리초(1초-1000)로 계산
        long time = System.currentTimeMillis(); //유니크(unique: 유일한) 값으로 사용가능
        System.out.println("time: "+ time);
        for(int i =0;i<1000000;i++){
            System.out.println("Print");
        }
        long time1 = System.currentTimeMillis();
        System.err.println("time1: "+ time1);
        System.out.println("작업시간: " + (time1-time));
        //0은 보통 정상적인 종료 의미
        //매개변수는 다른 프로그램에 전달되는 값으로 1,2,3 비정상적인 종료 세팅하는것
        System.exit(0);

    }
}
