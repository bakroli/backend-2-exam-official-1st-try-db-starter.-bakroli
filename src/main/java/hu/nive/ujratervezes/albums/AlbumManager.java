package hu.nive.ujratervezes.albums;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumManager {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public AlbumManager(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public List<String> getSingersWithTheirLatestAlbum() {
        List<String> result = new ArrayList<>();
        String sqlQuery = "SELECT DISTINCT singer_mame, release_year, singer_name || ' - ' || release_year FROM singers RIGHT JOIN albums ON singers.singer_id = albums.singer_id ORDER BY release_year DESC";

        try (Connection connection = DriverManager.getConnection(dbUrl,dbUser,dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            result = getResult(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(result);

    }

    private static List<String> getResult(ResultSet resultSet) throws SQLException {
        List<String> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(resultSet.getString(2));
        }
        return result;
    }


}
