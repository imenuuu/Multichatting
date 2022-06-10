package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://chatting.cx3635ohpt8d.ap-northeast-2.rds.amazonaws.com/ChattingDB",
                "root","chat1234");
    }
}
