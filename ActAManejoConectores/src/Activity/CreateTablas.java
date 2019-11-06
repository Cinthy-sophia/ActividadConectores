package Activity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTablas {
	
	public void createTable(String conexion, String createTable) {
		conexion = "jdbc:sqlite:clientes.db";
		Connection c;
		
		try {
			c= DriverManager.getConnection(conexion);
			if(c!=null) {
				Statement s= c.createStatement();
				
				String createSQL="CREATE TABLE clientes(dni char(9) primary key, nom varchar(20), edad int);";
				System.out.println("Creando tabla...");
				s.execute(createSQL);
				//insertar(s);
				//consultar(s);
				s.close();
				c.close();
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		};
		
	}

}
