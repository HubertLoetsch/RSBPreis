import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class InportCSV extends JFrame {

    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                InportCSV form = new InportCSV();
                form.setVisible(true);
            }
        });
    }

    public InportCSV() {




        super("Inport CSVDatei");
        setSize(668, 345);
        setLocation(500, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Label Result
        final JLabel lblResult = new JLabel("Result", JLabel.CENTER);
        lblResult.setBounds(150, 22, 370, 14);
        getContentPane().add(lblResult);

        JButton Back;
        Back = new JButton("Zurück");
        Back.setBounds(350,228, 89, 23);
        add(Back);

        ButtonHandler handler = new ButtonHandler();
        Back.addActionListener(handler);



        // Table
        table = new JTable();
        getContentPane().add(table);

        // Table Model
        final DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.addColumn("Land");
        model.addColumn("Plz");
        model.addColumn("Zone");
        model.addColumn("Ldm");
        model.addColumn("Braun");
        model.addColumn("Tisa");
        model.addColumn("Scheffknecht");

        // ScrollPane
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(45, 98, 580, 90);
        getContentPane().add(scroll);


        // Create Button Open JFileChooser
        JButton btnButton = new JButton("Open File Chooser");
        btnButton.setBounds(268, 47, 135, 23);
        btnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileopen = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter(
                        "Text/CSV file", "txt", "csv");
                fileopen.addChoosableFileFilter(filter);

                int ret = fileopen.showDialog(null, "Choose file");

                if (ret == JFileChooser.APPROVE_OPTION) {

                    // Read Text file
                    File file = fileopen.getSelectedFile();

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(
                                file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null) {
                            String[] arr = line.split(";");

                            model.addRow(new Object[0]);
                            for(int x = 0;x < arr.length;x++){
                                model.setValueAt( arr[x],row,x);

                            }
                            /*
                            model.addRow(new Object[0]);
                            model.setValueAt(arr[0], row, 0);
                            model.setValueAt(arr[1], row, 1);
                            model.setValueAt(arr[2], row, 2);
                            model.setValueAt(arr[3], row, 3);
                            model.setValueAt(arr[4], row, 4);
                            model.setValueAt(arr[5], row, 5);
                          //  model.setValueAt(arr[6], row, 6);
                          //  model.setValueAt(arr[7], row, 7);

                             */

                            row++;
                        }
                        br.close();
                    } catch (IOException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }

                    lblResult.setText(fileopen.getSelectedFile().toString());
                }

            }
        });
        getContentPane().add(btnButton);

        // Button Save
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SaveData(); // save Data
            }


        });
        btnSave.setBounds(200, 228, 89, 23);
        getContentPane().add(btnSave);





    }
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            /*String msg;
            msg = "Login Successful";
            JOptionPane.showMessageDialog(null, msg);*/

            SortingProgramm in = new SortingProgramm();
            in.setSize(800, 500);
            in.setVisible(true);
            dispose();
        }
    }




    private void SaveData()
    {
        Connection connect = null;
        Statement s = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connect = DriverManager.getConnection(""
                    + "jdbc:mysql://192.168.56.101/TestData"
                    + "?user=app&password=123abcABC!\"§");

            s = connect.createStatement();

            for(int i = 0; i<table.getRowCount();i++)
            {
                String Land = table.getValueAt(i, 0).toString();
                String PLZ = table.getValueAt(i,1).toString();
                String Zone = table.getValueAt(i, 2).toString();
                String Ldm = table.getValueAt(i, 3).toString();
                String Braun = table.getValueAt(i, 4).toString();
                String Tisa = table.getValueAt(i, 5).toString();
                String Scheffknecht= table.getValueAt(i, 6).toString();


                // SQL Insert

                String sql = "INSERT INTO TestRSBData "
                        + "(Land,Plz,Zone,Ldm,Braun,Tisa,Scheffknecht) "
                        + "VALUES ('" + Land + "','"
                        + PLZ + "','"
                        + Zone + "'" + ",'"
                        + Ldm + "','"
                        + Braun + "','"
                        + Tisa + "','"
                        +Scheffknecht
                        +"') ";
                s.execute(sql);
            }

            JOptionPane.showMessageDialog(null,
                    "Import Data Successfully");


        } catch (Exception ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }

        try {
            if (s != null) {
                s.close();
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}