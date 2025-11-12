package com.bank.util;

import org.h2.tools.RunScript;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;

public final class DbInit {
    // In-memory (recreated every run)
    public static final String JDBC_URL = "jdbc:h2:mem:bank;DB_CLOSE_DELAY=-1";
    // Or file-based:
    // public static final String JDBC_URL = "jdbc:h2:./data/bank;AUTO_SERVER=TRUE";

    public static final String USER = "sa";
    public static final String PASS = "";

    private DbInit() {}

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(JDBC_URL, USER, PASS);
    }

    /** Executes /db/schema.sql from the classpath once at app startup. */
    public static void runSchema() throws Exception {
        try (Connection c = getConnection()) {
            InputStream in = DbInit.class.getResourceAsStream("/db/schema.sql");
            if (in == null) {
                throw new IllegalStateException("schema.sql not found on classpath (/db/schema.sql)");
            }
            RunScript.execute(c, new InputStreamReader(in, StandardCharsets.UTF_8));
        }
    }
}
