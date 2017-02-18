# Test assignment #1
## Installation

In MySQL server execute the following script:

```
   CREATE DATABASE tz1 CHARACTER SET utf8 COLLATE utf8_bin;
   CREATE USER 'tz1'@'%' IDENTIFIED BY 'tz1';
   GRANT ALL PRIVILEGES ON tz1.* TO 'tz1'@'%' WITH GRANT OPTION;
   FLUSH PRIVILEGES;
   
   CREATE TABLE `tz1`.`log_record` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `created_at` DATETIME NOT NULL,
        PRIMARY KEY (`id`)
   );
```
## Running

### From IDE
Import the source into IDE as Maven project. Make sure that Maven dependency (MySQL driver) has been downloaded. 

Execute **ee.neotech.tz1.cli.Main** as Java application.

### From command line
Build the project using Maven command 

```
mvn clean package
```

Execute 

```
java -jar target/tz1.jar
```

to generate data or 

```
java -jar target/tz1.jar -p
```
to display data.
