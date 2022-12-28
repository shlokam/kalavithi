package com.tw.prograd.user.dto;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
public class UserPasswordDTO {
    private Integer id;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPasswordDTO that = (UserPasswordDTO) o;
        return Objects.equals(id, that.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
