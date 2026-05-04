package edu.jiangbaiyu.demo.springboot.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 绑定嵌套对象
 */
@Component
@ConfigurationProperties(prefix = "nested")
public class AppNestedProperties {
    private Database database;

    public static class Database {
        private String host;
        private int port;
        private Credentials credentials;

        // getter/setter 内部类...
        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        @Override
        public String toString() {
            return "Database{host='" + host + "', port=" + port + ", credentials=" + credentials + '}';
        }
    }

    public static class Credentials {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Credentials{username='" + username + "'}"; // 不输出密码
        }
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "NestedProperties{database=" + database + '}';
    }
}
