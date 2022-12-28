package com.tw.prograd.image;

import com.tw.prograd.image.dto.*;
import com.tw.prograd.image.storage.file.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@ExtendWith(MockitoExtension.class)
class ImageTransferServiceTest {
    private final int userId = 1;
    private final int imageId = 1;
    private final boolean status =true;


    @Mock
    private StorageService storageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private LikeImageRepository likeImageRepository;

    @Mock
    private FavouriteImageRepository favouriteImageRepository;

    @InjectMocks
    private ImageTransferService imageTransferService;

    private MockMultipartFile image;

    private byte[] imageContent;

    @BeforeEach
    void setUp() {
        imageContent = "dummy image content".getBytes();
        image = new MockMultipartFile("image", "image.png", "multipart/form-data", imageContent);
    }

    @Test
    void shouldStoreImage() {
        doNothing().when(storageService).store(image,"Hi, this ia a sample");
        ImageEntity savedImage = new ImageEntity(1, "image.png","Hi, this is a sample");
        when(imageRepository.save(any())).thenReturn(savedImage);

        UploadImage actual = imageTransferService.store(image,"Hi, this ia a sample");

        assertEquals(new UploadImage(1, "image.png","Hi, this is a sample"), actual);
        verify(storageService).store(image, "Hi, this ia a sample");
        verify(imageRepository).save(any());
    }

    @Test
    void shouldLoadImageWhenRequestedWithImageName() {
        Resource image = mock(Resource.class);
        when(storageService.load("image.png")).thenReturn(image);

        assertEquals(image, imageTransferService.imageByName("image.png"));

        verify(storageService).load("image.png");
    }

    @Test
    void shouldReturnImageMediaTypeWhenContentTypeRequested() {
        Resource image = mock(Resource.class);
        when(storageService.contentType(image)).thenReturn("image/png");

        assertEquals(IMAGE_PNG_VALUE, imageTransferService.contentType(image));

        verify(storageService).contentType(image);
    }

    @Test
    void shouldReturnsImagesWhenRequested() {
        when(imageRepository.findAll()).thenReturn(List.of(new ImageEntity(1, "image.png","Hi, this is a sample")));

        String url = "http://localhost:8080/api/images/";
        StoredImage images = imageTransferService.images(url);


        assertEquals(new StoredImage(List.of(new Image(1, "image.png", url + "image.png","Hi, this is a sample"))), images);

        verify(imageRepository).findAll();
    }

//<<<<<<< HEAD
    @Test
    void shouldBeAbleToLikeAnImageWhenTheStatusIsFalse(){
        LikeImageEntity likeImageEntity = LikeImageEntity.builder()
                .userid(userId)
                .imageid(imageId)
                .status(status).build();
        LikeImage likeImage =LikeImage.builder()
                        .userid(userId)
                        .imageid(imageId)
                        .status(status).build();
        when(likeImageRepository.getByUseridAndImageid(likeImage.getUserid(),likeImage.getImageid())).thenReturn(likeImageEntity);
        int expectedUserId= imageTransferService.changeLikeStatus(likeImage);

        assertThat(expectedUserId).isEqualTo(likeImage.getUserid());

        verify(likeImageRepository).save(likeImageEntity);

    }


    @Test
    void shouldBeAbleToAddAnImageToFavouriteWhenTheStatusIsFalse(){
        FavouriteImageEntity favouriteImageEntity = FavouriteImageEntity.builder()
                .userid(userId)
                .imageid(imageId)
                .favouritestatus(status).build();
        FavouriteImage favouriteImage =FavouriteImage.builder()
                .userid(userId)
                .imageid(imageId)
                .favouritestatus(status).build();
        when(favouriteImageRepository.getByUseridAndImageid(favouriteImage.getUserid(),favouriteImage.getImageid())).thenReturn(favouriteImageEntity);
        int expectedUserId= imageTransferService.changeFavouriteStatus(favouriteImage);

        assertThat(expectedUserId).isEqualTo(favouriteImage.getUserid());

        verify(favouriteImageRepository).save(favouriteImageEntity);

    }

}