import javax.swing.*;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;


public class Test extends JFrame implements ActionListener {
    JLabel JL_Ldm;
    JLabel JL_plz;
    JLabel JL_braun;
    JLabel JL_tisa;
    JLabel JL_Scheffknecht;
    JLabel JL_Land;

    JTextField JT_Ldm;
    JTextField JT_plz;
    JTextField JT_braun;
    JTextField JT_tisa;
    static JTextField JT_Scheffknecht;
    JTextField JT_Land;


    JButton btn_search;

    public Test() {
        super("Search");
        //Fenster Selbst
        //Suchen Funktion
        btn_search = new JButton("Search");
        btn_search.setBounds(360, 90, 80, 20);
        btn_search.addActionListener(this);

        ImageIcon img = new ImageIcon("C:\\4Java\\Test\\RSBLOGO.PNG");
        setIconImage(img.getImage());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Programm");
        setSize(800, 500);

        //PLZ Button
        JL_plz = new JLabel("Enter PLZ:");
        JL_plz.setBounds(20, 50, 140, 20);
        JT_plz = new JTextField(20);
        JT_plz.setBounds(200, 50, 150, 20);
        add(JL_plz);
        add(JT_plz);

        //Lademeter Tabelle
        JL_Ldm = new JLabel("Enter Lademeter: ");
        JL_Ldm.setBounds(20, 80, 140, 20);
        JT_Ldm = new JTextField(20);
        JT_Ldm.setBounds(200, 80, 150, 20);
        add(JL_Ldm);
        add(JT_Ldm);

        //Land
        JL_Land = new JLabel("Enter Land: ");
        JL_Land.setBounds(20, 20, 200, 20);
        JT_Land = new JTextField(20);
        JT_Land.setBounds(200, 20, 150, 20);
        add(JL_Land);
        add(JT_Land);


        //Scheffknecht Tabelle
        JL_braun = new JLabel("Braun: ");
        JL_braun.setBounds(20, 110, 100, 20);
        JT_braun = new JTextField(20);
        JT_braun.setBounds(200, 110, 150, 20);
        add(JL_braun);
        add(JT_braun);

        //Braun Tabelle
        JL_tisa = new JLabel("Tisa: ");
        JL_tisa.setBounds(20, 140, 100, 20);
        JT_tisa = new JTextField(20);
        JT_tisa.setBounds(200, 140, 150, 20);
        add(JL_tisa);
        add(JT_tisa);

        //ScheffknechtExkl
        JL_Scheffknecht = new JLabel("Scheffknecht: ");
        JL_Scheffknecht.setBounds(20, 170, 180, 20);
        JT_Scheffknecht = new JTextField(20);
        JT_Scheffknecht.setBounds(200, 170, 150, 20);
        add(JL_Scheffknecht);
        add(JT_Scheffknecht);

        setLayout(null);
        add(btn_search);

        this.getContentPane().setBackground(Color.white);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Hier werden die Daten weiter geleitet.
    @Override
    public void actionPerformed(ActionEvent e) {
        ResultSet Rs = null;
        Function f = new Function();

        Rs = f.find(JT_plz.getText(), JT_Ldm.getText(),JT_Land.getText());
        try {
            if (!Rs.next()) {
                JOptionPane.showMessageDialog(null, "Keine Daten vorhanden!!!");
            } else {
                JT_braun.setText(Rs.getString("braun"));
                JT_tisa.setText(Rs.getString("tisa"));
                JT_Scheffknecht.setText(Rs.getString("Scheffknecht"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
    //Verbingung ZUm server und Sql Abfrage
    public static class Function {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        public ResultSet find(String plz, String ldm,String Land) {
            try {
                //Verbindung Zum server
                myConn = DriverManager.getConnection("jdbc:mysql://192.168.56.101/TEST1", "app", "123abcABC!\"§");
                //SQL abfrage
               // myStmt = myConn.prepareStatement("select ldm = ? and plz = ? from DE ");
                myStmt = myConn.prepareStatement("SELECT * FROM ENDLIST_CSV ec WHERE Land = ? and Plz=? and Ldm = ?");
                //myStmt = myConn.prepareStatement("SELECT * FROM ENDLIST_CSV ec WHERE Plz = ? and Ldm=? and Land = ?");
                myStmt.setString(1,Land);
                myStmt.setString(2, plz);
                myStmt.setString(3, ldm);

                myRs = myStmt.executeQuery();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            return myRs;
        }
    }

    public static void main(String[] args) {
        new Test();
    }

}