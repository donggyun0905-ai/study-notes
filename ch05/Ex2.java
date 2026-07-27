package ch05;

public class Ex2 {
    public static void main(String[] args) {
        int arr[][] = new int[2][3];
        for(int i =0;i<arr.length;i++){
            for(int j =0;j<arr[i].length;j++){
                arr[i][j] = i+j;
                System.out.println("arr[" + i+"]["+j+"]:"+arr[i][j]);
            }
        }
        System.out.println("------------------");
        int arr2[][] = {{1,2},{3,4,5},{6},{7,8},{9,10}};

        int sum = 0;
        for(int i=0;i<arr2.length;i++){
            for(int j=0;j<arr2[i].length;j++){
                sum += arr2[i][j];
            }
        }
        System.out.println("sum: "+ sum);

        //문제2
        double arr3[][] = {{1.0},{2.3,3.4},{4.5,6.2,4.3},{9.0}};
        double sum3 =0;
        int count = 0;
        for(int i =0;i<arr3.length;i++){
            for(int j=0;j<arr3[i].length;j++){
                sum3 += arr3[i][j];
                count++;
            }
        }
        System.out.println("avg : "+sum3 / count);

        //문제3
        int arr4[][][] = {{{1,2,3},{4,5},{6,7,8,9}},
                {{10,11},{12,13,14},{15},{22}}
                ,{{16,17,18,19,20},{22,23,24}}};
        int sum4 = 0;
        int cnt4 = 0;
        for(int i =0;i<arr4.length;i++){
            for(int j=0;j<arr4[i].length;j++){
                for(int k =0;k<arr4[i][j].length;k++) {
                    sum4 += arr4[i][j][k];
                    cnt4++;
                }
            }
        }
        System.out.println("sum : "+sum4);
        System.out.println("avg4: " + sum4/cnt4);

        //배열예외
        int arr5[] = {0,1,2};
        System.out.println(arr5[3]);

    }
}
