package com.pack.server;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.pack.model.Employee;

public class MyHandler  extends DefaultHandler {
	
	
	// List to hold Employees object
		private List<Employee> empList = null;
		private Employee emp = null;
		private StringBuilder data = null;

		// getter method for employee list
		public List<Employee> getEmpList() {
			return empList;
		}

		boolean bNumero = false;
		boolean bNom = false;
		boolean bAdresse = false;
		boolean bSalaire = false;

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

			if (qName.equalsIgnoreCase("Employee")) {
				// create a new Employee and put it in Map
				//String id = attributes.getValue("id");
				// initialize Employee object and set id attribute
				emp = new Employee();
				//emp.setId(Integer.parseInt(id));
				// initialize list
				if (empList == null)
					empList = new ArrayList<>();
			} else if (qName.equalsIgnoreCase("numero")) {
				// set boolean values for fields, will be used in setting Employee variables
				bNumero = true;
			} else if (qName.equalsIgnoreCase("nom")) {
				bNom = true;
			} else if (qName.equalsIgnoreCase("adresse")) {
				bAdresse = true;
			} else if (qName.equalsIgnoreCase("salaire")) {
				bSalaire = true;
			}
			// create the data container
			data = new StringBuilder();
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (bNumero) {
				// age element, set Employee age
				emp.setNumero(data.toString());
				bNumero = false;
			} else if (bNom) {
				emp.setNom(data.toString());
				bNom = false;
			} else if (bAdresse) {
				emp.setAdresse(data.toString());
				bAdresse = false;
			} else if (bSalaire) {
				emp.setSalaire(Float.parseFloat(data.toString()));
				bSalaire = false;
			}
			
			if (qName.equalsIgnoreCase("Employee")) {
				// add Employee object to list
				empList.add(emp);
			}
		}

		@Override
		public void characters(char ch[], int start, int length) throws SAXException {
			data.append(new String(ch, start, length));
		}

}

