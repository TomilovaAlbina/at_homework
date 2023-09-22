package com.pflb.education.atexample.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {
    public final String env;

    public final String baseUrl;

    public final String apiUrl;

    public final String login;

    public final String password;

    private static final String ENV_NAME = "ACTIVE_ENVIRONMENT";
    public ApplicationConfig() {
        String getenv = System.getenv(ENV_NAME);
        if(getenv == null) env = "default";
        else env = getenv;
        Properties properties = parseProperties();
        this.baseUrl = properties.getProperty("base.url");
        this.apiUrl = properties.getProperty("api.url");
        this.login = properties.getProperty("user.login");
        this.password = properties.getProperty("user.password");
  }

    private Properties parseProperties() {
        String filename = env + ".properties";
        try(
            InputStream inputStream = getClass().getClassLoader().getResource(filename)
            .openStream();
        ) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e){
            throw new RuntimeException();
        }
    }
}
