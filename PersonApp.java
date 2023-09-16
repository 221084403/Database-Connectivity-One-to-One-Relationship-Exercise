/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personapp;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import za.ac.tut.bl.*;
import za.ac.tut.entities.*;

/**
 *
 * @author MNCEDISI
 */
public class PersonApp 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        
        //database connection
        String dbURL = "jdbc:derby://localhost:1527/PersonDataBase";
        String userName = "PersonDB";
        String password = "123";
        
        //insert tables
        String insertSQLP = "INSERT INTO PERSON_TBL VALUES( ? ,?, ? , ?)" ;
        
        String insertSQLS = "INSERT INTO STUDENT_TBL VALUES(? ,? , ? ,?)";
        
        //delete person
        String removeSQLP = "DELETE FROM PERSON_TBL "+
                            "WHERE IdNumber = ? ";
        
        String removeSQLS = "DELETE FROM STUDENT_TBL "+
                            "WHERE PersonID = ? ";
        
        //upate only student
        String updateSQLS = "UPDATE STUDENT_TBL "+
                            "SET RegisterDate = ? "+
                            "WHERE StudentNumber = ? ";
        
        //get a person
        String personSQLP = "SELECT p.IdNumber IdNumber , p.Name Name , p.Surname Surname , p.Gender Gender , "+
                            "s.StudentNumber StudentNumber , s.RegisterDate RegisterDate FROM "+
                            "PERSON_TBL p , STUDENT_TBL s "+
                            "WHERE p.IdNumber = s.PersonID "+
                            " AND "+
                            "p.IdNumber = ? ";
        
        //remove student only
        String deleteSQLS = "DELETE FROM STUDENT_TBL "+
                            "WHERE StudentNumber = ? ";
        
        //Number of student into a database
        String numOfStudntSQLS = "SELECT COUNT(*) NumberOfStudents FROM STUDENT_TBL ";
        
        //display all people that are store into database
        String displaySQLP ="SELECT p.IdNumber IdNumber , p.Name Name , p.Surname Surname , p.Gender Gender , "+
                            "s.StudentNumber StudentNumber , s.RegisterDate RegisterDate FROM "+
                            "PERSON_TBL p , STUDENT_TBL s "+
                            "WHERE p.IdNumber = s.PersonID ";     
        try
        {
            Connection connection = DriverManager.getConnection(dbURL, userName, password);
            
            PersonManager pm = new PersonManager(connection);
            
            Person person = null;
            Student student = null;
            Long idNumber  = null;
            Long studentNum  = null;
            
            int option = showOption();
            
            while(option!=8)
            {
                switch(option)
                {
                    case 1:
                        //add into database
                        person = getPerson();
                        
                        if(pm.addPerson(person, insertSQLP, insertSQLS))
                            System.out.println("The person is stored");
                        else
                            System.err.println("The person is not stored");
                    break;
                    
                    case 2:
                        //delete person
                        idNumber = getIdNumber();
                        
                        if(pm.removePerson(idNumber, removeSQLP, removeSQLS))
                            System.out.println("The person is removed");
                        else
                            System.err.println("The person is not removed");
                    break;
                        
                    case 3:
                        //update student
                        student = getStudent();
                        
                        if(pm.updateStudent(student, updateSQLS))
                            System.out.println("The student is updated");
                        else
                            System.err.println("The student is not updated");
                    break;
                        
                    case 4:
                        // get person
                        idNumber = getIdNumber();
                        
                        person = pm.getPerson(idNumber, personSQLP);
                        
                        if(person !=null)
                            displayPerson(person);
                        else
                            System.err.println("The person is not found ");
                    break;
                        
                    case 5:
                        //delete only student
                        studentNum = getStudentNumber();
                        
                        if(pm.removeStudent(studentNum, deleteSQLS))
                            System.out.println("The student is deleted");
                        else
                            System.err.println("The student is not deleted");
                    break;
                        
                    case 6:
                        //number of student into a database
                        Integer numOfStudents = pm.numOfStudent(numOfStudntSQLS);
                        
                        if(!numOfStudents.equals(null))
                            System.out.println("The number of students are :"+numOfStudents);
                        else
                            System.err.println("No students in the database ");
                    break;
                    
                    case 7:
                        //display all people
                        List<Person> thePeople = pm.getPeople(displaySQLP);
                        
                        if(!thePeople.isEmpty())
                            displayPeople(thePeople);
                        else
                            System.err.println("Nothing on the list");
                    break;
                        
                    default:
                        System.err.println("Invalid option. Please re-enter again");
                    break;
                        
                }
                
                option = showOption();
            }
                    
        } 
        
        catch (SQLException ex)
        {
            System.err.println("Something went wrong\n"+ex.getMessage());
        }
        
        catch(InputMismatchException ex)
        {
            System.err.println("Invalid respond.Enter a digit");
        }
        
        finally
        {
            System.out.println("\nThe application is closed\n");
        }
        
    }

    private static int showOption() 
    {
        Scanner sc = new Scanner(System.in);
        
        String menu = "\nPlease select one of the following option :\n\n"+
                      "1. Store a person into the database.\n"+
                      "2. Delete a person.\n"+
                      "3. Update a student.\n"+
                      "4. Get a person.\n"+
                      "5. Delete a student.\n"+
                      "6. Number of student stored in the database.\n"+
                      "7. Display all people.\n"+
                      "8. Exit.\n\n"+
                      "Your option :";
        
        System.out.print(menu);
        
        return sc.nextInt();
    }

    private static Person getPerson() 
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\nPerson"+
                         "\n--------\n");
        System.out.print("Enter the ID Number\t:");
        Long idNumber = sc.nextLong();
        
        sc.nextLine();
        
        System.out.print("Enter the name\t\t:");
        String name = sc.nextLine();
        
        System.out.print("Enter the surname\t:");
        String surname = sc.nextLine();
        
        System.out.print("Enter the gender[F/M]\t:");
        Character gender = sc.nextLine().toUpperCase().charAt(0);
        
        System.out.print("\nStudent"+
                         "\n--------\n");
        System.out.print("Enter the student Number\t\t:");
        Long studentNum = sc.nextLong();
        
        sc.nextLine();
        
        System.out.print("Enter your registration date[yyyy-mm-dd]:");
        Date registerDate = Date.valueOf(sc.nextLine());
        
        Person person = new Person(idNumber, name, surname, gender);
        person.setStudent(new Student(studentNum, idNumber, registerDate));
        
        return person; 
    }

    private static Long getIdNumber() 
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the ID Number of a person you want to [delete/get]\t:");
        
        return sc.nextLong();
    }

    private static Student getStudent()
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nUpdate Only Student"+
                           "\n--------------------\n");
        
        System.out.print("Enter the student Number\t\t:");
        Long studentNum = sc.nextLong();
        
        sc.nextLine();
        
        System.out.print("Enter your registration date[yyyy-mm-dd]:");
        Date registerDate = Date.valueOf(sc.nextLine());
        
        Student student = new Student();
        student.setStudentNum(studentNum);
        student.setRegisterDate(registerDate);
        
        return student;
    }

    private static void displayPerson(Person person)
    {
        String outcome = "\nPerson"+
                         "\n-------\n"+
                         "ID Number\t:"+person.getIdNumber()+"\n"+
                         "Name\t\t:"+person.getName()+"\n"+
                         "Surname\t\t:"+person.getSurname()+"\n"+
                         "Gender\t\t:"+person.getGender()+"\n"+
                         "\nStudent"+
                         "\n-------\n"+
                         "Student No\t  :"+person.getStudent().getStudentNum()+"\n"+
                         "Tut Email\t  :"+person.getStudent().outlookEmail()+"\n"+
                         "Registration Date :"+person.getStudent().getRegisterDate()+"\n";
       
        System.out.println(outcome);
                
    }

    private static Long getStudentNumber() 
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the student number of the student you want to remove :");
        
        return  sc.nextLong();
    }

    private static void displayPeople(List<Person> thePeople) 
    {
        String outcome ="";
        for (Person display : thePeople)
        {
             outcome += "\nPerson"+
                         "\n-------\n"+
                         "ID Number\t:"+display.getIdNumber()+"\n"+
                         "Name\t\t:"+display.getName()+"\n"+
                         "Surname\t\t:"+display.getSurname()+"\n"+
                         "Gender\t\t:"+display.getGender()+"\n"+
                         "\nStudent"+
                         "\n-------\n"+
                         "Student No\t  :"+display.getStudent().getStudentNum()+"\n"+
                         "Tut Email\t  :"+display.getStudent().outlookEmail()+"\n"+
                         "Registration Date :"+display.getStudent().getRegisterDate()+"\n";
        }
        
        System.out.println(outcome);
    }
    
}
