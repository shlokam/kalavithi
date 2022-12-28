package com.tw.prograd.image;

import com.tw.prograd.image.dto.UploadImage;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column
    private String description;


    public UploadImage toSavedImageDTO() {
        return new UploadImage(id, name, description);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
