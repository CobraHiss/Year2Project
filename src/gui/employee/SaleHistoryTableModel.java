//package gui.employee;
//
///*
//IT Tallaght - 2015, S2
//Computing - Year 2, Project
//Group 17 (George - 08/04/2015)
//*/
//
//import database.operations.MemberOperations;
//
//import javax.swing.*;
//import javax.swing.event.TableModelEvent;
//import javax.swing.table.DefaultTableColumnModel;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableColumn;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//
//public class SaleHistoryTableModel extends DefaultTableModel {
//
//    // Model (SaleHistoryTableModel) variables
//    int saleid;
//    String time;
//    Date date;
//    String prodMake;
//    String prodModel;
//    double salePrice;
//    int quantity;
//
//    // Table columns
//    final static int id = 0;
//    final static int saleTime = 1;
//    final static int saleDate = 2;
//    final static int make = 3;
//    final static int model = 4;
//    final static int price = 5;
//    final static int qty = 6;
//    final static String[] columnNames = {"Sale ID", "Sale Time", "Sale Date", "Make", "Model", "Price", "Quantity"};
//
//    // column model
//    DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
//
//    // array for objects that Model fills into
//    private static ArrayList<Object> pHistoryRows = new ArrayList();
//
//    public SaleHistoryTableModel() {
//        TableColumn col;
//        for (int i = 0; i < columnNames.length; i++) {
//            col = new TableColumn(i);
//            col.setHeaderValue(columnNames[i]);
//            // col.setMaxWidth(150); disabled because this does not work, width must be set from the JTable (MemberMain) ex... memTable.getColumnModel().getColumn(1).setPreferredWidth(50);
//            columnModel.addColumn(col);
//        }
//    }
//
//    // overloaded constructor for the model
//    public SaleHistoryTableModel(int saleid, String time, Date date, String prodMake, String prodModel, double salePrice, double totalPrice, int quantity) {
//        this.saleid = saleid;
//        this.time = time;
//        this.date = date;
//        this.prodMake = prodMake;
//        this.prodModel = prodModel;
//        this.salePrice = salePrice;
//        this.totalPrice = totalPrice;
//        this.quantity = quantity;
//    }
//
//    // get purchase list from database based on member id and populate the pHistoryRows model array
//    public void getPurchaseList(int mid) {
//        try {
//            MemberOperations mo = new MemberOperations();
//            ResultSet rset = mo.getPurchases(mid);
//            while (rset.next()) {
//                pHistoryRows.add(new SaleHistoryTableModel(rset.getInt(1), rset.getDate(2), rset.getString(3), rset.getString(4), rset.getDouble(5), rset.getInt(6), rset.getDouble(7)));
//            }
//            rset.close();
//            fireTableChanged(new TableModelEvent(this, -1, -1));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//
//    // get purchase list from database based on member id and sale id, and populate the pHistoryRows model array
//    public void getPurchaseList(int mid, int sid) {
//        try {
//            MemberOperations mo = new MemberOperations();
//            ResultSet rset = mo.getPurchases(mid, sid);
//            while (rset.next()) {
//                pHistoryRows.add(new SaleHistoryTableModel(rset.getInt(1), rset.getDate(2), rset.getString(3), rset.getString(4), rset.getDouble(5), rset.getInt(6), rset.getDouble(7)));
//            }
//            rset.close();
//            fireTableChanged(new TableModelEvent(this, -1, -1));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//
//    //a method just to pass in a column number and row and return that cell value
//    public Object getValueAt(int rowNum, int colNum) {
//        SaleHistoryTableModel row = (SaleHistoryTableModel) pHistoryRows.get(rowNum);//casting a product from the object arraylist to a row type
//        switch (colNum) {
//            case id:
//                return row.getSaleid();
//            case saleDate:
//                return row.getDate();
//            case make:
//                return row.getProdMake();
//            case model:
//                return row.getProdModel();
//            case qty:
//                return row.getQuantity();
//            case price:
//                return row.getSalePrice();
//            case paid:
//                return row.getTotalPrice();
//            default:
//                return "";
//        }
//    }
//
//    // returns column names - if commented out, columns get auto named - A,B,C,D...
//    public String getColumnName(int column) {
//        if (columnNames[column] != null)
//            return columnNames[column];
//        else
//            return "";
//    }
//
//    public void emptyArray() {
//        if (pHistoryRows.size() > 0) {
//            for (int i = pHistoryRows.size(); i > 0; i--) {
//                pHistoryRows.remove(i - 1);
//            }
//        }
//    }
//
//    public ArrayList<Object> getArray() {
//        return pHistoryRows;
//    }
//
//    // don't allow editing cells when double clicked
//    public boolean isCellEditable(int row, int column) {
//        return false;
//    }
//
//    public Class getColumnClass(int column) {
//        return String.class;
//    }
//
//    public int getColumnCount() {
//        return columnNames.length;
//    }
//
//    public int getRowCount() {
//        return pHistoryRows.size();
//    }
//
//// MODEL GETTERS
//
//    public int getSaleid() {
//        return this.saleid;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public String getProdMake() {
//        return prodMake;
//    }
//
//    public String getProdModel() {
//        return prodModel;
//    }
//
//    public double getSalePrice() {
//        return salePrice;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public double getTotalPrice() {
//        return totalPrice;
//    }
//}