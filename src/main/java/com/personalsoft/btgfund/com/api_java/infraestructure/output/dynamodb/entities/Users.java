package com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "")
public class Users {

    private String userId;
    private String email;
    private String passwordHash;
    private String role;

    @DynamoDBHashKey(attributeName = "user_id")
    public String getUserId() {
        return userId;
    }
}
