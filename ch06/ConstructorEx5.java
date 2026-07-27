package ch06;

class Normal{
    int a;
    String str;

    Normal(){
        /*생성자 기본 기능, 추가기능*/
    }
    Normal(int a){
        this.a = a;
        /*생성자 기본 기능, 추가기능*/
    }
    Normal(String str){
        this.str = str;
        /*생성자 기본 기능, 추가기능*/
    }
    Normal(int a, String str){
        this.a = a;
        this.str = str;
        /*생성자 기본 기능, 추가기능*/
    }
}

class Smart{
    int a;
    String str;

    //this():자기 자신의 생성자 호출, 반드시 생성자에 첫 번째 라인에 와야함.
    //그래서 super 충돌남.
    Smart(){
        this(10,"Hi");
    }

    Smart(int a){
        this(a,"Hi");
    }

    Smart(String str){
        this(10,str);
    }

    Smart(int a,String str){
        super();
        this.a = a;
        this.str = str;
    }
}

public class ConstructorEx5 {
    public static void main(String[] args) {

    }
}
