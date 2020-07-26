/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itja321.assignment;

import java.io.ObjectInputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Christiaan Bouwer VVF6HCS19
 */
public class Server {
    ServerSocket servSocket;
    int clientNumb = 0;    

    public Server() {
        try {
            servSocket = new ServerSocket(5252);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        while (true) {            
            try {
                Socket socketObj = servSocket.accept();
                Thread threadObj = new Thread(new Runnable(){
                    public void run(){
                        clientNumb++;
                        try {
                            try {
                                ObjectInputStream inputObj = new ObjectInputStream(socketObj.getInputStream());
                                StudentDetails studentObj = (StudentDetails) inputObj.readObject();
                            } catch (Exception exc) {
                                exc.printStackTrace();
                            }
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                    }                    
                });
                threadObj.start();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
    
    public static void saveToDatabase(StudentDetails studentObj) {
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           System.out.println("Driver has been loaded");
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost/PIHE2019", "root", "");
           System.out.println("Connected to the DB");
           String query = ("INSERT INTO 'details'('StudentID', 'Name', 'Surname', 'Contact', 'Address') 'VALUES'"+ "(?,?,?,?,?);");
           PreparedStatement statmentObj = con.prepareStatement(query);
           statmentObj.setString(1, studentObj.getStudentID());
           statmentObj.setString(2, studentObj.getName());
           statmentObj.setString(3, studentObj.getSurname());
           statmentObj.setString(4, studentObj.getContact());
           statmentObj.setString(5, studentObj.getAddress());
           statmentObj.execute();
           statmentObj.close();
           con.close();
        } catch (Exception exc) {
            System.out.println("An error has occured: "+exc);
        }
    }
    
}
