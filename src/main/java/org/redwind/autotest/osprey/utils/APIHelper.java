package org.redwind.autotest.osprey.utils;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class APIHelper {

    @Autowired
    DriverFactory driverFactory;
    private APIRequestContext request;


    public APIResponse get(String url, RequestOptions params) {
        return request.get(url, params);
    }

    public APIResponse post(String url, RequestOptions params) {
        request = driverFactory.getAPIRequest();
        return request.post(url, params);
    }

    public APIResponse patch(String url, RequestOptions params) {
        return request.patch(url, params);
    }

    public APIResponse delete(String url, RequestOptions params) {
        return request.delete(url, params);
    }

}
