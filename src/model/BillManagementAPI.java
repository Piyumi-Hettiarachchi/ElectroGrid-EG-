  package model;

  import com.BillingService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProjectAPI
 */
@WebServlet("/BillManagementAPI")
public class BillManagementAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Billing projectObj = new Billing();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillManagementAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String output = projectObj.insertBillDetails(request.getParameter("AccountNo"), 
				request.getParameter("Month"),
				request.getParameter("Date")
//				request.getParameter("Units"),
//				request.getParameter("Amount")
				);

		response.getWriter().write(output);

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map paras = getParasMap(request);

		String output = projectObj.updateBillDetails
				(paras.get("hidProjectIDSave").toString(),
				//paras.get("AccountNo").toString(), 
				paras.get("Date").toString());
				//paras.get("Month").toString(),
				//paras.get("Units").toString(),
				//paras.get("Amount").toString()
				
		response.getWriter().write(output);
	}


	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map paras = getParasMap(request);

		String output = projectObj.deleteBillDetails(paras.get("Bill_ID").toString());

		response.getWriter().write(output);
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();

			String[] params = queryString.split("&");
			for (String param : params) {

				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {

		}
		return map;

	}
}
