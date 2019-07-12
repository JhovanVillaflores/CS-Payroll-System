/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jhovan
 */
public class attendanceSummary {
    private int AttendSumID;
    private String EmployeeID, EmployeeName, EmployeePosition,Number_of_Days, Month, Year;

    attendanceSummary(int AttendSumID, String EmployeeID, String EmployeeName, String EmployeePosition, String Number_of_Days, String Month, String Year) {
        
        this.AttendSumID = AttendSumID;
        this.EmployeeID = EmployeeID;
        this.EmployeeName = EmployeeName;
        this.EmployeePosition = EmployeePosition;
        this.Number_of_Days = Number_of_Days;
        this.Month = Month;
        this.Year = Year;
    }
    
    public int getAttendSumID(){
        return AttendSumID;
    }
    public String getEmployeeID(){
        return EmployeeID;
    }
    public String getEmployeeName(){
        return EmployeeName;
    }
    public String getEmployeePosition(){
        return EmployeePosition;
    }
    public String getDaysRendered(){
        return Number_of_Days;
    }
    public String getMonth(){
        return Month;
    }
    public String getYear(){
        return Year;
    }
}
