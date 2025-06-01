package org.example.util;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    private static final String URL = "jdbc:h2:./base_mascotas";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void inicializarBase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("DatosPruebas.sql");

            if (is == null) {
                System.err.println("No se encontró el archivo DatosPruebas.sql en resources.");
                return;
            }

            Path tempFile = Files.createTempFile("init", ".sql");
            Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
            stmt.execute("RUNSCRIPT FROM '" + tempFile.toAbsolutePath() + "'");
            System.out.println("Base de datos inicializada correctamente.");
        } catch (Exception e) {
            Log.error("Error al inicializar la base de datos.", e);
        }
    }
}
