package com.tw.prograd.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    Optional<ImageEntity> findByName(String filename);

    @Transactional
    @Modifying
    @Query("UPDATE image SET description = (case " +
            "when name = '1.jpeg' then 'This is a painting depicting a beautiful scenery, which has a tree and a paint brush symbolising the relation between art and nature for an artist.' " +
            "when name = '2.jpeg' then 'Here, the artist has tried to capture all the emotions of a woman, that they go through and hide them behind her calming face just like clouds hide the scorching sun.' " +
            "when name = '3.jpeg' then 'Eyes are the most beautiful creation on this earth 🌍 which depicts the purity of a soul, for once a human can lie through their words but not through their eyes.'" +
            " when name = '4.jpeg' then 'This art portrays what an eye can see and what an eye can hold 👁️' " +
            "when name = '5.jpeg' then 'sometimes , a person gets trapped in their own thoughts and fears and create a whole world into it, unable to escape until and unless they release their thoughts 😶‍🌫️' " +
            "when name = '6.jpeg' then 'Free yourself like these birds in the arms of the nature with a aim to touch the sky and flying 1000km away from all the redundant emotions and relations 💕.' end)")
    void updateDescription();


}
