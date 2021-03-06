package gui.product;

import database.operations.ProductOperations;
import gui.Griddy;
import gui.UIElements;
import gui.admin.AdminMain;
import model.Product;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

/*
IT Tallaght - 2015, S2
Computing - Year 2, Project
Group 17 (George - 07/03/2015)
David
*/

public class ProductMain implements ActionListener, MouseListener {

    private JPanel prodMain;
    private JButton addButton, editButton, deleteButton, searchButton, viewButton;
    private JTextField searchField;
    private JComboBox prodManufacturers, prodType, modelTypes;
    private JPanel managePanel, northPanel, southPanel, searchPanel, searchTopPanel, searchBottomPanel;

    private String textFieldTip = "type your search query...";

    private AdminMain am;

    private JTable products;
    private ProductTableModel productTableModel;
    private ProductOperations po;
    private int selectedRow = -1;
    private int selectedRowId = 0;

    public JPanel getProductMain() {
// setup the frame
        prodMain = new JPanel(new BorderLayout());
// north panel
        northPanel = new JPanel(new GridBagLayout());
//northPanel.setBackground(new Color(98, 169, 221));
        // manage products panel

        managePanel = new JPanel(new FlowLayout());
        //managePanel.setBackground(new Color(98, 169, 221));
        managePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Manage Products")); // set anonymous titled, etched border

        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 28));
        addButton.setIcon(new ImageIcon(UIElements.plus16));
        addButton.addActionListener(this);
        managePanel.add(addButton);

        editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(100, 28));
        editButton.setIcon(new ImageIcon(UIElements.edit16));
        editButton.addActionListener(this);
        managePanel.add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100, 28));
        deleteButton.setIcon(new ImageIcon(UIElements.delete16));
        deleteButton.addActionListener(this);
        managePanel.add(deleteButton);

        northPanel.add(managePanel, Griddy.getConstraints(0, 0, 1, 1, 0, 0, 0, 0, 5, 0, 0, 5, 0, GridBagConstraints.CENTER));

        // search products panel

        searchPanel = new JPanel(new BorderLayout());
        //searchPanel.setBackground(new Color(98, 169, 221));
        searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search Products")); // set anonymous titled, etched border

        // top panel containing the search field and search button
        searchTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //searchTopPanel.setBackground(new Color(98, 169, 221));
        searchField = new JTextField(29);
        searchField.setText(textFieldTip); // set initial text field search
        searchField.setForeground(Color.GRAY); // set initial colour to gray
        searchField.addMouseListener(this);
        searchTopPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(105, 28));
        searchButton.setIcon(new ImageIcon(UIElements.search16));
        searchButton.addActionListener(this);
        searchTopPanel.add(searchButton);

        // add top panel to search panel
        searchPanel.add(searchTopPanel, BorderLayout.NORTH);

        // buttom panel containing comboboxes for information filtering
        searchBottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        po = new ProductOperations();
        prodManufacturers = new JComboBox(new DefaultComboBoxModel(po.getManufacturers()));
        prodManufacturers.addActionListener(this);
        prodType = new JComboBox(new DefaultComboBoxModel<>());
        prodType.setEnabled(false);
        prodType.addActionListener(this);
        modelTypes = new JComboBox(new DefaultComboBoxModel<>());
        modelTypes.setEnabled(false);


        // add bottom panel to search panel
        searchBottomPanel.add(prodManufacturers);
        searchBottomPanel.add(prodType);
        searchBottomPanel.add(modelTypes);

        searchPanel.add(searchBottomPanel, BorderLayout.SOUTH);

        // add all the above to northPanel
        northPanel.add(searchPanel, Griddy.getConstraints(1, 0, 1, 2, 0, 0, 0, 0, 5, 0, 0, 5, 0, GridBagConstraints.CENTER));

        // add the above to the northPanel
        prodMain.add(northPanel, BorderLayout.NORTH);

        // results panel
        productTableModel = new ProductTableModel();
        products = new JTable(productTableModel);
        products.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        products.addMouseListener(this);
        po = new ProductOperations();

        // Set the table width, depending upon the width of
        // the columns
        int tableWidth = 0;
        int columnCount = productTableModel.columnModel.getColumnCount();
        for (int i = 0; i < columnCount; i++)
            tableWidth += productTableModel.columnModel.getColumn(i).getWidth();

        JScrollPane scrollPane = new JScrollPane(products);
        scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //productTableModel.getAllProductsTable();
        refreshList();

        prodMain.add(scrollPane, BorderLayout.CENTER);
