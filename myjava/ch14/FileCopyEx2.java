package ch14;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;

public class FileCopyEx2 extends MFrame 
implements ActionListener{
	
	Button open, save;
	TextArea ta;
	FileDialog openDialog, saveDialog;
	String sourceDir;
	String sourceFile;
	
	public FileCopyEx2() {
		super(400,500);
		setTitle("FileCopyEx2");
		add(ta = new TextArea());
		Panel p = new Panel();
		p.add(open = new Button("OPEN"));
		p.add(save = new Button("SAVE"));
		ta.setEditable(false);
		open.addActionListener(this);
		save.addActionListener(this);
		add(p,BorderLayout.SOUTH);
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==open) {
			if(openDialog == null){
				openDialog = new FileDialog(this,"파일열기",FileDialog.LOAD);
			}
			openDialog.setVisible(true);
			//선택한 파일의 디렉토리와 파일면 리턴
			String dir,file;
			dir = openDialog.getDirectory();
			file=openDialog.getFile();
			readFile(dir+file);
		}else if(obj==save) {
			if(saveDialog == null){
				saveDialog = new FileDialog(this,"파일저장",FileDialog.SAVE);
			}
			saveDialog.setVisible(true);
			//선택한 파일의 디렉토리와 파일면 리턴
			String dir,file;
			dir = saveDialog.getDirectory();
			file= saveDialog.getFile();
			writeFile(dir+file);
			countDown();
		}
	}

	//선택한 파일의 내용을 ta에 append
	public void readFile(String file) {
		try{
		    FileReader fr = new FileReader(file);
			int a = 0;
			String s = "";
			while((a=fr.read())!=-1){
				s+=(char)a;
			}
			ta.append(s);
			fr.close();
		} catch (Exception e){
		    e.printStackTrace();
		}
	}
	
	//ta에 오픈된 텍스트를 지정한 파일로 저장
	public void writeFile(String file) {
		try{
		    FileWriter fw = new FileWriter(file);
			fw.write(ta.getText());
			fw.close();
			ta.setText("");
		} catch (Exception e){
		    e.printStackTrace();
		}
	}
	
	//카운트 다운 기능
	public void countDown() {
		try {
			for (int i = 5; i > 0; i--) {
				ta.setText("저장 하였습니다 - " + i +"초 후에 사라집니다");
				Thread.sleep(1000);
			}
			ta.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new FileCopyEx2();
	}
}










