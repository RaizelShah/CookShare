package com.raizelshahprojects.cookshare.repository;

import com.raizelshahprojects.cookshare.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image getImageById(Long id);
}
