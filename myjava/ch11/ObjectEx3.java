package ch11;

class Member{
    String id;

    public Member(String id){
        this.id = id;
    }

    @Override //Member 타입이고 id값이 같은 객체이면 true 리턴
    public boolean equals(Object obj){
        //instnaceof 연산자: 타입을 비교 연산자
        if(obj instanceof Member){
            Member member = (Member)obj;//매개변수 타입이 Member이기 때문에 캐스팅 에러 안남.
            if(id.equals(member.id)){
                return true;
            }
        }
        return false;
    }
}

public class ObjectEx3 {
    public static void main(String[] args) {
        Member m1 = new Member("aaa");
        Member m2 = new Member("aaa");
        Member m3 = new Member("bbb");
        if(m1.equals(m2)){
            System.out.println("m1과 m2는 같다");
        }else{
            System.out.println("m1과 m2는 다르다");
        }
        if(m1.equals(m3)){
            System.out.println("m1과 m3는 같다");
        }else{
            System.out.println("m1과 m3는 다르다");
        }
    }
}
