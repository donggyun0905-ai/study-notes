package ch04;

public class Ex2 {
    public static void main(String[] args) {
        String month = "february";
        switch (month) {
            case "january":
                System.out.println("1월달");
                break;
            case "february":
                System.out.println("2월달");
                //break;
            case "march":
                System.out.println("3월달");
                break;
            default:
                System.out.println("4월달 이후.....");
        }
        System.out.println("*****************");
        if(month.equals("january")){
            System.out.println("1월달");
        }else if(month.equals("february")){
            System.out.println("2월달");
        }else if(month.equals("march")){
            System.out.println("3월달");
        }else{
            System.out.println("4월달 이후...");
        }

    }
}
