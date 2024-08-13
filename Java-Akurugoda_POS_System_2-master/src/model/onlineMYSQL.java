package model;

import java.io.FileReader;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class onlineMYSQL {

    private static Connection connection;

    static {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String userDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();

            String url = userDirectory + "\\lib\\databs.json";

            Object obj = new JSONParser().parse(new FileReader(url));
            JSONObject j = (JSONObject) obj;
            
            String dburl = String.valueOf(j.get("o_url"));
            String port = String.valueOf(j.get("o_port"));
            String reconnect = String.valueOf(j.get("o_reconnect"));
            String db = String.valueOf(j.get("o_databaseName"));
            String username = String.valueOf(j.get("o_username"));
            String password = String.valueOf(j.get("o_password"));

            connection = DriverManager.getConnection("jdbc:mysql://" + dburl + ":" + port + "/" + db + reconnect, username, password);

        } catch (Exception e) {
        }

    }

    public static ResultSet execute(String query) throws Exception {

        Statement statement = connection.createStatement();
        if (query.startsWith("SELECT")) {
            ResultSet r = statement.executeQuery(query);
            return r;
        } else {
            int result = statement.executeUpdate(query);
            return null;

        }

    }

}
