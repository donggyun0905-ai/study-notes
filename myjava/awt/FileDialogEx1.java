package awt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileDialogEx1 extends MFrame{

    FileDialog openFile,saveFile;
    Button openBtn, saveBtn;

    public FileDialogEx1() {
        openFile = new FileDialog(this/*현재의 Frame 객체*/,"파일 열기",FileDialog.LOAD);
        saveFile = new FileDialog(this,"파일 저장",FileDialog.SAVE);
        Panel p = new Panel();
        p.add(openBtn = new Button("OPEN"));
        p.add(saveBtn = new Button("SAVE"));
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile.setVisible(true);
                String dir = openFile.getDirectory();
                String file = openFile.getFile();
                System.out.printf("%s%s\n",dir,file);
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile.setVisible(true);
                String dir = saveFile.getDirectory();
                String file = saveFile.getFile();
                System.out.printf("%s%s\n",dir,file);
            }
        });
        add(p);

        validate();
    }

    public static void main(String[] args) {
        new FileDialogEx1();
    }
}
