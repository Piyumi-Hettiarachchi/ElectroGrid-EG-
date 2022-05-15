<%@page import="model.Billing"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
if (request.getParameter("AccountNo") != null) 
{ 
	Billing projectObj = new Billing(); 
 String stsMsg = ""; 
//Insert--------------------------
if (request.getParameter("hidProjectIDSave") == "") 
 { 
 stsMsg = projectObj.insertBillDetails(request.getParameter("AccountNo"), 
 request.getParameter("Month"), 
 request.getParameter("Date")
); 
 } 
else//Update----------------------
 { 
 stsMsg = projectObj.updateBillDetails(request.getParameter("hidProjectIDSave"), 
 request.getParameter("Date")
); 
 } 
 session.setAttribute("statusMsg", stsMsg); 
} 
//Delete-----------------------------
if (request.getParameter("hidItemIDDelete") != null) 
{ 
	Billing projectObj = new Billing(); 
 String stsMsg = projectObj.deleteBillDetails(request.getParameter("hidItemIDDelete")); 
 session.setAttribute("statusMsg", stsMsg); 
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.1.min.js"></script>
<script src="Components/BillManagement.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Bill Management</h1>

				<form id="formBillManagement" name="formBillManagement" method="post" action="BillManagement.jsp">


					<!-- ACCOUNT NO -->
					 <div class="input-group input-group-sm mb-3">
					 <div class="input-group-prepend">
					 <span class="input-group-text" id="lblName">Account No: </span>
					 </div>
					 <input type="text" id="AccountNo" name="AccountNo"></div>
										 
					 <!-- MONTH -->
					 <div class="input-group input-group-sm mb-3">
					 <div class="input-group-prepend">
					 <span class="input-group-text" id="lblName">Year: </span>
					 </div>
					 <select id="ddlYear" name="ddlYear">
					 <option value="0">--Select Month--</option>
					 <option value="1">January</option>
					 <option value="2">February</option>
					 <option value="3">March</option>
					 <option value="4">April</option>
					 <option value="5">May</option>
					 <option value="6">June</option>
					 <option value="7">July</option>
					 <option value="8">August</option>
					 <option value="9">September</option>
					 <option value="10">October</option>
					 <option value="11">November</option>
					 <option value="12">December</option>
					 </select>
					 </div>

					 <!-- DATE -->
					 <div class="input-group input-group-sm mb-3">
					 <div class="input-group-prepend">
					 <span class="input-group-text" id="lblName">Date </span>
					 </div>
					 <input type="text" id="Date" name="Date">
					 </div>
					 
					 <!-- UNITS -->
					 <div class="input-group input-group-sm mb-3">
					 <div class="input-group-prepend">
					 <span class="input-group-text" id="lblName">Consumed Units </span>
					 </div>
					 <input type="text" id="Units" name="Units">
					 </div>
					 
					 <!-- Amount -->
					 <div class="input-group input-group-sm mb-3">
					 <div class="input-group-prepend">
					 <span class="input-group-text" id="lblName">Amount </span>
					 </div>
					 <input type="text" id="Amount" name="Amount">
					 </div>
											
											
						<br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidProjectIDSave" name="hidProjectIDSave" value="Save">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divProjectGrid">
					<%
					Billing projectObj = new Billing();
						out.print(projectObj.readBillDetails());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
