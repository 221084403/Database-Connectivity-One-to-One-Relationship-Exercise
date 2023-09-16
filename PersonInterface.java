/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.bl;

import java.sql.*;
import java.util.*;


/**
 *
 * @author MNCEDISI
 */
public interface PersonInterface<P ,S> 
{
    public boolean  addPerson(P p , String sqlP , String sqlS) throws SQLException;
    
    public boolean removePerson(Long idNumber , String sqlP , String sqlS) throws SQLException;
    
    public boolean updateStudent(S s , String sqlS) throws SQLException;
    
    public P getPerson(Long idNumber , String sqlP) throws SQLException;
    
    public boolean removeStudent(Long studentNum , String sqlS) throws SQLException;
    
    public Integer numOfStudent(String sqlS) throws SQLException;
    
    public List<P> getPeople(String sqlP) throws SQLException;
}
