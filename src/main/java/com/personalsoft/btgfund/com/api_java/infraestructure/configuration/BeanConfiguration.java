package com.personalsoft.btgfund.com.api_java.infraestructure.configuration;


import com.personalsoft.btgfund.com.api_java.domain.ports.input.IUserService;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IPasswordAdapter;
import com.personalsoft.btgfund.com.api_java.domain.ports.output.IUserAdapter;
import com.personalsoft.btgfund.com.api_java.domain.usecase.UsersUseCase;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.mapper.UsersEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {


    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;


    @Bean
    public DynamoDbClient dynamoDbClient() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
        return DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }



    @Bean
    IUserService usersUseCase(IUserAdapter userAdapter, IPasswordAdapter passwordAdapter) {
        return new UsersUseCase(passwordAdapter, userAdapter);
    }



}
