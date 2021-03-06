package gui.employee;

import database.operations.DepartmentOperations;
import database.operations.EmployeeOperations;
import gui.*;
import model.Employee;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/*
IT Tallaght - 2015, S2
Computing - Year 2, Project
Group 17 (George - 08/03/2015)
*/

public class EmployeeAddEdit implements ActionListener {

    private JDialog employeeAdd;
    private JPanel picturePanel, pictureButtonsPanel, detailsPanel, buttonsPanel;
    private JLabel profilePictureLabel, empFNameLabel, empLNameLabel, empStreetLabel, empCityLabel, empCountyLabel, empDOB, empEmailLabel, empUsernameLabel, empPasswordLabel, empPositionLabel, empSalaryLabel, empDeptLabel;
    private JTextField empFNameField, empLNameField, empStreetField, empCityField, empCountyField, empEmailField, empUsernameField, empPasswordField, empPositionField, empSalaryField, empDeptField;
    private JComboBox<String> birthDayCBox, birthMonthCBox, birthYearCBox;
    private JButton addButton, removeButton, cancelButton, okButton, passGenButton, usernameGenButton;
    private File fImg;
    private JFileChooser fc;
    private DateGenerator dg;
    private int dayBefore;

    private EmployeeOperations eo;
    private int open = 1;
    private int choice;
    EmployeeMain empMain;
    Employee emp;



