package com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.repositories;

import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Iterator;

@Repository
@RequiredArgsConstructor
public class UsersRepositories {


    private final DynamoDbClient dynamoDbClient;

    @Value("${aws.dynamodb.table-name}")
    private String tableName;

    public UserEntity findByEmail(String email) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        DynamoDbTable<UserEntity> usersTable = enhancedClient
                .table(tableName, TableSchema.fromBean(UserEntity.class));

        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                .filterExpression(Expression.builder()
                        .expression("email = :email")
                        .putExpressionValue(":email", AttributeValue.builder().s(email).build())
                        .build())
                .build();

        Iterator<UserEntity> results = usersTable.scan(scanRequest).items().iterator();
        return results.hasNext() ? results.next() : null;
    }

    public UserEntity save(UserEntity user) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        DynamoDbTable<UserEntity> usersTable = enhancedClient
                .table(tableName, TableSchema.fromBean(UserEntity.class));

        usersTable.putItem(user);

        return usersTable.getItem(r -> r.key(k -> k.partitionValue(user.getUserId())));
    }
}
