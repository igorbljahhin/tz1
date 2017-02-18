package tz1.task;

import tz1.Constants;
import tz1.domain.LogRecord;
import tz1.repository.LogRecordRepository;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Queue;

public class LogRecordConsumer implements Runnable {
    private final Queue<LogRecord> queue;
    private final LogRecordRepository repository;
    private final DateFormat dateFormat;

    public LogRecordConsumer(final Queue<LogRecord> queue, final LogRecordRepository repository) {
        this.queue = queue;
        this.repository = repository;
        this.dateFormat = new SimpleDateFormat(Constants.TIMESTAMP_FORMAT);
    }

    private void log(final LogRecord obj) {
        System.out.println("<<< " + dateFormat.format(obj.getCreatedAt()));
    }

    @Override
    public void run() {
        while (!queue.isEmpty()) {
            final LogRecord obj = queue.peek();

            try {
                repository.save(obj);

                queue.poll();

                log(obj);
            } catch (SQLException e) {
                System.err.println("Database connection lost (underlying error message is \"" + e.getMessage() + "\". Retrying...");

                return;
            }
        }
    }
}
