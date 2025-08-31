package com.raizelshahprojects.cookshare.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private long id;
    private int stars;
    private String feedBack;
    private UserDto user;
}
