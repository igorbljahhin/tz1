package tz1.domain;

import java.util.Date;

public class LogRecord {
    private Date timestamp;

    public Date getCreatedAt() {
        return timestamp;
    }

    public void setCreatedAt(Date timestamp) {
        this.timestamp = timestamp;
    }
}