// south panel

        southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setBackground(UIElements.getColour());

        // bottom buttons
        viewButton = new JButton("View Purchase History");
        viewButton.setPreferredSize(new Dimension(200, 28));
        viewButton.setIcon(new ImageIcon(UIElements.product16));
        southPanel.add(viewButton);
        viewButton.addActionListener(this);

        prodMain.add(southPanel, BorderLayout.SOUTH);

        return prodMain;
    }

    public void refreshList() {
        productTableModel.emptyArray();
        productTableModel.getAllProductsTable();
    }

    public void refine(){
        productTableModel.emptyArray();
    }

    // open the edit window (created a method because it's used in two places - mouse and action listener
    public void displayEdit() {
        Product p = po.productByIDO(selectedRowId);
            new ProductAddEdit(am, 1, this, p);
    }

// BUTTON ACTIONS

    // have to implement these methods for MouseListener
    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(searchField)) {
            if (searchField.getText().equals(textFieldTip)) {
                searchField.setText("");
                searchField.setForeground(null); // reset colour to black
            }
            searchField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                }

                @Override
                public void focusLost(FocusEvent e) { // set the textFieldTip to be visible in text field on focus loss
                    if (searchField.getText().equals("")) {
                        searchField.setText(textFieldTip);
                        searchField.setForeground(Color.GRAY);
                    }
                }
            });
        } else if (e.getSource().equals(products)) {
            selectedRow = products.getSelectedRow();
            selectedRowId = (Integer) products.getValueAt(products.getSelectedRow(), 0);
            if (e.getClickCount() == 2) {
                displayEdit();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addButton))
            new ProductAddEdit(am, 0, this, null);
        else if (e.getSource().equals(viewButton)){
            new PurchaseHistory(selectedRowId);
        }
        else if (e.getSource().equals(searchButton)){
            productTableModel.searchTable(searchField.getText());
        }
        else if (e.getSource() == prodManufacturers){
            prodType.removeAllItems();
            prodType.setEnabled(true);
            String[] types = po.getTypes((String)prodManufacturers.getItemAt(prodManufacturers.getSelectedIndex()));
            for (int i = 0; i < types.length; i++)
                prodType.addItem(types[i]);
            productTableModel.emptyArray();
            String selection = (String)prodManufacturers.getItemAt(prodManufacturers.getSelectedIndex());
            if(selection.equals("All"))
                refreshList();
            else
                productTableModel.refreshTableProduct((String) prodManufacturers.getItemAt(prodManufacturers.getSelectedIndex()));
        }
        else if (e.getSource() == prodType){
            modelTypes.removeAllItems();
            modelTypes.setEnabled(true);
            String[] types = po.getModels((String) prodManufacturers.getItemAt(prodManufacturers.getSelectedIndex()), (String)prodType.getItemAt(prodType.getSelectedIndex()));
            for (int i = 0; i < types.length; i++) {
                modelTypes.addItem(types[i]);
            }
            productTableModel.refreshTableProduct((String)prodManufacturers.getItemAt(prodManufacturers.getSelectedIndex()),
                    (String)prodType.getItemAt(prodType.getSelectedIndex()));
        }
        else if (e.getSource() == editButton){
            if (selectedRow == -1)
                JOptionPane.showMessageDialog(null, "Please Select The Product First", "Product Not Selected", JOptionPane.WARNING_MESSAGE);
            else {
                if (e.getSource().equals(editButton))// edit product
                    displayEdit();
                else if (e.getSource().equals(deleteButton)) {
                    Object[] options = {"Yes", "No"};
                    int choice = JOptionPane.showOptionDialog(prodMain, "Are You Sure?", "Delete Product", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
                    if (choice == 0) {
                        po.deleteProduct((Integer) products.getValueAt(products.getSelectedRow(), 0));
                        JOptionPane.showMessageDialog(prodMain, "Product Deleted");
                        selectedRow = -1;
                        refreshList();
                    }
                }
            }

        }
    }
}