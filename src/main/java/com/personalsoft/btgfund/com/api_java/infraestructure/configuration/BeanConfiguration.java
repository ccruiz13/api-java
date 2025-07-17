package com.personalsoft.btgfund.com.api_java.infraestructure.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.adapter.UserAdapter;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.mapper.UsersEntityMapper;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.repositories.UsersRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final UsersEntityMapper entityMapper;
    private final UsersRepositories usersRepositories;

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKey, secretKey)
                ))
                .build();
    }

    @Bean
    DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }

    @Bean
    IUserAdapter userAdapter(){
        return new UserAdapter(entityMapper, usersRepositories);
    }
}
