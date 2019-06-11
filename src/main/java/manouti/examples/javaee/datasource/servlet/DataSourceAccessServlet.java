package manouti.examples.javaee.datasource.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * A sample servlet that retrieves a DataSource.
 */
@WebServlet(name="sample", urlPatterns = "/dbinfo")
public class DataSourceAccessServlet extends HttpServlet {

	private static final long serialVersionUID = 3360567384020493303L;

	@Resource(name="java/myapp/jdbc_ds")
	DataSource dataSource;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataSourceAccessServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try(Connection connection = dataSource.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			PrintWriter writer = response.getWriter();
			writer.append("DB info: ")
			      .append(databaseMetaData.getDatabaseProductName())
			      .append("\nCatalog name: ")
			      .append(connection.getCatalog());
			try(ResultSet tables = databaseMetaData.getTables(connection.getCatalog(), null, null, new String[] { "TABLE" })) {
				while(tables.next()) {
					writer.append("\n" + tables.getString("TABLE_NAME"));
				}
			}
		} catch(SQLException ex) {
			response.getWriter().append("Error " + ex.getMessage());
			getServletContext().log("Error connecting to DB", ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
