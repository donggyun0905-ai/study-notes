package ch12;

public class Synchronized2 {

    private int total = 0;
    private final int GOAL = 1_000_000;

    public synchronized void donate(String name, int amount){
        if(total >= GOAL) return;

        total+=amount;
        System.out.printf("%s님이 %,d원 기부 -> 누적 모금액: %,d원%n",name,amount,total);//%,d 천단위 표시
        if(total>=GOAL)
            System.out.println("*** 목표 금액 1000만원 달성! 모굼을 종료합니다. *** ");
    }

    public int getTotal(){return total;}

    //기부자 역할을 하는 내부 클래스
    class Donor implements Runnable{

        private String name; //기부자 이름
        private int amount; //1회 기부 금액

        public Donor(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) {
                    donate(name,amount);
                    Thread.sleep(100); // 0.1초 대기
                }
            }catch(Exception e){}
        }
    }

    public static void main(String[] args) throws Exception {
        Synchronized2 fund =new Synchronized2();
        Thread t1 = new Thread(fund.new Donor("민지",10_000));
        Thread t2 = new Thread(fund.new Donor("하니",10_000));
        Thread t3 = new Thread(fund.new Donor("해린",10_000));
        Thread t4 = new Thread(fund.new Donor("혜인",10_000));
        Thread t5 = new Thread(fund.new Donor("니엘",10_000));

        t1.start(); t2.start(); t3.start(); t4.start(); t5.start();
        t1.join(); t2.join(); t3.join(); t4.join(); t5.join();

        System.out.println("===============================");
        System.out.printf("최종 모금 총액: %,d원",fund.getTotal());
    }
}
