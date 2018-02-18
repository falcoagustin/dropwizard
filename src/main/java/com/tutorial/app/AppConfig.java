package com.tutorial.app;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppConfig extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database;

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
    
    public void setDataSourceFactory(DataSourceFactory database) {
        this.database = database;
    }
}