package com.shopeasy.estore.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class userDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
}
