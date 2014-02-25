import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Prepared {

	public static void selectAll(Statement stmt) throws SQLException {

		ResultSet rs = stmt.executeQuery("SELECT * FROM table_car");

		while (rs.next()) {

			String model = rs.getString("model");
			String engine = rs.getString("engine");
			String garage = rs.getString("garage");			

			System.out.println(model + "  " + engine + "  " + garage);

		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		Connection con = DriverManager.getConnection("jdbc:derby:wombat;create=true");
		
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		stmt.execute("CREATE TABLE table_car (model VARCHAR(45), engine VARCHAR(30), garage VARCHAR(30))");
		stmt.execute("INSERT INTO table_car VALUES ('Opel', 1800, 'In_Sofia')");
		stmt.execute("INSERT INTO table_car VALUES ('VOLVO', 1700, 'In_Pirdop')");
		stmt.execute("INSERT INTO table_car VALUES ('BMW', 2800, 'In_Varna')");
		
		selectAll(stmt);

		PreparedStatement updateModel = con.prepareStatement("UPDATE table_car SET model = ? " + "WHERE garage LIKE ?");
		updateModel.setString(1, "Opel_Astra"); 
		updateModel.setString(2, "In_Pirdop"); 

		int res = updateModel.executeUpdate();
		if(res == 1) {

			System.out.println(" Update rows ");

		}
	
		selectAll(stmt);
	}
}
