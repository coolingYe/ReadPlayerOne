package com.zee.club.home.data.model;

import java.io.Serializable;

public class ActivityAppMo implements Serializable {
    private String name;
    private String softwareCode;

    public ActivityAppMo(String name, String softwareCode) {
        this.name = name;
        this.softwareCode = softwareCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoftwareCode() {
        return softwareCode;
    }

    public void setSoftwareCode(String softwareCode) {
        this.softwareCode = softwareCode;
    }
}
