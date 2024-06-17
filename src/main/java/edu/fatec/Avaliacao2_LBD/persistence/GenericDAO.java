package edu.fatec.Avaliacao2_LBD.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class GenericDAO
{
    private Connection connection;

    public Connection getConnection() throws ClassNotFoundException, SQLException
    {
        String hostName = "localhost";
        String port = "1433";
        String dataBaseName = "Avaliacao_2_Lab_BD";
        String user = "Usuario";
        String passwd = "USU4rio@123";

        Class.forName("net.sourceforge.jtds.jdbc.Driver"); // driver
        connection = DriverManager.getConnection(String.format(
                "jdbc:jtds:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s",
                hostName,
                port,
                dataBaseName,
                user,
                passwd
        ));

        return connection;
    }
}
