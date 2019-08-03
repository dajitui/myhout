package com.example.demo.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

@Configuration
public class MahoutConfig {

    private MysqlDataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("127.0.0.1");
        dataSource.setUser("root");
        dataSource.setPassword("ys123456");
        dataSource.setDatabaseName("test");
        try {
            dataSource.setServerTimezone("UTC");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(autowire = Autowire.BY_NAME, value = "mySQLDataModel")
    public DataModel getMySQLJDBCDataModel() {
        DataModel dataModel = new MySQLJDBCDataModel(getDataSource(), "test", "uid", "iid",
                "score", "ts");
        return dataModel;
    }

    @Bean(autowire = Autowire.BY_NAME, value = "fileDataModel")
    public DataModel getDataModel() throws IOException {
        URL url = MahoutConfig.class.getClassLoader().getResource("mahout/ratings-1m.data");
        //URL url = MahoutConfig.class.getClassLoader().getResource("mahout/dajitui.data");
        DataModel dataModel = new FileDataModel(new File(url.getFile()));
        return dataModel;
    }
}
