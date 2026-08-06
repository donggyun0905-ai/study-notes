package net;

import java.net.InetAddress;

public class InetAddressEx1 {
    public static void main(String[] args) {
        try{
            //IP 및 도메인 객체화
            InetAddress add = InetAddress.getLocalHost();
            System.out.println("Host Name: "+ add.getHostName());
            System.out.println("Host Address: "+ add.getHostAddress());
            //도메인 -> ip
            add = InetAddress.getByName("auction.co.kr");
            System.out.println("Auction IP: " + add.getHostAddress());
            add = InetAddress.getByName("www.sajiktennis.kr");
            System.out.println("사직테니스: " + add/*toString*/);
            InetAddress adds[] = InetAddress.getAllByName("naver.com");
            for (int i = 0; i < adds.length; i++) {
                System.out.println("Naver IP: " + adds[i]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
