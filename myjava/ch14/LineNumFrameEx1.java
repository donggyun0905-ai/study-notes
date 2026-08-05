package ch14;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class LineNumFrameEx1 extends MFrame 
implements ActionListener{
	
	Button open, save;
	TextArea ta;
	FileDialog openDialog, saveDialog;
	String sourceDir;
	String sourceFile;
	String dir, file;
	
	public LineNumFrameEx1() {
		super(400,500);
		setTitle("Line Number Add");
		add(ta = new TextArea());
		Panel p = new Panel();
		p.add(open = new Button("OPEN"));
		p.add(save = new Button("SAVE"));
		openDialog = new FileDialog(this,"파일열기",FileDialog.LOAD);
		saveDialog = new FileDialog(this,"파일저장",FileDialog.SAVE);
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
			openDialog.setVisible(true);
			dir = openDialog.getDirectory();
			file = openDialog.getFile();
			if(file != null)
				readFile(dir+file,ta);
		}else if(obj==save) {
			saveDialog.setVisible(true);
			dir = saveDialog.getDirectory();
			file = saveDialog.getFile();
			if(file != null){
				writeFile(dir+file,ta);
				new DialogBox(this,"저장하였습니다.","알림");
			}
		}
	}
	
	public void readFile(String file, TextArea ta) {
		BufferedReader br = null;
		try{
		    br = new BufferedReader(new FileReader(file));
			String line;
			int num = 1;
			ta.setText("");
			while((line= br.readLine()) != null){
				if(line == null) break;
				ta.append(num + " : " + line + "\n");
				num++;
			}
			br.close();
		} catch (Exception e){
		    e.printStackTrace();
		}
	}
	
	public void writeFile(String file, TextArea ta) {
		FileWriter fw = null;
		try{
		    fw = new FileWriter(file);
			fw.write(ta.getText());
			ta.setText("");
			fw.close();
		} catch (Exception e){
		    e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new LineNumFrameEx1();
	}
}






