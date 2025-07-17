package com.personalsoft.btgfund.com.api_java.infraestructure.output.dynamodb.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "")
public class Users {

    private String userId;
    private String email;
    private String password;
    private String role;

    @DynamoDBHashKey(attributeName = "user_id")
    public String getUserId() {
        return userId;
    }

    @DynamoDBIndexHashKey(attributeName = "email", globalSecondaryIndexName = "email-index")
    public String getEmail() {
        return email;
    }
}