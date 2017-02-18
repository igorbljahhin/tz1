package tz1.repository;

import tz1.domain.LogRecord;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogRecordRepository {
    private static final String SQL_SELECT_FIND_ALL = "SELECT id, created_at FROM log_record";
    private static final String SQL_INSERT = "INSERT INTO log_record (created_at) VALUES (?)";

    private final DataSource dataSource;

    public LogRecordRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<LogRecord> findAll() throws SQLException {
        final List<LogRecord> list = new ArrayList<>();

        try (final Connection connection = dataSource.getConnection()) {
            try (final Statement stmt = connection.createStatement()) {
                try (final ResultSet rs = stmt.executeQuery(SQL_SELECT_FIND_ALL)) {
                    while (rs.next()) {
                        final LogRecord obj = new LogRecord();
                        obj.setCreatedAt(rs.getTimestamp(2));

                        list.add(obj);
                    }
                }
            }
        }

        return list;
    }

    public void save(final LogRecord obj) throws SQLException {
        try (final Connection connection = dataSource.getConnection()) {
            try (final PreparedStatement pstmt = connection.prepareStatement(SQL_INSERT)) {
                pstmt.setTimestamp(1, new Timestamp(obj.getCreatedAt().getTime()));
                pstmt.executeUpdate();
            }
        }
    }
}
