package gui;/*2ndYearProject
  gui
  Created by David
  14:29   30/03/2015
  Software Development 3
*/

import database.operations.ProductOperations;
import gui.terminal.TerminalMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



  public class StartWindow extends JFrame implements ActionListener {

    private ProductOperations po;
    public static TerminalMode mf;
    private JLabel logoLabel;
    private JPanel centerPanel;
    private JPanel southPanel;
    private JPanel main;
    private JButton terminal, login, help;
    private GridBagLayout bl;
    private boolean displayArea = false;


    public StartWindow() {
      po = new ProductOperations();

      this.setTitle("DGA Computers");
      this.setLayout(new BorderLayout());
      this.setSize(1000, 650);
      this.setResizable(true);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      this.getContentPane().setBackground(UIElements.getColour());


      help = new JButton("HELP", new ImageIcon("src/res/images/UI Elements/help64.png"));
      help.addActionListener(this);

      bl = new GridBagLayout();

      main = new JPanel(new BorderLayout());
      main.setMaximumSize(new Dimension(800, 600));

      logoLabel = new JLabel(new ImageIcon(UIElements.banner));

      JPanel northPanel = new JPanel(new GridLayout(1, 1));
      northPanel.setBackground(Color.BLACK);
      northPanel.add(logoLabel);

      main.add(northPanel, BorderLayout.NORTH);
      main.add(getCenterPanel(), BorderLayout.CENTER);
      main.add(getMinSouthPanel(), BorderLayout.SOUTH);

      this.add(main, BorderLayout.CENTER);
      this.setVisible(true);
    }


    public JPanel getCenterPanel() {
      centerPanel = new JPanel(bl);

      terminal = new JButton(new ImageIcon("src/res/images/UI Elements/product150.png"));
      terminal.addActionListener(this);

      login = new JButton(new ImageIcon("src/res/images/UI Elements/search150.png"));
      login.addActionListener(this);

      centerPanel.setBackground(UIElements.getColour());
      //centerPanel.add(browse, getConstraints(0,0,1,1, GridBagConstraints.WEST, 0,75,0,75));
      centerPanel.add(terminal, Griddy.getConstraints(0, 0, 1, 1, 10, 10, 0, 0, 0, 75, 75, 0, 0, GridBagConstraints.WEST));
      //centerPanel.add(search, getConstraints(1,0,1,1, GridBagConstraints.EAST, 0,75,0,75));
      centerPanel.add(login, Griddy.getConstraints(1, 0, 1, 1, 10, 10, 0, 0, 0, 75, 75, 0, 0, GridBagConstraints.EAST));

      displayArea = true;

      return centerPanel;
    }


    // South Panel with help button
    public JPanel getMinSouthPanel() {
      southPanel = new JPanel(bl);
      southPanel.setBackground(UIElements.getColour());
      //southPanel.add(help, getConstraints(0,0,1,1,GridBagConstraints.CENTER, 20,0,20,0));
      southPanel.add(help, Griddy.getConstraints(0, 0, 1, 1, 10, 10, 0, 0, 20, 0, 0, 20, 0, GridBagConstraints.CENTER));

      return southPanel;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource().equals(terminal)) {
        TerminalMode tm = new TerminalMode();
        tm.setVisible(true);
        TerminalMode.setMf(tm);
        this.setVisible(false);
      } else if (e.getSource().equals(login)) {
        AuthenticationPopUp apu = new AuthenticationPopUp(this);
      } else if (e.getSource().equals(help)) {
        //setToProductView();
      }
    }


  }