    public EmployeeAddEdit(JFrame parent, int choice, EmployeeMain employeeMain, Employee employee){

    // setup the jdialog

        employeeAdd = new JDialog(parent, true);
        employeeAdd.setTitle("Add New Employee");
        employeeAdd.setLayout(new BorderLayout());
        employeeAdd.setSize(760, 550);
        employeeAdd.setResizable(true);
        employeeAdd.setLocationRelativeTo(null);

        this.emp = employee;
        this.empMain = employeeMain;
        this.choice = choice;

    // picture panel + picture buttons  panel inside it

        picturePanel = new JPanel(new BorderLayout());
        picturePanel.setBackground(UIElements.getColour());
        picturePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Profile Picture",2,2)); // set anonymous titled, etched border, centered title

        // profile picture
        profilePictureLabel = new JLabel(new ImageIcon(UIElements.person128));
        picturePanel.add(profilePictureLabel, BorderLayout.CENTER);

        // buttons panel
        pictureButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));  // alignment, hgap, vgap
        pictureButtonsPanel.setBackground(UIElements.getColour());

        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 26));
        addButton.setIcon(new ImageIcon(UIElements.plus16));
        addButton.addActionListener(this);
        pictureButtonsPanel.add(addButton);

        removeButton = new JButton("Remove");
        removeButton.setPreferredSize(new Dimension(100, 26));
        removeButton.setIcon(new ImageIcon(UIElements.minus16));
        removeButton.addActionListener(this);
        pictureButtonsPanel.add(removeButton);

        picturePanel.add(pictureButtonsPanel, BorderLayout.SOUTH);

        // add picture panel to the main JDialog
        employeeAdd.add(picturePanel, BorderLayout.WEST);

    // detailsPanel - GridBagLayout

        detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(UIElements.getColour());

        // Name
        empFNameLabel = new JLabel("Employee Name");
        detailsPanel.add(empFNameLabel, Griddy.getConstraints(0,0,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empFNameField = new JTextField();
        detailsPanel.add(empFNameField, Griddy.getConstraints(1,0,1,1,0,0,0,0,5,15,15,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER));

        // Surname
        empLNameLabel = new JLabel("Employee Surname");
        detailsPanel.add(empLNameLabel, Griddy.getConstraints(0,1,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empLNameField = new JTextField();
        detailsPanel.add(empLNameField, Griddy.getConstraints(1,1,1,1,0,0,0,0,5,15,15,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER));

        // Email
        empEmailLabel = new JLabel("Employee Email");
        detailsPanel.add(empEmailLabel, Griddy.getConstraints(0,2,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empEmailField = new JTextField();
        detailsPanel.add(empEmailField, Griddy.getConstraints(1,2,1,1,0,0,0,0,5,15,15,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER));

        // DOB
        dg = new DateGenerator();  // this needs DateGen class, to get correct days, months and years

        empDOB = new JLabel("Date Of Birth");
        detailsPanel.add(empDOB, Griddy.getConstraints(0,3,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));

        birthYearCBox = new JComboBox<>(new DefaultComboBoxModel<>(dg.getPastCentury()));
        // can either directly pass the parameters as displayed above, or do separately as displayed below
        // birthYearCBox = new JComboBox<String>()
        // birthYearCBox.setModel(new DefaultComboBoxModel<String>(dg.getPastCentury()));
        birthYearCBox.addActionListener(this);

        birthMonthCBox = new JComboBox<>(new DefaultComboBoxModel<>(dg.getMonths()));
        birthMonthCBox.setEnabled(false);
        birthMonthCBox.addActionListener(this);

        birthDayCBox = new JComboBox<>(dg.getMonthDays(birthMonthCBox.getSelectedIndex() + 1, Integer.parseInt(birthYearCBox.getSelectedItem().toString())));
        birthDayCBox.setEnabled(false);

            // add day, month, year comboboxes to details panel
        detailsPanel.add(birthYearCBox, Griddy.getConstraints(1,3,1,1,0,0,0,0,5,115,125,5,0,GridBagConstraints.WEST));
        detailsPanel.add(birthMonthCBox, Griddy.getConstraints(1,3,1,1,0,0,0,0,5,60,190,5,0,GridBagConstraints.WEST));
        detailsPanel.add(birthDayCBox, Griddy.getConstraints(1,3,1,1,0,0,0,0,5,15,245,5,0,GridBagConstraints.WEST));

        // Street
        empStreetLabel = new JLabel("Employee Street");
        detailsPanel.add(empStreetLabel, Griddy.getConstraints(0,4,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empStreetField = new JTextField();
        detailsPanel.add(empStreetField, Griddy.getConstraints(1,4,1,1,0,0,0,0,5,15,15,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER));

        // City
        empCityLabel = new JLabel("Employee City");
        detailsPanel.add(empCityLabel, Griddy.getConstraints(0,5,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empCityField = new JTextField();
        detailsPanel.add(empCityField, Griddy.getConstraints(1,5,1,1,0,0,0,0,5,15,15,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER));

        // County
        empCountyLabel = new JLabel("Employee County");
        detailsPanel.add(empCountyLabel, Griddy.getConstraints(0,6,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empCountyField = new JTextField();
        detailsPanel.add(empCountyField, Griddy.getConstraints(1,6,1,1,0,0,0,0,5,15,15,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER));

        // Username
        empUsernameLabel = new JLabel("Employee Username");
        detailsPanel.add(empUsernameLabel, Griddy.getConstraints(0,7,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empUsernameField = new JTextField();
        empUsernameField.setEditable(false);
        empUsernameField.setBackground(Color.LIGHT_GRAY);
        detailsPanel.add(empUsernameField, Griddy.getConstraints(1,7,1,1,0,0,0,0,5,15,110,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST));

        // Username Generator
        usernameGenButton = new JButton("Generate");
        usernameGenButton.addActionListener(this);
        detailsPanel.add(usernameGenButton, Griddy.getConstraints(1,7,1,1,0,0,0,0,5,15,15,5,0,GridBagConstraints.EAST));

        // Password
        empPasswordLabel = new JLabel("Employee Password");
        detailsPanel.add(empPasswordLabel, Griddy.getConstraints(0,8,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empPasswordField = new JTextField(5);
        detailsPanel.add(empPasswordField, Griddy.getConstraints(1,8,1,1,0,0,0,0,5,15,110,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.WEST)); // password field and button are in the "same" cell, the field get set to stretch horizontally, but padded in from the right to allow the password button to fit

        // Password Generator
        passGenButton = new JButton("Generate");
        passGenButton.addActionListener(this);
        detailsPanel.add(passGenButton, Griddy.getConstraints(1,8,1,1,0,0,0,0,5,15,15,5,0,GridBagConstraints.EAST));

        // Position
        empPositionLabel = new JLabel("Employee Position");
        detailsPanel.add(empPositionLabel, Griddy.getConstraints(0,9,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empPositionField = new JTextField();
        detailsPanel.add(empPositionField, Griddy.getConstraints(1,9,1,1,0,0,0,0,5,15,15,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER));

        // Salary
        empSalaryLabel = new JLabel("Employee Salary");
        detailsPanel.add(empSalaryLabel, Griddy.getConstraints(0,10,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empSalaryField = new JTextField();
        detailsPanel.add(empSalaryField, Griddy.getConstraints(1,10,1,1,0,0,0,0,5,15,15,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER));

        // Department
        empDeptLabel = new JLabel("Employee Department");
        detailsPanel.add(empDeptLabel, Griddy.getConstraints(0,11,1,1,0,0,0,0,5,15,5,5,0,GridBagConstraints.WEST));
        empDeptField = new JTextField();
        detailsPanel.add(empDeptField, Griddy.getConstraints(1,11,1,1,0,0,0,0,5,15,15,5,GridBagConstraints.HORIZONTAL,GridBagConstraints.CENTER));

        employeeAdd.add(detailsPanel, BorderLayout.CENTER);

    // bottom, buttons panel - FlowLayout, added to main's South border

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));  // layout, horizontal padding, vertical padding
        buttonsPanel.setBackground(UIElements.getColour());

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 26));
        cancelButton.setIcon(new ImageIcon(UIElements.cancel6));
        cancelButton.addActionListener(this);
        buttonsPanel.add(cancelButton);

        okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(100, 26));
        okButton.setIcon(new ImageIcon(UIElements.save16));
        okButton.addActionListener(this);
        buttonsPanel.add(okButton);

        employeeAdd.add(buttonsPanel, BorderLayout.SOUTH);

        eo = new EmployeeOperations();
        // choice - add(clean fields) or edit(populate fields (1))
        if (choice == 1){
            try{
                profilePictureLabel.setIcon(new ImageIcon(DataProcessor.fitImageByte(employee.getEmpPic(), 200, 200)));
                fImg = DataProcessor.byteToFile(employee.getEmpPic());
            }catch (IOException ioE){
                System.out.println("Problem with picture");
            }
            empFNameField.setText(employee.getEmpFName());
            empLNameField.setText(employee.getEmpLName());
            empEmailField.setText(employee.getEmpEmail());
            birthYearCBox.setSelectedItem(employee.getEmpDOBy());
            birthMonthCBox.setSelectedItem(employee.getEmpDOBm());
            birthDayCBox.setSelectedItem(employee.getEmpDOBd());
            empStreetField.setText(employee.getEmpStreet());
            empCityField.setText(employee.getEmpCity());
            empCountyField.setText(employee.getEmpCounty());
            empUsernameField.setText(employee.getEmpUsername());
            empPasswordField.setText("********");
            empPositionField.setText(employee.getPosition());
            empSalaryField.setText(Double.toString(employee.getSalary()));
            empDeptField.setText(new DepartmentOperations().getDepartmentName(employee.getEmpDeptId()));
            open = 0;
        }

// turns the lights on

        employeeAdd.setVisible(true);
    }

// BUTTON ACTIONS

    public void actionPerformed(ActionEvent e){ // the two methods below set the correct day, based on year and month, both month and year comboboxes set correct days. "dayBefore" is to remember what day was set before and set it again, otherwise it resets to 1 ... ex, Person A, birthday 4th, this way when selecting month,year, it stays at 4th, without beforeDay, day combobox would reset to 1 because month and year combobox trigger it
        if (e.getSource().equals(birthYearCBox)){
            dayBefore = birthDayCBox.getSelectedIndex();
            birthDayCBox.setModel(new DefaultComboBoxModel<>(dg.getMonthDays(birthMonthCBox.getSelectedIndex() + 1, Integer.parseInt(birthYearCBox.getSelectedItem().toString()))));
            birthDayCBox.setSelectedIndex(dayBefore);
            birthMonthCBox.setEnabled(true);
        }
        else if (e.getSource().equals(birthMonthCBox)){
            dayBefore = birthDayCBox.getSelectedIndex();
            birthDayCBox.setModel(new DefaultComboBoxModel<>(dg.getMonthDays(birthMonthCBox.getSelectedIndex() + 1, Integer.parseInt(birthYearCBox.getSelectedItem().toString()))));
            birthDayCBox.setSelectedIndex(dayBefore);
            birthDayCBox.setEnabled(true);
        }
        else if (e.getSource().equals(usernameGenButton)){
            if (empFNameField.getText().equals("") || empLNameField.getText().equals(""))
                JOptionPane.showMessageDialog(null,"First Name And Last Name Must Be Entered","Username Error",JOptionPane.ERROR_MESSAGE);
            else
                empUsernameField.setText(empFNameField.getText()+empLNameField.getText()+eo.getNextID());
        }
        else if (e.getSource().equals(passGenButton))
            empPasswordField.setText(PasswordGenerator.generatePassword());
        else if (e.getSource().equals(addButton)){
            fc = new JFileChooser(); // initialize the JFileChooser - Initializing on button action because if initialized in the constructor, it slows down the UI response to the button
            fc.setFileFilter(DataProcessor.imageFilter); // set image filter on JFileChooser
            fc.setPreferredSize(new Dimension(600, 400)); // JFileChooser size
            fc.setMultiSelectionEnabled(false); // don't allow multifile selection
            fc.setDialogTitle("Select Employee Image"); // title
            fc.setAcceptAllFileFilterUsed(false); // turn off viewing of all files
            open = fc.showOpenDialog(employeeAdd); // could've done "this" if I was extending the JDialog, option dialog returns int
            if (open == JFileChooser.APPROVE_OPTION) {  // if JFileChooser is open (int 1)
                fImg = fc.getSelectedFile(); // select the file
                try{
                    profilePictureLabel.setIcon(new ImageIcon(DataProcessor.fitImageFile(fImg, 128, 128))); // scale the picture and set it
                } catch (IOException ip){
                    JOptionPane.showMessageDialog(null, "Image Problem");
                }
            }
        }
        else if (e.getSource().equals(removeButton))
            profilePictureLabel.setIcon(new ImageIcon(UIElements.person128));
        else if (e.getSource().equals(cancelButton))
            employeeAdd.dispose();
        else if (e.getSource().equals(okButton)){
            if(FormValidator.isEmptyField(empFNameField.getText())
                    || FormValidator.isEmptyField(empLNameField.getText())
                    || FormValidator.isEmptyField(empEmailField.getText())
                    || FormValidator.isEmptyField(empStreetField.getText())
                    || FormValidator.isEmptyField(empCityField.getText())
                    || FormValidator.isEmptyField(empCountyField.getText())
                    || FormValidator.isEmptyField(empUsernameField.getText())
                    || FormValidator.isEmptyField(empPasswordField.getText())
                    || FormValidator.isEmptyField(empPositionField.getText())
                    || FormValidator.isEmptyField(empSalaryField.getText())
                    || FormValidator.isEmptyField(empDeptField.getText()))
                JOptionPane.showMessageDialog(null,"Please Fill-In All Fields Of The Form","Empty Fields", JOptionPane.WARNING_MESSAGE);
            else if(FormValidator.isNumber(empFNameField.getText())
                    || FormValidator.isNumber(empLNameField.getText())
                    || FormValidator.isNumber(empStreetField.getText())
                    || FormValidator.isNumber(empCityField.getText())
                    || FormValidator.isNumber(empCountyField.getText())
                    || FormValidator.isNumber(empPositionField.getText()))
                JOptionPane.showMessageDialog(null,"Please Enter Valid Data For Each Field","Invalid Data",JOptionPane.WARNING_MESSAGE);
            else if (!FormValidator.isValidEmail(empEmailField.getText()))
                JOptionPane.showMessageDialog(null,"Please Enter A Valid Email Address","Invalid Email",JOptionPane.WARNING_MESSAGE);
            else if(!empPositionField.getText().equals("Sales") && !empPositionField.getText().equals("Manager") &&
                    !empPositionField.getText().equals("Admin") )
                JOptionPane.showMessageDialog(null,"Valid Positions are Sales /nManager","Invalid Position",JOptionPane.WARNING_MESSAGE);
            else if(!empDeptField.getText().equals("Administration") && !empDeptField.getText().equals("Sales") &&
                    !empDeptField.getText().equals("HR") && !empDeptField.getText().equals("Maintenance") &&
                    !empDeptField.getText().equals("Management")) {
                String s = "<html>Valid departments are: </br>" +
                        "<ol><li>Administration</li><li>Maintenance</li><li>Management</li><li>Sales</li><li>HR</li></ol></html>";
                JOptionPane.showMessageDialog(null, s, "Invalid Department", JOptionPane.WARNING_MESSAGE);
            }
            else if (open == 1)
                JOptionPane.showMessageDialog(null, "You have not selected a picture", "Empty Fields", JOptionPane.WARNING_MESSAGE);
            else if (!FormValidator.isDouble(empSalaryField.getText()))
                JOptionPane.showMessageDialog(null,"Salary must be a number","Invalid Salary",JOptionPane.WARNING_MESSAGE);
            else{
                int deptId = new DepartmentOperations().getDepartmentId(empDeptField.getText());
                if(choice == 0){ // to create a new employee
                    eo.addEmployee(deptId, empFNameField.getText(), empLNameField.getText(), empPositionField.getText(),
                            empStreetField.getText(), empCityField.getText(), empCountyField.getText(), birthDayCBox.getSelectedIndex() + 1,
                            birthMonthCBox.getItemAt(birthMonthCBox.getSelectedIndex()), birthYearCBox.getItemAt(birthYearCBox.getSelectedIndex()),
                            empEmailField.getText(), Double.parseDouble(empSalaryField.getText()), fImg, empUsernameField.getText(),
                            empPasswordField.getText());
                }
                else if (choice == 1){ // to update an existing employee
                    eo.updateEmployee(emp.getEmpId(),deptId, empFNameField.getText(), empLNameField.getText(), empPositionField.getText(),
                            empStreetField.getText(), empCityField.getText(), empCountyField.getText(), birthDayCBox.getSelectedIndex() + 1,
                            birthMonthCBox.getItemAt(birthMonthCBox.getSelectedIndex()), birthYearCBox.getItemAt(birthYearCBox.getSelectedIndex()),
                            empEmailField.getText(), Double.parseDouble(empSalaryField.getText()), fImg, empUsernameField.getText(),
                            empPasswordField.getText());
                }
                empMain.refreshList();
                employeeAdd.dispose();
            }
            }
        }
    }
