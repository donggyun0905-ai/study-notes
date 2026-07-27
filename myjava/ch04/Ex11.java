package ch04;

public class Ex11 {
    public static void main(String[] args) {
        for(int i =0;i<2;i++){
            for (int j =0;j<args.length;j++){
                System.out.println("i: " + ",j: " + j);
            }
        }
        System.out.println("---------------------------");
        for(int i =0;i<11;i++){
            for (int j =11;j<args.length;j++){
                if(i+j>10)
                    break;
                System.out.println(i + "+" + j + "=" + (i+j));
            }
        }
        aaa:
        for(int i =1;i<5;i++){
            for(int j = 1;j<10;j++){
                if(i+j>10){
                    break aaa;
                }
            }
            System.out.println("~~~~~~~~~~~~~~~");

            bbb:
            for(i =1; true;i++){
                for(int j =1;j < 5;j++){
                    if(i+j>30)
                        break bbb;
                    System.out.println(i + "+" + j +" = " + (i+j));
                }
            }
        }

    }
}
