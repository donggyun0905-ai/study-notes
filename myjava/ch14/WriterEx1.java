package ch14;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class WriterEx1 {
    public static void main(String[] args) {
        char a = 'A';
        char b = 'b';
        char c = '가';
        try{
            OutputStream os = System.out;
            Writer writer = new OutputStreamWriter(os);
            writer.write(a);
            writer.write(b);
            writer.write(c);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
