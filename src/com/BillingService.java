package com;
import model.Billing;


// For REST service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;  


//For JSON
import com.google.gson.*; 

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;



@Path("/Billing")

public class BillingService {

	
	// Read Data 
	
	Billing BillObj = new Billing(); 
	@GET
	@Path("/readBill") 
	@Produces(MediaType.TEXT_HTML) 
	public String readBillDetails() 
	 { 
	 return BillObj.readBillDetails(); 
	 } 
	
	
	//Insert Data
	
	@POST
	@Path("/insertBill") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertBillDetails(
									
									@FormParam("AccountNo") String AccountNo, 
									@FormParam("Month") String Month, 
									@FormParam("Date") String Date)
	
	
	
	{ 
		//inserting validations
				
					if(AccountNo.isEmpty()||Month.isEmpty()||Date.isEmpty())
					{
						 return "All the Bill Details must be filled out";
					}
				
					else if(Month.length()>15) {
						 return "Invalide Value Inserted";
					 }
					else if (Date.length()!=10) {
						return "Invalid format";
					}
					else if(AccountNo.length()!=6) {
						return "Account Number should consist of 6 characters";
					}
			 
	String output = BillObj.insertBillDetails(AccountNo, Month, Date); 
	return output; 
	}
	
	  
	//Update Function
	
	@PUT
	@Path("/updateBill") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateBillDetails(String billingDatata) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject BillObj1 = new JsonParser().parse(billingDatata).getAsJsonObject(); 
	//Read the values from the JSON object'
	 String Bill_ID = BillObj1.get("Bill_ID").getAsString(); 
//	 String AccountNo = BillObj1.get("AccountNo").getAsString(); 
//	 String Month = BillObj1.get("Month").getAsString(); 
//	 String Units = BillObj1.get("Units").getAsString(); 
//	 String Amount = BillObj1.get("Amount").getAsString(); 
	 String Date = BillObj1.get("Date").getAsString(); 
	 
	 String output = BillObj.updateBillDetails(Bill_ID,Date);
	 
	return output; 
	}
	
	// Delete Bill Details

	@DELETE
	@Path("/deleteBill")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String deleteBillDetails(String billingDatata)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse( billingDatata, "", Parser.xmlParser());

	//Read the value from the element 
	 String Bill_ID = doc.select("Bill_ID").text();
	 
	 String output = BillObj.deleteBillDetails(Bill_ID);
	return output;
	}

	
	// Search Bills
	
	@GET
	@Path("/searchBill")
	@Produces(MediaType.TEXT_HTML)
	public String searchBills(String billingDatata) {
		Document doc = Jsoup.parse(billingDatata, "", Parser.xmlParser()); 
		String Bill_ID = doc.select("Bill_ID").text(); 
		
		return BillObj.searchBills(Bill_ID);
	}
}
