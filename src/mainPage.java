
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.view.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import javax.swing.Timer;


//import org.apache.commons.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jhovan
 */
public final class mainPage extends javax.swing.JFrame implements ActionListener{
    public Connection cn;
    public Statement st;
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
    int yMouse, xMouse;
    /**
     * Creates new form mainPage
     */
    public mainPage() {
            initComponents();
            updateClock();
            new Timer (1000, this).start();
            
             try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/citysquare_payroll?zeroDateTimeBehavior=convertToNull","root","");
            st = cn.createStatement();
            show_employeesList();
            show_attendanceLog();
            
            
        } catch (Exception ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void actionPerformed(ActionEvent e) {
        updateClock();
    }
    private void updateClock() {
        DatePL.setText(dateFormat.format(Calendar.getInstance().getTime()));
        TimePL.setText(timeFormat.format(Calendar.getInstance().getTime()));
    }
   
    @SuppressWarnings("NonPublicExported")
    public ArrayList<employee> employeeList(){
            ArrayList<employee> employeesList = new ArrayList<>();
            try{
            String query = "SELECT * FROM `employeeinfo_tb`";
            ResultSet rs = st.executeQuery(query);
            int i = 0;
            employee emp;
            while(rs.next()){
                emp = new employee(rs.getInt("ID"),rs.getString("EmployeeID"), rs.getString("First_Name"), rs.getString("Middle_Name"), rs.getString("Last_Name"), rs.getString("Job_Title"), rs.getString("Birth_Date"),
                        rs.getString("Address"), rs.getString("Gender"),rs.getString("RatePerDay"),rs.getString("Contact_No"),rs.getString("Tin_No"),rs.getString("SSS_No"),
                        rs.getString("PhilHealth_No"),rs.getString("PAGIBIG_No"),rs.getString("Date_Start"),rs.getString("Age"));
                employeesList.add(emp); i++;
            }
            numberOfEmployee.setText(String.valueOf(i));
                    
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
           return employeesList;
    }
    public void show_employeesList(){
        ArrayList<employee> List = employeeList();
        DefaultTableModel model =  (DefaultTableModel)empTable.getModel();
        Object[] row = new Object[13];
        for(int i=0; i<List.size();i++){
            row[0] = List.get(i).getEmployeeID(); 
            row[1] = List.get(i).getEmpName();
            row[2] = List.get(i).getEmpJobTitle();
            row[3] = List.get(i).getEmpAge();
            model.addRow(row);
        }
         
    }
    
    
    @SuppressWarnings("NonPublicExported")
    public ArrayList<AttendanceLog> attendanceLog(){
        ArrayList<AttendanceLog> attendanceList = new ArrayList<>();
        try {
            String query = "SELECT * FROM `attendancelog_perday_tb`";
            ResultSet rs1 = st.executeQuery(query);
            
            AttendanceLog aLog;
            while(rs1.next()){
                aLog = new AttendanceLog(rs1.getInt("ID"),rs1.getString("EmployeeID"),rs1.getString("Employee_Name"),rs1.getString("Employee_Position"),rs1.getString("Log_In"),rs1.getString("Log_Out"),
                rs1.getString("Month"),rs1.getString("Day"),rs1.getString("Year"),rs1.getString("Number_of_Hours"));
                attendanceList.add(aLog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attendanceList;
    }
    public void show_attendanceLog(){
        try{
        ArrayList<AttendanceLog> attendanceList = attendanceLog();
        DefaultTableModel model =  (DefaultTableModel)attendanceLogTable.getModel();
        Object[] row = new Object[13];
        for(int l=0; l<attendanceList.size();l++){
            row[0] = attendanceList.get(l).getEmployeeID();
            row[1] = attendanceList.get(l).getEmployeeName();
            row[2] = attendanceList.get(l).getEmployeePosition();
            row[3] = attendanceList.get(l).getLogin();
            row[4] = attendanceList.get(l).getLogout();
            model.addRow(row);
       
        }
        
         }catch(Exception e){
              System.out.println(e);  
        }
    }
     @SuppressWarnings("NonPublicExported")
   
    
     public ArrayList<Salary_Deduction> Salary_Deduction(){
        ArrayList<Salary_Deduction> Salary_DeductionList = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM `salary_deduction`";
            ResultSet rs2 = st.executeQuery(query);
            
            Salary_Deduction sD;
            while(rs2.next()){
                sD = new Salary_Deduction(rs2.getInt("DeducID"),rs2.getString("DeducName"),rs2.getString("DeducValue"));
                Salary_DeductionList.add(sD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Salary_DeductionList;
     }
     
     public ArrayList<Employee_Deduction> Employee_Deduction(){
        ArrayList<Employee_Deduction> Employee_DeductionList = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM `Employee_Deduction`";
            ResultSet rs2 = st.executeQuery(query);
            
            Employee_Deduction eD;
            while(rs2.next()){
                eD = new Employee_Deduction(rs2.getInt("ID"),rs2.getString("EmployeeID"),rs2.getString("SSS"),rs2.getString("PH"),rs2.getString("PAGIBIG"));
                Employee_DeductionList.add(eD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Employee_DeductionList;
     }
     
     
    
    public void update_attendance_table(){
        String newAttendance = "SELECT * FROM `attendancelog_perday_tb`";
        ResultSet aRs ;
        try{
            st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                aRs = st.executeQuery(newAttendance);
                attendanceLogTable.setModel(DbUtils.resultSetToTableModel(aRs));
        }catch(SQLException e){
        }
    }
    public void update_employee_table(){
        String newEmployee = "SELECT * FROM `employeeinfo_tb`";
        ResultSet eRs ;
        try{
            st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                eRs = st.executeQuery(newEmployee);
                attendanceLogTable.setModel(DbUtils.resultSetToTableModel(eRs));
        }catch(SQLException e){
        }
    }
    public String get_month_array(String indexNum){
        
        @SuppressWarnings("MismatchedReadAndWriteOfArray")
        String[] monthArr = new String[] { "January","February",
                                           "March","April",
                                           "May","June",
                                            "July","August",
                                            "September","October",
                                            "November","December"};
        
        String newMonth = monthArr[Integer.parseInt(indexNum)];
        
        return newMonth;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sideBar = new javax.swing.JPanel();
        homePage = new javax.swing.JPanel();
        homeLabel = new javax.swing.JLabel();
        empInfoPage = new javax.swing.JPanel();
        empInfoLabel = new javax.swing.JLabel();
        attendancePage = new javax.swing.JPanel();
        attendanceLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        homePage1 = new javax.swing.JPanel();
        homeLabel1 = new javax.swing.JLabel();
        IDnumberLabel = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        closeBtn = new javax.swing.JLabel();
        minBtn = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        numberOfEmployee = new javax.swing.JLabel();
        generateBtn = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        empIDgen = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        DatePL = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        TimePL = new javax.swing.JLabel();
        employeeInfoPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        empTable = new javax.swing.JTable();
        searchFld = new javax.swing.JTextField();
        addEmployeeBtn = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        searchBtn = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        attendanceLogPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        attendanceLogTable = new javax.swing.JTable();
        monthCB = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        searchMonth = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        viewInfoPanel = new javax.swing.JPanel();
        BackBtn = new javax.swing.JButton();
        employeeNamePL = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        empTINPL = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        empPHPL = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        empPAGIBIGPL = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        empSSSPL = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        empGender = new javax.swing.JLabel();
        empAge = new javax.swing.JLabel();
        birthDatePL = new javax.swing.JLabel();
        empAddressPL = new javax.swing.JLabel();
        empContactNoPL = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        dateStartPL = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jButton5 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        basicSalaryPL = new javax.swing.JLabel();
        employeeIDPL = new javax.swing.JLabel();
        empPositionPL = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 500));
        setUndecorated(true);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setMaximumSize(new java.awt.Dimension(900, 500));
        mainPanel.setMinimumSize(new java.awt.Dimension(900, 500));
        mainPanel.setPreferredSize(new java.awt.Dimension(900, 500));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideBar.setBackground(new java.awt.Color(102, 102, 102));
        sideBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homePage.setBackground(new java.awt.Color(102, 102, 102));
        homePage.setForeground(new java.awt.Color(102, 102, 102));
        homePage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homePage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homePageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homePageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homePageMouseExited(evt);
            }
        });
        homePage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        homeLabel.setForeground(new java.awt.Color(51, 51, 51));
        homeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Home_24px_1_1.png"))); // NOI18N
        homeLabel.setText(" Home");
        homePage.add(homeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 6, -1, -1));

        sideBar.add(homePage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 200, 40));

        empInfoPage.setBackground(new java.awt.Color(102, 102, 102));
        empInfoPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        empInfoPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empInfoPageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                empInfoPageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                empInfoPageMouseExited(evt);
            }
        });
        empInfoPage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        empInfoLabel.setBackground(new java.awt.Color(102, 102, 102));
        empInfoLabel.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        empInfoLabel.setForeground(new java.awt.Color(51, 51, 51));
        empInfoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Set_As_Resume_24px_3.png"))); // NOI18N
        empInfoLabel.setText(" Employee List");
        empInfoPage.add(empInfoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 6, -1, -1));

        sideBar.add(empInfoPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 200, 40));

        attendancePage.setBackground(new java.awt.Color(102, 102, 102));
        attendancePage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        attendancePage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attendancePageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                attendancePageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                attendancePageMouseExited(evt);
            }
        });
        attendancePage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        attendanceLabel.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        attendanceLabel.setForeground(new java.awt.Color(51, 51, 51));
        attendanceLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Today_24px_1_1.png"))); // NOI18N
        attendanceLabel.setText(" Attendance Log");
        attendancePage.add(attendanceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 6, -1, -1));

        sideBar.add(attendancePage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 200, 40));

        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_MySQL_24px_1.png"))); // NOI18N
        sideBar.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, -1, -1));

        jLabel4.setFont(new java.awt.Font("Felix Titling", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CITY SQUARE");
        sideBar.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Source_Code_24px_3.png"))); // NOI18N
        jLabel3.setText(" Team Array");
        sideBar.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Org_Unit_96px_4.png"))); // NOI18N
        sideBar.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Java_24px_2.png"))); // NOI18N
        sideBar.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 440, -1, -1));

        homePage1.setBackground(new java.awt.Color(102, 102, 102));
        homePage1.setForeground(new java.awt.Color(102, 102, 102));
        homePage1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homePage1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homePage1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homePage1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homePage1MouseExited(evt);
            }
        });
        homePage1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel1.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        homeLabel1.setForeground(new java.awt.Color(51, 51, 51));
        homeLabel1.setText(" Logout");
        homePage1.add(homeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        sideBar.add(homePage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 200, 40));

        IDnumberLabel.setForeground(new java.awt.Color(102, 102, 102));
        IDnumberLabel.setText("ID");
        sideBar.add(IDnumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        mainPanel.add(sideBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 200, 470));

        headerPanel.setBackground(new java.awt.Color(51, 51, 51));
        headerPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerPanelMouseDragged(evt);
            }
        });
        headerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerPanelMousePressed(evt);
            }
        });
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        closeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Close_Window_24px_1.png"))); // NOI18N
        closeBtn.setAlignmentY(0.0F);
        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtnMouseClicked(evt);
            }
        });
        headerPanel.add(closeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, -1, -1));

        minBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Minimize_Window_24px_1.png"))); // NOI18N
        minBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minBtnMouseClicked(evt);
            }
        });
        headerPanel.add(minBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 0, -1, -1));

        jLabel1.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CITY SQUARE PAYROLL SYSTEM v1");
        headerPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 500, -1));

        mainPanel.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 30));

        bodyPanel.setBackground(new java.awt.Color(255, 255, 255));
        bodyPanel.setMaximumSize(new java.awt.Dimension(633, 430));
        bodyPanel.setMinimumSize(new java.awt.Dimension(633, 430));
        bodyPanel.setPreferredSize(new java.awt.Dimension(633, 430));
        bodyPanel.setLayout(new java.awt.CardLayout());

        homePanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Dashboard");

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Number of Employee");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel24)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 230, -1));

        numberOfEmployee.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        numberOfEmployee.setForeground(new java.awt.Color(255, 255, 255));
        numberOfEmployee.setText("0");
        jPanel5.add(numberOfEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, 60));

        generateBtn.setText("Generate Payslip");
        generateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateBtnActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Generate Payslip");

        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Enter Employee ID");
        jLabel9.setEnabled(false);

        jLabel13.setText("Today:");

        DatePL.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        DatePL.setText("DATE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(DatePL, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(DatePL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel17.setText("Time:");

        TimePL.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        TimePL.setText("TIME");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(TimePL, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(TimePL, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addGroup(homePanelLayout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(empIDgen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(generateBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(26, 131, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(empIDgen, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generateBtn)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        bodyPanel.add(homePanel, "card2");

        employeeInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        empTable.setAutoCreateRowSorter(true);
        empTable.setBackground(new java.awt.Color(204, 204, 204));
        empTable.setFont(new java.awt.Font("HelvLight", 0, 12)); // NOI18N
        empTable.setForeground(new java.awt.Color(51, 51, 51));
        empTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EmployeeID", "EmployeeName", "Job Title"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        empTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(empTable);

        searchFld.setFont(new java.awt.Font("HelvLight", 0, 12)); // NOI18N

        addEmployeeBtn.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        addEmployeeBtn.setForeground(new java.awt.Color(102, 102, 102));
        addEmployeeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Add_User_Male_24px.png"))); // NOI18N
        addEmployeeBtn.setText("Add Employee");
        addEmployeeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addEmployeeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addEmployeeBtnMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("EMPLOYEE INFORMATION");

        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("ENTER EMPLOYEE ID");

        jButton2.setText("Print");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout employeeInfoPanelLayout = new javax.swing.GroupLayout(employeeInfoPanel);
        employeeInfoPanel.setLayout(employeeInfoPanelLayout);
        employeeInfoPanelLayout.setHorizontalGroup(
            employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                        .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                                    .addComponent(searchFld, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(searchBtn)
                                    .addGap(43, 43, 43)
                                    .addComponent(addEmployeeBtn)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                        .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        employeeInfoPanelLayout.setVerticalGroup(
            employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchFld, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn)
                    .addComponent(addEmployeeBtn)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        bodyPanel.add(employeeInfoPanel, "card3");

        attendanceLogPanel.setBackground(new java.awt.Color(255, 255, 255));

        attendanceLogTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EmployeeID", "Name", "Position", "Log In", "Log Out"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attendanceLogTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attendanceLogTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(attendanceLogTable);

        jTabbedPane1.addTab("Log Per Day", jScrollPane2);

        monthCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel23.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("Attendance Log");

        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        searchMonth.setText("Search");
        searchMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMonthActionPerformed(evt);
            }
        });

        jLabel19.setText("Month");

        javax.swing.GroupLayout attendanceLogPanelLayout = new javax.swing.GroupLayout(attendanceLogPanel);
        attendanceLogPanel.setLayout(attendanceLogPanelLayout);
        attendanceLogPanelLayout.setHorizontalGroup(
            attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                            .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                                    .addComponent(monthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(searchMonth))
                                .addComponent(jLabel19))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        attendanceLogPanelLayout.setVerticalGroup(
            attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, attendanceLogPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(29, 29, 29))
                    .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchMonth))))
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        bodyPanel.add(attendanceLogPanel, "card4");

        viewInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        BackBtn.setText("Back");
        BackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtnActionPerformed(evt);
            }
        });

        employeeNamePL.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        employeeNamePL.setForeground(new java.awt.Color(51, 51, 51));
        employeeNamePL.setText("Employee Name");

        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Position");

        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("TIN :");

        empTINPL.setText("number");

        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("PhilHealth :");

        empPHPL.setText("number");

        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("PAGIBIG :");

        empPAGIBIGPL.setText("number");

        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("SSS :");

        empSSSPL.setText("number");

        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Employee ID");

        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Gender :");

        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Address :");

        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("Contact Number :");

        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("Age :");

        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setText("Birth Date :");

        empGender.setText("number");

        empAge.setText("number");

        birthDatePL.setText("number");

        empAddressPL.setText("number");

        empContactNoPL.setText("number");

        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setText("Date Start :");

        dateStartPL.setText("number");

        jButton5.setText("Edit Information");

        jLabel35.setForeground(new java.awt.Color(51, 51, 51));
        jLabel35.setText("Basic Salary :");

        basicSalaryPL.setText("number");

        employeeIDPL.setText("number");

        empPositionPL.setText("number");

        javax.swing.GroupLayout viewInfoPanelLayout = new javax.swing.GroupLayout(viewInfoPanel);
        viewInfoPanel.setLayout(viewInfoPanelLayout);
        viewInfoPanelLayout.setHorizontalGroup(
            viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(BackBtn))
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewInfoPanelLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dateStartPL, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(basicSalaryPL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(65, 65, 65))
                            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(employeeNamePL, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel25)
                                                .addGap(9, 9, 9)
                                                .addComponent(empContactNoPL, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(84, 84, 84))
                                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                            .addGap(44, 44, 44)
                                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel20)
                                                .addComponent(jLabel11))
                                            .addGap(39, 39, 39)
                                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(empPositionPL, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(employeeIDPL, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                            .addGap(45, 45, 45)
                                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel21)
                                                .addComponent(jLabel26)
                                                .addComponent(jLabel27)
                                                .addComponent(jLabel22))
                                            .addGap(42, 42, 42)
                                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(empAddressPL, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(birthDatePL, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                                    .addComponent(empAge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(empGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGap(18, 18, 18)
                                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewInfoPanelLayout.createSequentialGroup()
                                                    .addComponent(jLabel12)
                                                    .addGap(70, 70, 70)
                                                    .addComponent(empTINPL, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                                                .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                                    .addComponent(jLabel14)
                                                    .addGap(31, 31, 31)
                                                    .addComponent(empPHPL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel16)
                                                        .addComponent(jLabel18))
                                                    .addGap(42, 42, 42)
                                                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(empPAGIBIGPL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(empSSSPL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewInfoPanelLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(176, 176, 176))))
                                .addGap(58, 58, 58)))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        viewInfoPanelLayout.setVerticalGroup(
            viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackBtn)
                .addGap(26, 26, 26)
                .addComponent(employeeNamePL, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel33)
                    .addComponent(dateStartPL)
                    .addComponent(employeeIDPL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel35)
                    .addComponent(basicSalaryPL)
                    .addComponent(empPositionPL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewInfoPanelLayout.createSequentialGroup()
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(empAddressPL))
                            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(empGender))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addGap(5, 5, 5)
                                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(birthDatePL))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(empContactNoPL, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(viewInfoPanelLayout.createSequentialGroup()
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(empTINPL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(empPHPL)
                            .addComponent(empAge))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(empPAGIBIGPL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(empSSSPL))))
                .addGap(70, 70, 70)
                .addComponent(jButton5)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        bodyPanel.add(viewInfoPanel, "card5");

        mainPanel.add(bodyPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 700, 470));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(900, 500));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void empInfoPageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empInfoPageMouseEntered
        // TODO add your handling code here:
       empInfoPage.setBackground(new Color(51,51,51));
       empInfoLabel.setForeground(new Color(102,102,102));
    }//GEN-LAST:event_empInfoPageMouseEntered

    private void empInfoPageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empInfoPageMouseExited
       
      empInfoPage.setBackground(new Color(102,102,102));
      empInfoLabel.setForeground(new Color(51,51,51));
    }//GEN-LAST:event_empInfoPageMouseExited

    private void attendancePageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendancePageMouseEntered
      
        attendancePage.setBackground(new Color(51,51,51));
        attendanceLabel.setForeground(new Color(102,102,102));
    }//GEN-LAST:event_attendancePageMouseEntered

    private void attendancePageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendancePageMouseExited
        attendancePage.setBackground(new Color(102,102,102));
        attendanceLabel.setForeground(new Color(51,51,51));
    }//GEN-LAST:event_attendancePageMouseExited

    private void homePageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePageMouseEntered
       
      homePage.setBackground(new Color(51,51,51));
      homeLabel.setForeground(new Color(102,102,102));
    }//GEN-LAST:event_homePageMouseEntered

    private void homePageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePageMouseExited
     
        homePage.setBackground(new Color(102,102,102));
        homeLabel.setForeground(new Color(51,51,51));
    }//GEN-LAST:event_homePageMouseExited

    private void empTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empTableMouseClicked
      
          homePage.setEnabled(false);
          empInfoPage.setEnabled(false);
          attendancePage.setEnabled(false);
            int emp = empTable.getSelectedRow();
        

        ArrayList<employee> List = employeeList();
        for(int i=0; i<List.size();i++) {
            if(i==emp){
                try {
                    IDnumberLabel.setText(List.get(i).getEmployeeID());
                    
                    bodyPanel.removeAll();
                    bodyPanel.repaint();
                    bodyPanel.revalidate();
                    bodyPanel.add(viewInfoPanel);
                    bodyPanel.repaint();
                    bodyPanel.revalidate();
                    
                    String query = "SELECT * FROM `employeeinfo_tb` WHERE employeeID = " + IDnumberLabel.getText();
                    ResultSet rs = st.executeQuery(query);
                   
                   while(rs.next()){
                       employeeNamePL.setText(rs.getString("First_Name")+" "+rs.getString("Middle_Name")+" "+rs.getString("Last_Name"));
                       employeeIDPL.setText(rs.getString("EmployeeID"));
                       empPositionPL.setText(rs.getString("Job_Title"));
                       empGender.setText(rs.getString("Gender"));
                       empAge.setText(rs.getString("Age"));
                       birthDatePL.setText(rs.getString("Birth_Date"));
                       empAddressPL.setText(rs.getString("Address"));
                       empContactNoPL.setText(rs.getString("Contact_No"));
                       dateStartPL.setText(rs.getString("Date_Start"));
                       basicSalaryPL.setText(rs.getString("RatePerDay"));
                       empTINPL.setText(rs.getString("TIN_No"));
                       empPHPL.setText(rs.getString("PhilHealth_No"));
                       empPAGIBIGPL.setText(rs.getString("PAGIBIG_No"));
                       empSSSPL.setText(rs.getString("SSS_No"));
                   }
                   
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
    }//GEN-LAST:event_empTableMouseClicked

    private void homePageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePageMouseClicked
        // TODO add your handling code here:
        
        homeLabel.setForeground(new Color(102,102,102));
        
        homePage.setBackground(new Color(51,51,51));
        empInfoPage.setBackground(new Color(102,102,102));
        attendancePage.setBackground(new Color(102,102,102));
        
        bodyPanel.removeAll();
        bodyPanel.repaint();
        bodyPanel.revalidate();
        bodyPanel.add(homePanel);
        bodyPanel.repaint();
        bodyPanel.revalidate();
           
    }//GEN-LAST:event_homePageMouseClicked

    private void empInfoPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empInfoPageMouseClicked
        // TODO add your handling code here:
        empInfoLabel.setForeground(new Color(102,102,102));
        
        homePage.setBackground(new Color(102,102,102));
        empInfoPage.setBackground(new Color(51,51,51));
        attendancePage.setBackground(new Color(102,102,102));
        
        bodyPanel.removeAll();
        bodyPanel.repaint();
        bodyPanel.revalidate();
        bodyPanel.add(employeeInfoPanel);
        bodyPanel.repaint();
        bodyPanel.revalidate();
        
        update_employee_table();
    }//GEN-LAST:event_empInfoPageMouseClicked

    private void attendancePageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendancePageMouseClicked
        // TODO add your handling code here:
        bodyPanel.removeAll();
        bodyPanel.repaint();
        bodyPanel.revalidate();
        bodyPanel.add(attendanceLogPanel);
        bodyPanel.repaint();
        bodyPanel.revalidate();
        update_attendance_table();
        
    }//GEN-LAST:event_attendancePageMouseClicked

    private void closeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtnMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeBtnMouseClicked

    private void headerPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_headerPanelMousePressed

    private void headerPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_headerPanelMouseDragged

    private void minBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minBtnMouseClicked
        // TODO add your handling code here:
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_minBtnMouseClicked

    private void homePage1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePage1MouseClicked
        // TODO add your handling code here:
        new loginForm().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_homePage1MouseClicked

    private void homePage1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePage1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_homePage1MouseEntered

    private void homePage1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePage1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_homePage1MouseExited

    private void addEmployeeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addEmployeeBtnMouseClicked
       new addEmployeeForm().setVisible(true);
    }//GEN-LAST:event_addEmployeeBtnMouseClicked

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        // TODO add your handling code here:
        IDnumberLabel.setText("");
        homePage.setEnabled(true);
        empInfoPage.setEnabled(true);
        attendancePage.setEnabled(true);
        
        bodyPanel.removeAll();
        bodyPanel.repaint();
        bodyPanel.revalidate();
        bodyPanel.add(employeeInfoPanel);
        bodyPanel.repaint();
        bodyPanel.revalidate();
        
         int emp = empTable.getSelectedRow();
    
        ArrayList<employee> List = employeeList();
        Object[] row = new Object[13];
        for(int i=0; i<List.size();i++) {
            row[0] = List.get(i).getEmployeeID();
            row[1] = List.get(i).getEmpName();
            row[2] = List.get(i).getEmpJobTitle();
        }
    }//GEN-LAST:event_BackBtnActionPerformed
    private String generateChoice;
    @SuppressWarnings("IncompatibleEquals")
    private void generateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateBtnActionPerformed
      // TODO add your handling code here:
      
            String emp = empIDgen.getText();
         
       // try{
	//InputStream in = new FileInputStream(new File("C:\\Users\\Jhovan\\Documents\\NetBeansProjects\\Payroll System\\src\\reports\\Payslip.jrxml"));
	//JasperDesign jd = JRXmlLoader.load(in);
	String sql = "SELECT * FROM salary WHERE EmployeeID = '"+emp+"'";
	JRDesignQuery newQuery = new JRDesignQuery();
	newQuery.setText(sql);
	//jd.setQuery(newQuery);
	//JasperReport jr = JasperCompileManager.compileReport(jd);
	HashMap para = new HashMap();
	//JasperPrint j = JasperFillManager.fillReport(jr,para,cn);
	//JasperViewer.viewReport(j,false);
	//OutputStream os = new FileOutputStream(new File("C:\\Users\\Jhovan\\Documents\\NetBeansProjects\\Payroll System\\src\\"));
	//JasperExportManager.exportReportToPdfStream(j,os);
	
      //  }catch(Exception e ){
          
            //System.out.println(e);
        //}*/
        
    }//GEN-LAST:event_generateBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
        String keyword = searchFld.getText();
        String newEmployee = "SELECT EmployeeID, First_Name, Middle_Name,Last_Name FROM `employeeinfo_tb` WHERE EmployeeID = "+keyword;
        ResultSet eRs ;
        try{
            st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                eRs = st.executeQuery(newEmployee);
                empTable.setModel(DbUtils.resultSetToTableModel(eRs));
        }catch(SQLException e){
        }
    }//GEN-LAST:event_searchBtnActionPerformed

    private void attendanceLogTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendanceLogTableMouseClicked
        // TODO add your handling code here:
        ArrayList<AttendanceLog> attendanceList = attendanceLog();
        Object[] row = new Object[13];
        for(int i=0; i<attendanceList.size();i++) {
            row[0] = attendanceList.get(i).getEmployeeID();
            row[1] = attendanceList.get(i).getEmployeeName();
            row[2] = attendanceList.get(i).getEmployeePosition();
            row[3] = attendanceList.get(i).getLogin();
            row[4] = attendanceList.get(i).getLogout();
        }
    }//GEN-LAST:event_attendanceLogTableMouseClicked

    private void searchMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMonthActionPerformed
        // TODO add your handling code here:
        
        String month = (String) monthCB.getSelectedItem();
        
        if(month.equals(" ")){
             String newAttendance = "SELECT * FROM `attendancelog_perday_tb`";
                ResultSet aRs ;
                try{
                    
                    st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    aRs = st.executeQuery(newAttendance);
                    attendanceLogTable.setModel(DbUtils.resultSetToTableModel(aRs));
                    
                }catch(SQLException e){
                    System.out.println(e);
                }
        
        }else{
              String newAttendance = "SELECT * FROM `attendancelog_perday_tb` WHERE Month = '"+month+"'";
                ResultSet aRs ;
                try{
                    
                    st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    aRs = st.executeQuery(newAttendance);
                    attendanceLogTable.setModel(DbUtils.resultSetToTableModel(aRs));
                    
                }catch(SQLException e){
                    System.out.println(e);
                }
        
        
        }
    }//GEN-LAST:event_searchMonthActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        try{
            attendanceLogTable.print();
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try{
            empTable.print();
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("dracula".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JLabel DatePL;
    private javax.swing.JLabel IDnumberLabel;
    private javax.swing.JLabel TimePL;
    private javax.swing.JLabel addEmployeeBtn;
    private javax.swing.JLabel attendanceLabel;
    private javax.swing.JPanel attendanceLogPanel;
    private javax.swing.JTable attendanceLogTable;
    private javax.swing.JPanel attendancePage;
    private javax.swing.JLabel basicSalaryPL;
    private javax.swing.JLabel birthDatePL;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel closeBtn;
    private javax.swing.JLabel dateStartPL;
    private javax.swing.JLabel empAddressPL;
    private javax.swing.JLabel empAge;
    private javax.swing.JLabel empContactNoPL;
    private javax.swing.JLabel empGender;
    private javax.swing.JTextField empIDgen;
    private javax.swing.JLabel empInfoLabel;
    private javax.swing.JPanel empInfoPage;
    private javax.swing.JLabel empPAGIBIGPL;
    private javax.swing.JLabel empPHPL;
    private javax.swing.JLabel empPositionPL;
    private javax.swing.JLabel empSSSPL;
    private javax.swing.JLabel empTINPL;
    private javax.swing.JTable empTable;
    private javax.swing.JLabel employeeIDPL;
    private javax.swing.JPanel employeeInfoPanel;
    private javax.swing.JLabel employeeNamePL;
    private javax.swing.JButton generateBtn;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel homeLabel;
    private javax.swing.JLabel homeLabel1;
    private javax.swing.JPanel homePage;
    private javax.swing.JPanel homePage1;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel minBtn;
    private javax.swing.JComboBox<String> monthCB;
    private javax.swing.JLabel numberOfEmployee;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchFld;
    private javax.swing.JButton searchMonth;
    private javax.swing.JPanel sideBar;
    private javax.swing.JPanel viewInfoPanel;
    // End of variables declaration//GEN-END:variables
}
