package server;

import server.http.HttpRequest;

public class Request {

    private HttpRequest httpRequest;

    private boolean isHttp;

    private String monitorRequest;


    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public boolean getIsHttp() {
        return isHttp;
    }

    public void setIsHttp(boolean http) {
        isHttp = http;
    }

    public String getMonitorRequest() {
        return monitorRequest;
    }

    public void setMonitorRequest(String monitorRequest) {
        this.monitorRequest = monitorRequest;
    }
}
