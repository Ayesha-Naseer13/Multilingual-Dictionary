package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import dto.DictionaryEntry;

public class CsvDatabaseLayer {

    private static final String URL = "jdbc:mysql://localhost:3306/dictionary";
    private static final String USER = "user_name";
    private static final String PASSWORD = "user_password";

    public void saveEntries(List<DictionaryEntry> entries) throws SQLException {
        String wordInsertSQL = "INSERT INTO words (word) VALUES (?)";
        String translationInsertSQL = "INSERT INTO translations (word_id, language, translation) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement wordStmt = conn.prepareStatement(wordInsertSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement translationStmt = conn.prepareStatement(translationInsertSQL)) {

            for (DictionaryEntry entry : entries) {
                // Insert word into 'words' table
                wordStmt.setString(1, entry.getWord());
                wordStmt.executeUpdate();

                // Get the generated word ID
                try (var rs = wordStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int wordId = rs.getInt(1);

                        // Insert each translation into 'translations' table
                        for (Map.Entry<String, String> translation : entry.getTranslations().entrySet()) {
                            translationStmt.setInt(1, wordId);
                            translationStmt.setString(2, translation.getKey());
                            translationStmt.setString(3, translation.getValue());
                            translationStmt.addBatch();
                        }
                    }
                }
            }

            // Execute batch insert for translations
            translationStmt.executeBatch();
            System.out.println("All entries successfully saved to the database.");
        }
    }
}
