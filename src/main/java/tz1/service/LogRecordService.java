package tz1.service;

import tz1.Constants;
import tz1.domain.LogRecord;
import tz1.repository.LogRecordRepository;
import tz1.task.LogRecordConsumer;
import tz1.task.LogRecordProducer;

import java.io.PrintStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.defaultThreadFactory;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class LogRecordService {
    private final LogRecordRepository repository;
    private final DateFormat dateFormat;

    public LogRecordService(final LogRecordRepository repository) {
        this.repository = repository;
        this.dateFormat = new SimpleDateFormat(Constants.TIMESTAMP_FORMAT);
    }

    public void generateData() {
        final Queue<LogRecord> queue = new LinkedBlockingQueue<>();

        final LogRecordProducer producer = new LogRecordProducer(queue);
        newScheduledThreadPool(1, new ThreadFactoryWithCustomName(defaultThreadFactory(), "tz1-producer-thread"))
                .scheduleAtFixedRate(producer, 0, 1, TimeUnit.SECONDS);

        final LogRecordConsumer consumer = new LogRecordConsumer(queue, repository);
        newScheduledThreadPool(1, new ThreadFactoryWithCustomName(defaultThreadFactory(), "tz1-consumer-thread"))
                .scheduleAtFixedRate(consumer, 0, 5, TimeUnit.SECONDS);

    }

    public void print(final PrintStream out, final LogRecord obj) {
        out.println(dateFormat.format(obj.getCreatedAt()));
    }

    public void printAllData(final PrintStream out) {
        try {
            repository.findAll().forEach(logRecord -> print(out, logRecord));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to print all data", e);
        }
    }
}
