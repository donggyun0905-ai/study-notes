package ch03;

public class Ex2_1 {
    public static void main(String[] args) {
        char c = '가';
        while(true){
            for(int i =0;i<20;i++){
                System.out.print((c++)+"\t");
                if(c=='힣'+1){
                    return;
                }
            }
           System.out.println();
        }
    }
}
