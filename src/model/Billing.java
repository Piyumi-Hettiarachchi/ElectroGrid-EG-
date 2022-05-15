package model;
import java.sql.*;
import java.util.Date;

public class Billing 
{
	//A common method to connect to the DB
	private Connection connect() 
	 { 
	 Connection con = null; 
	 try
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 
	 
	 //Provide the correct details: DBServer/DBName, username, password 
	 
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid-db", "root", ""); 
	 } 
	 catch (Exception e) 
	 {e.printStackTrace();} 
	 return con; 
	 } 
	
	
	
	//Insert Function
	// since manager insert only account Number, month and date
	//amount should be Calculated
	//Units will be get by GetUnit Method
	
	
	public String insertBillDetails(String accountNo, String month,String date) 
	 {

	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for inserting."; } 
	 
	 int units = Integer.parseInt(GetUnits(accountNo,month));
	
	 
	 //Calculate Amount
	 
	 Double amount = 0.0;
	 
	if (units <= 30){
		amount = 30 + units * 2.5;
	}
	else if (units <=60) {
		amount = 60 + units * 4.85;
	}
	else if (units <=90) {
		amount = 90 + units * 8.25;
	}
	else if (units <=120) {
		amount = 480 + units * 13.00;
	}
	else if (units <=180) {
		amount = 480 + units * 18.00;
	}
	else {
		amount = 540 + units * 23.50;
	}

	
	 // create a prepared statement
	 
	 String query = " insert into billing(`Bill_ID`,`AccountNo`,`Month`,`Units`,`Amount`,`Date`)"
	 + " values (?, ?, ?, ?, ?, ?)"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	 
	 // binding values
	 
	 preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(2, accountNo); 
	 preparedStmt.setString(3, month); 
	 preparedStmt.setInt(4, units); 
	 preparedStmt.setDouble(5,amount);
	 preparedStmt.setString(6,date);
	 
	 
	 // execute the statement
	 
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Inserted successfully"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while inserting the Bill Details."+e; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	
	// GetUnits Method for get the Units consumed by customer from Units Record table
	
	 public String GetUnits(String accountNo, String month) {
		 String output="";
		 
		 try {
				 Connection con = connect(); 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for reading."; 
				 }
				 
				 
				 String query = "SELECT consumedUnits FROM unit_records WHERE accountNo= '"+accountNo+"' AND Month ='"+month+"'" ; 
				 
				 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 
				 
				 while (rs.next()) 
				 { 
				  output = Integer.toString(rs.getInt("consumedUnits"));
				  
				 }
				 System.out.println("hello"+output);
			 }
		 
		 catch(Exception ex) {
			 System.out.println("hello"+ex);
		 }
		 return output;
	 }
	
	 
	 // Read Function
	 
	public String readBillDetails() 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for reading."; } 
	 
	 
	 // Prepare the html table to be displayed
	 
	 output = "<table border='1'><tr><th>Bill No</th><th>Account No</th><th>Month</th>" +
	 "<th>Units</th>" + 
	 "<th>Amount</th>" +
	 "<th>Date</th>"+
	 "<th>Update</th><th>Delete</th></tr>"; 
	 
	 String query = "select * from billing"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 
	 
	 // iterate through the rows in the result set
	 
	 while (rs.next()) 
	 { 
	 String Bill_ID = Integer.toString(rs.getInt("Bill_ID")); 
	 String AccountNo = rs.getString("AccountNo"); 
	 String Month = rs.getString("Month"); 
	 String Units = Integer.toString(rs.getInt("Units")); 
	 String Amount = Double.toString(rs.getDouble("Amount")); 
	 String Date = rs.getString("Date");
	 
	 // Add into the html table
	 
	 output += "<tr><td>" + Bill_ID + "</td>";
	 output += "<td>" + AccountNo + "</td>"; 
	 output += "<td>" + Month + "</td>"; 
	 output += "<td>" + Units + "</td>"; 
	 output += "<td>" + Amount + "</td>"; 
	 output += "<td>" + Date + "</td>";
	 
	 // buttons
	 
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
	 + "<input name='Bill_ID' type='hidden' value='" + Bill_ID 
	 + "'>" + "</form></td></tr>"; 
	 } 
	 con.close(); 
	 
	 
	 // Complete the html table
	 
	 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while reading the items."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	
	
	//Update Function
	
	public String updateBillDetails(String billno, String date) 
	 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for updating."; } 
	 
	 
	 // create a prepared statement
	 
	 String query = "UPDATE billing SET Date=? WHERE Bill_ID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	 // binding values
	 
//	 preparedStmt.setString(1, accountNo); 
//	 preparedStmt.setString(2, month); 
//	 preparedStmt.setInt(3, Integer.parseInt(units)); 
//	 preparedStmt.setString(4, amount); 
	 preparedStmt.setInt(2, Integer.parseInt(billno)); 
	 preparedStmt.setString(1, date); 
	 
	 System.out.println(billno);
	 System.out.println(date);
	  
	 // execute the statement
	 
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Updated successfully"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while updating the Bill."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	
	// Delete Function
	
	public String deleteBillDetails(String Bill_ID) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for deleting."; } 
	 
	 // create a prepared statement
	 
	 String query = "delete from billing where Bill_ID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	 // binding values
	 
	 preparedStmt.setInt(1, Integer.parseInt(Bill_ID)); 
	 
	 // execute the statement
	 
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Deleted successfully"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while deleting the Bill."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	
	// Search Function
	
public String searchBills(String Bill_ID) {
		
		String output="";
		try{ 
			Connection con = connect(); 
			if (con == null)  {
				return "Error while connecting to the database";
				} 
				
				output = "<html>"
						+"<table border='1'><tr><th>Bill_ID</th>"+
						"<th>AccountNo</th>"+
						"<th>Month</th>" +
						"<th>Units</th>" +
						"<th>Amount</th>" +
						"<th>Date</th></tr>" ; 

			// create a prepared statement
			String query = "select * from billing where Bill_ID='"+Bill_ID+"'"; 
			Statement stmt = con.createStatement(); 
		 	ResultSet rs = stmt.executeQuery(query);
		 	while(rs.next()) {
		 		
		 		//String Bill_ID = rs.getString("Bill_ID"); 
				String AccountNo = rs.getString("AccountNo");
				String Month= rs.getString("Month"); 
				String Units = rs.getString("Units");
				String Amount = rs.getString("Amount");
				String Date = rs.getString("Date");
			
				
				output += "<td>" +  Bill_ID+ "</td>"; 
 				output += "<td>" + AccountNo + "</td>"; 
 				output += "<td>" + Month + "</td>"; 
 				output += "<td>" + Units + "</td>"; 
 				output += "<td>" + Amount + "</td>"; 
 				output += "<td>" + Date + "</td>"; 
 				 

				
		 	}
			
			con.close(); 
	 
				output += "</table>"; 

		} 
		catch (Exception e) { 
			output = "Error while searching";  
			System.err.println(e.getMessage()); 
		} 
		return output;
	

}

}
