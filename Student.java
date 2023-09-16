/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.entities;

import java.sql.*;

/**
 *
 * @author MNCEDISI
 */
public class Student 
{
    private Long studentNum;
    private Long personID;
    private Date registerDate;

    public Student() {}

    public Student(Long studentNum, Long personID, Date registerDate) 
    {
        this.studentNum = studentNum;
        this.personID = personID;
        this.registerDate = registerDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    
    public Long getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Long studentNum) {
        this.studentNum = studentNum;
    }

    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }
    
    public String outlookEmail()
    {
        return getStudentNum()+"@tut4life.ac.za";
    }
}
