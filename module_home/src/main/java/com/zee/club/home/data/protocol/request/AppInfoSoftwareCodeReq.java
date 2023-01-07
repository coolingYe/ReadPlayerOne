package com.zee.club.home.data.protocol.request;

import java.util.List;

public class AppInfoSoftwareCodeReq {
    public List<String> softwareCodes;

    public AppInfoSoftwareCodeReq(List<String> softwareCodes) {
        this.softwareCodes = softwareCodes;
    }
}
