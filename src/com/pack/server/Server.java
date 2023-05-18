package com.pack.server;

import java.io.*;

import java.net.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
import com.pack.model.Employee;
import java.sql.Connection;
import java.sql.Statement;

public class Server {

    private static ServerSocket server;
    private static int port = 1022;

    public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException {

        server = new ServerSocket(port);

        while (true) {

            System.out.println("En attente d'une requ�te...");
            Socket socket = server.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            String msg = (String) ois.readObject();

            Connection conn = null;
            Statement stmt = null;

            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/db", "root", "");
            System.out.println("Connection avec la base de donn�e r�ussie");

            if (msg.equals("list")) {

                stmt = (Statement) conn.createStatement();
                String query1 = "SELECT * FROM employee ";
                ResultSet result = stmt.executeQuery(query1);

                List<Employee> emp = new ArrayList<>();

                while (result.next()) {

                    String numero = result.getString("numero");
                    String nom = result.getString("nom");
                    String adresse = result.getString("adresse");
                    float salaire = result.getFloat("salaire");

                    Employee employee = new Employee();

                    employee.setNumero(numero);
                    employee.setNom(nom);
                    employee.setAdresse(adresse);
                    employee.setSalaire(salaire);

                    emp.add(employee);

                }

                oos.writeObject(emp);
            } else if (msg.equals("addFile.xml")) {

                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                MyHandler handler = new MyHandler();
                saxParser.parse(new File(msg), handler);
                //Get Employees list
                List<Employee> empList = handler.getEmpList();

                String theNumero = empList.get(0).getNumero();
                String theNom = empList.get(0).getNom();
                String theAdresse = empList.get(0).getAdresse();
                float theSalaire = empList.get(0).getSalaire();

                stmt = (Statement) conn.createStatement();
                String query1 = "INSERT INTO employee " + " VALUES ('" + theNumero + "','" + theNom + "','" + theAdresse + "'," + theSalaire + ")";
                stmt.executeUpdate(query1);
                oos.writeObject("Enregistrement reussi.....");
				      //System.out.println("L'employ�e a �t� ajout� avec succ�s.....");	 

            } else if (msg.equals("delFile.xml")) {

                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                MyHandler handler = new MyHandler();
                saxParser.parse(new File(msg), handler);
                //Get Employees list
                List<Employee> empList = handler.getEmpList();

                String theNumero = empList.get(0).getNumero();

                stmt = (Statement) conn.createStatement();
                String query = "DELETE FROM employee WHERE numero = '" + theNumero + "'";
                stmt.executeUpdate(query);
                oos.writeObject("Enregistrement supprime.....");

            } else if (msg.equals("getOneEmpFile.xml")) {

                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                MyHandler handler = new MyHandler();
                saxParser.parse(new File(msg), handler);
                //Get Employees list
                List<Employee> empList = handler.getEmpList();

                String theNumero = empList.get(0).getNumero();

                stmt = (Statement) conn.createStatement();
                String query1 = "SELECT * FROM employee WHERE numero = '" + theNumero + "'";
                ResultSet result = stmt.executeQuery(query1);

                List<Employee> emp = new ArrayList<>();

                while (result.next()) {

                    String numero = result.getString("numero");
                    String nom = result.getString("nom");
                    String adresse = result.getString("adresse");
                    float salaire = result.getFloat("salaire");

                    Employee employee = new Employee();
                    employee.setNumero(numero);
                    employee.setNom(nom);
                    employee.setAdresse(adresse);
                    employee.setSalaire(salaire);

                    emp.add(employee);

                }

                oos.writeObject(emp);

            } else if (msg.equals("editFile.xml")) {

                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                MyHandler handler = new MyHandler();
                saxParser.parse(new File(msg), handler);
                //Get Employees list
                List<Employee> empList = handler.getEmpList();

                String theNumero = empList.get(0).getNumero();
                String theNom = empList.get(0).getNom();
                String theAdresse = empList.get(0).getAdresse();
                float theSalaire = empList.get(0).getSalaire();

                stmt = (Statement) conn.createStatement();
                String query = "UPDATE employee SET nom = '" + theNom + "', adresse = '" + theAdresse + "', salaire = '" + theSalaire + "' WHERE numero = '" + theNumero + "'";
                stmt.executeUpdate(query);
                oos.writeObject("Enregistrement modifie.....");

            }

        }
				    //oos.close();

    }

}
