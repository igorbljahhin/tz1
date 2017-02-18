package tz1.task;

import tz1.Constants;
import tz1.domain.LogRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;

public class LogRecordProducer implements Runnable {
    private final Queue<LogRecord> queue;
    private final DateFormat dateFormat;

    public LogRecordProducer(final Queue<LogRecord> queue) {
        this.queue = queue;
        this.dateFormat = new SimpleDateFormat(Constants.TIMESTAMP_FORMAT);
    }

    private void log(final LogRecord obj) {
        System.out.println(">>> " + dateFormat.format(obj.getCreatedAt()));
    }

    @Override
    public void run() {
        final LogRecord obj = new LogRecord();
        obj.setCreatedAt(new Date());

        queue.add(obj);

        log(obj);
    }
}
