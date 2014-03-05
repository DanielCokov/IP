import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction{

	public static void selectAll(Statement stmt) throws SQLException {

		ResultSet rs = stmt.executeQuery("SELECT * FROM table_car");

		while (rs.next()) {

			String model = rs.getString("model");
			int engine = rs.getInt("engine");
			String garage = rs.getString("garage");			

			System.out.println(model + " " + engine + " " + garage);
		
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		Connection con = DriverManager.getConnection("jdbc:derby:wombat;create=true");

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		stmt.execute("CREATE TABLE table_car (model VARCHAR(15), engine VARCHAR(5), garage VARCHAR(20))");

		stmt.execute("INSERT INTO table_car VALUES ('Opel', 1800, 'In_Sofia')");
		stmt.execute("INSERT INTO table_car VALUES ('VOLVO', 1700, 'In_Pirdop')");
		stmt.execute("INSERT INTO table_car VALUES ('BMW', 2800, 'In_Varna')");
		stmt.execute("INSERT INTO table_car VALUES ('Audi', 1900, 'In_Sofia')");

		selectAll(stmt);

		
		System.out.println("Transaction");

		con.setAutoCommit(false);

		PreparedStatement updateEngine = con.prepareStatement("UPDATE table_car SET engine = ? WHERE model LIKE ?");
		updateEngine.setInt(1, 1800);
		updateEngine.setString(2, "P%");
		updateEngine.executeUpdate();

		

		PreparedStatement updateGarage = con.prepareStatement("UPDATE table_car SET garage = ?");
		updateGarage.setString(1, "Plovdiv");
		updateGarage.executeUpdate();

		con.commit();
		con.setAutoCommit(true);

		selectAll(stmt);

	}
}
