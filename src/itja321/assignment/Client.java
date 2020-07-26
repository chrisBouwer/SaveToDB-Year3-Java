/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itja321.assignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

/**
 *
 * @author Christiaan Bouwer - VVF6HCS19
 */

public class Client implements ActionListener{
    JPanel panel;
    JTextField studentIDTf;
    JTextField nameTf;
    JTextField surnameTf;
    JTextField contactTf;
    JTextField addressTf;
        
    public void createForm() { //creation of the GUI
        JFrame frame = new JFrame("Student details");
        panel = new JPanel();
        JLabel studentIDLbl = new JLabel("Student ID");
        JLabel nameLbl = new JLabel("First Name");
        JLabel surnameLbl = new JLabel("Last Name");
        JLabel contactLbl = new JLabel("Contact Number");
        JLabel addressIDLbl = new JLabel("Address");
        JLabel empty = new JLabel("");
        JTextField studentIDTf = new JTextField();
        JTextField nameTf = new JTextField();
        JTextField surnameTf = new JTextField();
        JTextField contactTf = new JTextField();
        JTextField addressTf = new JTextField();
        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(this);
        GridLayout gird = new GridLayout(6,2);
        
        panel.setLayout(gird);
        panel.add(studentIDLbl);
        panel.add(studentIDTf);
        panel.add(nameLbl);
        panel.add(nameTf);
        panel.add(surnameLbl);
        panel.add(surnameTf);
        panel.add(contactLbl);
        panel.add(contactTf);
        panel.add(addressIDLbl);
        panel.add(addressTf);
        panel.add(empty);
        panel.add(registerBtn);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) { //Stream the student object to and from the server
        StudentDetails studentObj = new StudentDetails(studentIDTf.getText(), nameTf.getText(), surnameTf.getText(), contactTf.getText(), addressTf.getText());
        try {
            Socket socketObj = new Socket("localhost", 5252);
            ObjectOutputStream outputObj = new ObjectOutputStream(socketObj.getOutputStream());
            outputObj.writeObject(studentObj);
            ObjectInputStream inputObj = new ObjectInputStream(socketObj.getInputStream());
            String text = (String) inputObj.readObject();
            JOptionPane.showMessageDialog(panel, text, "localhost", JOptionPane.PLAIN_MESSAGE);
            socketObj.close();
        } catch (HeadlessException | IOException | ClassNotFoundException exc) {
            System.out.println("An error has occured: "+exc);
        }
    }
}
