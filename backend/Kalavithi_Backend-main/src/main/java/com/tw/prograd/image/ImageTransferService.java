package com.tw.prograd.image;

import com.tw.prograd.image.dto.*;
import com.tw.prograd.image.storage.file.StorageService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.trimTrailingCharacter;


@Service
public class ImageTransferService {

    private StorageService service;

    private ImageRepository repository;

    private FavouriteImageRepository favouriteImageRepository;

    private LikeImageRepository likeImageRepository;

    private ImageLikesCountRepository imageLikesCountRepository;


    public ImageTransferService(StorageService service, ImageRepository repository, LikeImageRepository likeImageRepository ,FavouriteImageRepository favouriteImageRepository) {
        this.service = service;
        this.repository = repository;
        this.likeImageRepository=likeImageRepository;
        this.favouriteImageRepository=favouriteImageRepository;
    }


    Resource imageByName(String name) {
        return service.load(name);
    }



    StoredImage images(String url) {

        List<Image> images = repository.findAll()
                .parallelStream()
                .map(it -> new Image(it.getId(), it.getName(), trimTrailingCharacter(url, '/') + "/" + it.getName(),it.getDescription()))
                .collect(toList());

        return new StoredImage(images);
    }


    UploadImage store(MultipartFile file, String description) {
        service.store(file, description);
        ImageEntity imageEntity = new ImageEntity(null, file.getOriginalFilename(),description); //"png" --> 1.png ---> 1 1.png
        return repository.save(imageEntity).toSavedImageDTO();
    }

    public String contentType(Resource image) {
        return service.contentType(image);
    }
    public int changeLikeStatus(LikeImage likeImage){

        LikeImageEntity likeImageEntity = likeImageRepository.getByUseridAndImageid(likeImage.getUserid(),likeImage.getImageid());
        likeImageEntity.setStatus(likeImage.getStatus());

        likeImageRepository.save(likeImageEntity);
        return likeImageEntity.getUser_id();
    }

    public List<LikeImageEntity> imageStatus(int userid) {


        List<LikeImageEntity> likeImageEntityList = likeImageRepository.findByUserid(userid);

        return likeImageEntityList;

    }
    public List<Object[]> imageLikeCount() {


        List<Object[]> likeImageEntityList = likeImageRepository.findLikeCount();

        return likeImageEntityList;

    }

    public int changeFavouriteStatus(FavouriteImage favouriteImage){

        FavouriteImageEntity favouriteImageEntity = favouriteImageRepository.getByUseridAndImageid(favouriteImage.getUserid(),favouriteImage.getImageid());
        favouriteImageEntity.setFavouritestatus(favouriteImage.isFavouritestatus());
        favouriteImageRepository.save(favouriteImageEntity);
        return favouriteImageEntity.getUserid();
    }

    public List<FavouriteImageEntity> imageFavouriteStatus(int userid) {
        List<FavouriteImageEntity> favouriteImageEntityList = favouriteImageRepository.findByUserid(userid);
        return favouriteImageEntityList;

    }
}
