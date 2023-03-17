package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)  // null값인 필드 아예 반환X
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
    private List<ResponseOrder> orders;
}
