/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.bl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import za.ac.tut.entities.*;

/**
 *
 * @author MNCEDISI
 */
public class PersonManager implements PersonInterface<Person, Student>
{
    private Connection connection;

    public PersonManager(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public boolean addPerson(Person person, String sqlP, String sqlS) throws SQLException 
    {
        PreparedStatement psp = getConnection().prepareStatement(sqlP);
        psp.setLong(1, person.getIdNumber());
        psp.setString(2, person.getName());
        psp.setString(3, person.getSurname());
        psp.setString(4, String.valueOf(person.getGender()));
        
        PreparedStatement pss = getConnection().prepareStatement(sqlS);
        pss.setLong(1, person.getStudent().getStudentNum());
        pss.setString(2, person.getStudent().outlookEmail());
        pss.setDate(3, person.getStudent().getRegisterDate());
        pss.setLong(4, person.getStudent().getPersonID());
        
        return psp.executeUpdate()!=0&&pss.executeUpdate()!=0 ?true:false;
    }

    @Override
    public boolean removePerson(Long idNumber, String sqlP, String sqlS) throws SQLException 
    {
        PreparedStatement psp = getConnection().prepareStatement(sqlP);
        psp.setLong(1, idNumber);
        
        PreparedStatement pss = getConnection().prepareStatement(sqlS);
        pss.setLong(1, idNumber);
        
        if(pss.executeUpdate()!=0)
        {
            if(psp.executeUpdate()!=0)
            {
                psp.close();
                pss.close();
                return true;
            }
        }
        psp.close();
        pss.close();
        
        return false;
    }

    @Override
    public boolean updateStudent(Student student, String sqlS) throws SQLException 
    {
        PreparedStatement pss = getConnection().prepareStatement(sqlS);
        pss.setDate(1, student.getRegisterDate());
        pss.setLong(2, student.getStudentNum());
        
        return pss.executeUpdate()!=0?true:false;
    }

    @Override
    public Person getPerson(Long idNumber, String sqlP) throws SQLException 
    {
        PreparedStatement psp = getConnection().prepareStatement(sqlP);
        psp.setLong(1, idNumber);
        
        ResultSet rs = psp.executeQuery();
        
        if(rs.next())
        {
            //Person
            String name = rs.getString("Name");
            String surname = rs.getString("Surname");
            Character gender = rs.getString("Gender").charAt(0);
            
            //Student
            Long studentNum = rs.getLong("StudentNumber");
            Date registerDate = rs.getDate("RegisterDate");
            
            Person person = new Person(idNumber, name, surname, gender);
            person.setStudent(new Student(studentNum, idNumber, registerDate));
            
            rs.close();
            return person;
        }
        
        rs.close();
        return null;
        
    }

    @Override
    public boolean removeStudent(Long studentNum, String sqlS) throws SQLException 
    {
        PreparedStatement pss = getConnection().prepareStatement(sqlS);
        pss.setLong(1, studentNum);
        
        return pss.executeUpdate()!=0?true:false;
    }

    @Override
    public Integer numOfStudent(String sqlS) throws SQLException 
    {
        PreparedStatement pss = getConnection().prepareStatement(sqlS);
        
        ResultSet rs = pss.executeQuery();
        
        if(rs.next())
        {
            Integer numOfStudents = rs.getInt("NumberOfStudents");
            
            rs.close();
            return numOfStudents;
        }
        
        rs.close();
        return null;   
    }

    @Override
    public List<Person> getPeople(String sqlP) throws SQLException 
    {
        PreparedStatement psp = getConnection().prepareStatement(sqlP);
        
        ResultSet rs = psp.executeQuery();
        
        List<Person> thePeople = new ArrayList<>();
        
        while(rs.next())
        {
            //Person
            Long idNumber = rs.getLong("IdNumber");
            String name = rs.getString("Name");
            String surname = rs.getString("Surname");
            Character gender = rs.getString("Gender").charAt(0);
            
            //Student
            Long studentNum = rs.getLong("StudentNumber");
            Date registerDate = rs.getDate("RegisterDate");
            
            Person person = new Person(idNumber, name, surname, gender);
            person.setStudent(new Student(studentNum, idNumber, registerDate));
            
            thePeople.add(person);
        }
        
        rs.close();
        return thePeople;
        
    }

    public Connection getConnection() {
        return connection;
    }
    
}
