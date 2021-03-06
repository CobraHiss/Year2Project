package gui.employee;

/*
IT Tallaght - 2015, S2
Computing - Year 2, Project
Group 17 (George - 22/03/2015)
David Lawlor
*/

import gui.FormValidator;
import gui.UIElements;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

public class SalesHistory extends JDialog implements MouseListener, ActionListener  {

    private JPanel pnlNorth, pnlCenter, pnlSouth;
    private JButton btnSearch, btnBack;
    private JTextField tfSearch;
    private String textFieldTip = "type in the order number...";
    private JTable saleHistory;
    private SaleHistoryTableModel tableModel;



    public SalesHistory(int id){

        this.setTitle("Sales History");
        this.setLayout(new BorderLayout()); // tip: border(don't indicate position), grid or gridbag layouts will stretch a component to the whole screen
        this.setSize(650,650);
        this.setResizable(false);
        this.getContentPane().setBackground(UIElements.getColour());
        this.setLocationRelativeTo(null);

// NORTH

        pnlNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10)); // center and add padding
        pnlNorth.setBorder(BorderFactory.createEtchedBorder());
        pnlNorth.setBackground(UIElements.getColour());

        tfSearch = new JTextField(30);
        tfSearch.setText(textFieldTip); // set initial text field search
        tfSearch.setForeground(Color.GRAY); // set initial colour to gray
        tfSearch.addMouseListener(this);
        pnlNorth.add(tfSearch);

        btnSearch = new JButton("Search", new ImageIcon(UIElements.search16)); // initialize the search button, add a add and icon
        btnSearch.setPreferredSize(new Dimension(100, 28));
        btnSearch.addActionListener(this);
        pnlNorth.add(btnSearch);

        this.add(pnlNorth, BorderLayout.NORTH);

// CENTER

        tableModel = new SaleHistoryTableModel();
        saleHistory = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(saleHistory);
        scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        pnlCenter = new JPanel(new GridLayout());
        pnlCenter.add(scrollPane);

        if (id != -1)
            tableModel.getSaleList(id);

        this.add(pnlCenter, BorderLayout.CENTER);

// SOUTH

        pnlSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlSouth.setBorder(BorderFactory.createEtchedBorder());
        pnlSouth.setBackground(UIElements.getColour());

        btnBack = new JButton("Back", new ImageIcon(UIElements.cancel6)); // initialize the search button, add a add and icon);
        btnBack.setPreferredSize(new Dimension(100, 28));
        btnBack.addActionListener(this);
        pnlSouth.add(btnBack);

        this.add(pnlSouth, BorderLayout.SOUTH);

// turns the lights on
        this.setVisible(true);
    }

// BUTTON ACTIONS

    // have to implement these methods for MouseListener
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}

    public void mouseClicked(MouseEvent e){
        if(e.getSource().equals(tfSearch)){
            if (tfSearch.getText().equals(textFieldTip)) {
                tfSearch.setText("");
                tfSearch.setForeground(null); // reset colour to black
            }
            tfSearch.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {}
                @Override
                public void focusLost(FocusEvent e) { // set the textFieldTip to be visible in text field on focus loss
                    if (tfSearch.getText().equals("")){
                        tfSearch.setText(textFieldTip);
                        tfSearch.setForeground(Color.GRAY);
                    }
                }
            });
        }
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(btnBack)){
            this.dispose();
        }
        else if (e.getSource().equals(btnSearch)){
            if (FormValidator.isNumber(tfSearch.getText())){
                tableModel.getSaleList(Integer.parseInt(tfSearch.getText()));
            } else if (tfSearch.getText().equals(textFieldTip)){
                JOptionPane.showMessageDialog(this,"Please Enter The Order Number","No Order Number",JOptionPane.WARNING_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,"Please Enter The Correct Order Number","Invalid Number",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
