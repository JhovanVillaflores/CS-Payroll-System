SELECT
     attendancelog_perday_tb.`EmployeeID` AS attendancelog_perday_tb_EmployeeID,
     attendancelog_perday_tb.`Employee_Name` AS attendancelog_perday_tb_Employee_Name,
     attendancelog_perday_tb.`Employee_Position` AS attendancelog_perday_tb_Employee_Position,
     attendancelog_perday_tb.`Log_In` AS attendancelog_perday_tb_Log_In,
     attendancelog_perday_tb.`Log_Out` AS attendancelog_perday_tb_Log_Out,
     attendancelog_perday_tb.`Month` AS attendancelog_perday_tb_Month,
     attendancelog_perday_tb.`Day` AS attendancelog_perday_tb_Day,
     attendancelog_perday_tb.`Year` AS attendancelog_perday_tb_Year,
     attendancelog_perday_tb.`Number_of_Hours` AS attendancelog_perday_tb_Number_of_Hours
FROM
     `attendancelog_perday_tb` attendancelog_perday_tb