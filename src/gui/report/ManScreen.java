package gui.report;

import gui.StartWindow;
import gui.UIElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by Adam Lee on 01/04/2015.
 */
public class ManScreen extends JFrame{

    private JFrame re;
    private JPanel top, button, north, center, south;
    private JLabel logo;
    private JButton sales, reports, logout;

    public ManScreen() {

        re = new JFrame();
        re.setTitle("Reports");
        re.setLayout(new BorderLayout());
        re.setSize(1000, 650);
        re.setLocationRelativeTo(null); //Sets the screen to appear in the center
        re.setResizable(true);

        //TOP PANEL FOR LOGO
        top = new JPanel();
        top.setBackground(Color.BLACK);
        top.setPreferredSize(new Dimension(100,105));

        //JLabel for Logo
        logo = new JLabel(new ImageIcon(UIElements.banner));
        top.add(logo);

        //BUTTON PANEL
        button = new JPanel(new BorderLayout());
        button.setBackground(new Color(98, 169, 221));

        //North Button Panel
        north = new JPanel(new FlowLayout());
        north.setBackground(new Color(98, 169, 221));
        button.add(north, BorderLayout.NORTH);

        //SALES Button
        sales = new JButton("SALES", new ImageIcon(UIElements.cashreg32));
        sales.setPreferredSize(new Dimension(130, 100));
        sales.setFocusPainted(false); //Remove Outline
        north.add(sales);
        sales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                try {
                    new ManSales();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //Center Panel
        center = new JPanel(new FlowLayout());
        center.setBackground(new Color(98, 169, 221));
        button.add(center, BorderLayout.CENTER);

        //REPORTS Button
        reports = new JButton("REPORTS", new ImageIcon(UIElements.report32));
        reports.setPreferredSize(new Dimension(130,100));
        center.add(reports);
        reports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportEmployee();
            }
        });

        //South Panel
        south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.setBackground(new Color(98, 169, 221));
        button.add(south, BorderLayout.SOUTH);

        //LOGOUT Button
        logout = new JButton("LOGOUT", new ImageIcon(UIElements.logout32));
        logout.setPreferredSize(new Dimension(150,100));
        south.add(logout);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Do you wish to logout?", "Logout", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                {
                    re.dispose();
                    new StartWindow();
                }
            }
        });

        re.add(top, BorderLayout.NORTH);
        re.add(button, BorderLayout.CENTER);
        re.setVisible(true);

    }
}
