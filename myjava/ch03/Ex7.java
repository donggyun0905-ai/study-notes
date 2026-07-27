package ch03;

public class Ex7 {
    public static void main(String[] args) {
        int arr[];
        arr = new int[3];

        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;
        System.out.println(arr[0] + arr[1] + arr[2]);
        int arr2[] = new int[5];

        int sum2 = 0;

        for(int i =0;i<arr2.length;i++){
            arr2[i] = (i+1)*10;
            sum2 += arr2[i];
        }
        System.out.println("sum2: " + sum2);

        String subject[] = {"java","JSP","Flutter","Spring"};
        for (int i =0;i<subject.length;i++){
            System.out.println(subject[i] + "\t");
        }
    }
}
