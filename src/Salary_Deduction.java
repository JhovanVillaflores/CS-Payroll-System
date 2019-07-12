/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jhovan
 */
public class Salary_Deduction {
    private int DeducID;
    private String DeducName, DeducValue;
    
    public Salary_Deduction(int DeducID, String DeducName, String DeducValue){
    
        this.DeducID=DeducID;
        this.DeducName=DeducName;
        this.DeducValue=DeducValue;
    
    }
    public int getDeducID(){
        return DeducID;
    }
    public String getDeducName(){
        return DeducName;
    }
    public String getDeducValue(){
        return DeducName;
    }
}
