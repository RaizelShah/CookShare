package com.raizelshahprojects.cookshare.service.image;

import com.raizelshahprojects.cookshare.dto.ImageDto;
import com.raizelshahprojects.cookshare.model.Image;
import com.raizelshahprojects.cookshare.model.Recipe;
import com.raizelshahprojects.cookshare.repository.ImageRepository;
import com.raizelshahprojects.cookshare.service.Recipe.IRecipeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IRecipeService recipeService;

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(imageRepository::delete, () -> {
            throw new EntityNotFoundException("Image not found");
        });
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try {
            image.setImage(new SerialBlob(file.getBytes()));
            image.setFileType(file.getContentType());
            image.setFileName(file.getOriginalFilename());
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ImageDto saveImage(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeService.getRecipeById(recipeId);

        try {
            Image image = new Image();
            image.setImage(new SerialBlob(file.getBytes()));
            image.setFileType(file.getContentType());
            image.setFileName(file.getOriginalFilename());
            image.setRecipe(recipe);

            String buildDownloadUrl = "/api/images/download/";
            String downloadUrl = buildDownloadUrl + image.getId();
            image.setDownloadUrl(downloadUrl);

            Image savedImage = imageRepository.save(image);
            savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
            imageRepository.save(savedImage);

            ImageDto imageDto = new ImageDto();
            imageDto.setId(savedImage.getId());
            imageDto.setFileName(savedImage.getFileName());
            imageDto.setDownloadUrl(savedImage.getDownloadUrl());
            return imageDto;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
