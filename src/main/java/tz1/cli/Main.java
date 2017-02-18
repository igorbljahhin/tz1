package tz1.cli;

import com.mysql.cj.jdbc.MysqlDataSource;
import tz1.repository.LogRecordRepository;
import tz1.service.LogRecordService;

import javax.sql.DataSource;

public class Main {
    private LogRecordService logRecordService;

    public Main() {
        this.logRecordService = new LogRecordService(new LogRecordRepository(createDataSource()));
    }

    public static void main(String[] args) {
        final Main app = new Main();

        if (args.length == 0) {
            app.generateData();
        } else if ("-p".equals(args[0])) {
            app.printAllData();
        } else {
            System.err.println("The app parameter \"" + args[0] + "\" is not supported.");
            System.err.println();
            System.err.println("Run app either without parameters for data generation or with \"-p\" parameter for data display.");

            System.exit(-1);
        }
    }

    private void printAllData() {
        logRecordService.printAllData(System.out);
    }

    private void generateData() {
        logRecordService.generateData();
    }

    private DataSource createDataSource() {
        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("tz1");
        dataSource.setPassword("tz1");
        dataSource.setDatabaseName("tz1");
        dataSource.setServerName("localhost");

        return dataSource;
    }
}
