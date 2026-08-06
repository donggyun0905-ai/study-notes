package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;

public class URLEx1 {
    public static void main(String[] args) {
        String str = "https://search.naver.com:80/"
                + "search.naver?where=nexearch"
                + "&sm=top_hty&fbm=0&ie=utf8"
                + "&query=java#top";
        try{
            URI uri = new URI(str);
            URL url = uri.toURL();
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("FileName: " + url.getFile());
            System.out.println("Ref: " + url.getRef());
            url = new URL("https://reserve.busan.go.kr");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
            String line = "";
            while(true){
                line = br.readLine();
                if(line == null)break;
                System.out.println(line);
            }
            br.close();
            System.out.println("End~~~~~~~");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
