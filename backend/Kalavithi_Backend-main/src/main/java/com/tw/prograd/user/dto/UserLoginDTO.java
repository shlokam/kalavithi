package com.tw.prograd.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
