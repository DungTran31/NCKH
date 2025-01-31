package Model;

import Controller.TableController;
import DataTable.TableActionCellEditor;
import DataTable.TableActionCellRender;
import DataTable.TableProfileRender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentTableModel {
    private JTable table;
    private String imagePath;
    private String userName;
    private String id;
    private String className;
    private TableController controller;
    private List<Student> students;
    private String imgPath;
    private Object[][] data;

    public Object[][] getData() {
        return data;
    }

    public StudentTableModel(List<Student> students, TableController controller){
        this.students = students;
        this.controller = controller;
        init();
    }

    private void init(){
        table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 1 && column != 2 && column != 3;
            }
        };
        table.setRowSelectionAllowed(false);
        table.setAutoscrolls(false);
        table.setFocusable(false);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRequestFocusEnabled(false);
        table.setShowGrid(false);
        table.setRowSelectionAllowed(false);

        table.setRowHeight(50);
        table.setBorder(null);

        data = new Object[students.size()][5];
        for (int i = 0; i < students.size(); i++) {
            imgPath = students.get(i).getImagePath();
            if(imgPath == null || imgPath.isEmpty()){
                imgPath = "defaultUser.png";
            }
            data[i][0] = new ImageIcon(getClass().getResource("/Icon/Profile/" + imgPath));
            data[i][1] = students.get(i).getUserName();
            data[i][2] = students.get(i).getId();
            data[i][3] = students.get(i).getClassName();
            data[i][4] = null;
        }

        table.setModel(new DefaultTableModel(
                data,
                new String[] {
                        "Profile image", "Name", "ID", "Class", "Action"
                }
        ));
        table.getColumnModel().getColumn(0).setCellRenderer(new TableProfileRender());

        table.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(controller.getEvent()));
    }

    public JTable getTable() {
        return table;
    }
    public List<Student> getStudents(){
        List<Student> students = new ArrayList<>();
        for(Object[] row : data){
            ImageIcon icon = (ImageIcon) row[0];
            String userName = (String) row[1];
            int id = (int) row[2];
            String className = (String) row[3];

            String path = icon.getDescription();
            students.add(new Student(id, userName, className, path, null));
        }
        return students;
    }
}
