import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {

	public static void selectAll(Statement stmt) throws SQLException {ResultSet rs = stmt.executeQuery("SELECT * FROM table_car");

		while (rs.next()) {

			String brand = rs.getString("brand");
			int engine = rs.getInt("engine");
			String garage = rs.getString("garage");			

			System.out.println(brand + " " + engine + " " + garage);

		}

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		Connection con = DriverManager.getConnection("jdbc:derby:wombat;create=true");

		Statement stmt = con.createStatement();

		stmt.execute("CREATE TABLE tabl_car (name VARCHAR(45), age INT, birthplace VARCHAR(30))");

		stmt.execute("INSERT INTO table_car VALUES (Opel', 1800, 'In_Sofia')");
		stmt.execute("INSERT INTO table_car VALUES ('VOLVO', 1700, 'In_Pirdop')");
		stmt.execute("INSERT INTO table_car VALUES ('BMW', 2800, 'In_Varna')");

		stmt = con.createStatement();

		selectAll(stmt);

		stmt.execute("UPDATE table_car SET model='Vw' WHERE garage='In_Pernik'");
		stmt.execute("DELETE FROM table_car WHERE engine<18");

		System.out.println("   ");

		selectAll(stmt);

	}
}
