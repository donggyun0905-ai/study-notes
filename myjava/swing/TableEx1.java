package swing;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TableEx1 extends JFrame {
    public TableEx1() {
        // 샘플 데이터로 테이블 생성
        DefaultTableModel model = new DefaultTableModel(new Object[][] {
                { "A", "B", "C" },
                { "D", "E", "F" },
                { "G", "H", "I" }
        }, new Object[] { "Column 1", "Column 2", "Column 3" });
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        // 첫 번째 행의 셀 병합
        setCellMerge(table, 0, 0, 1, 2);
    }

    public static void main(String[] args) {
        TableEx1 frame = new TableEx1();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // JTable에서 셀을 병합하는 메소드
    public static void setCellMerge(JTable table, int row, int column, int rowspan, int colspan) {
        // 행 높이를 첫 번째 행의 높이에 rowspan을 곱한 값으로 설정
        table.setRowHeight(row, table.getRowHeight(row) * rowspan);

        // 병합된 셀 범위 내 각 셀의 셀 편집기 제거
        for (int i = 0; i < rowspan; i++) {
            for (int j = 0; j < colspan; j++) {
                if (i == 0 && j == 0) {
                    continue; // 첫 번째 셀은 이미 있으므로 건너뜀
                }
                int cellRow = row + i;
                int cellColumn = column + j;
                TableColumn columnModel = table.getColumnModel().getColumn(cellColumn);
                table.setValueAt("", cellRow, cellColumn); // 값을 빈 문자열로 설정
                //table.removeEditor(table.getCellEditor(cellRow, cellColumn)); // 셀 편집기 제거
                table.removeEditor(); // 셀 편집기 제거
                columnModel.setMinWidth(0);
                columnModel.setMaxWidth(0);
                columnModel.setWidth(0);
                columnModel.setPreferredWidth(0);
            }
        }
    }
}
