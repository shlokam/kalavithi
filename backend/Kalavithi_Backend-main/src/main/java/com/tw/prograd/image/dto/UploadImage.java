package com.tw.prograd.image.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadImage {
    private Integer id;
    private String name;
    private String description;


}
