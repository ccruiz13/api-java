package com.personalsoft.btgfund.com.api_java.infraestructure.entrypoint.lambda;

import com.amazonaws.serverless.proxy.internal.testutils.Timer;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.personalsoft.btgfund.com.api_java.ApiJavaApplication;

public class MainHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            Timer.start("SpringBootInitialization");
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(ApiJavaApplication.class);
            Timer.stop("SpringBootInitialization");
        } catch (Exception e) {
            throw new RuntimeException("Error initializing Spring Boot application", e);
        }
    }

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest input, Context context) {
        return handler.proxy(input, context);
    }
}