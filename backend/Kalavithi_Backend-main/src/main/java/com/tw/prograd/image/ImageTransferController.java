package com.tw.prograd.image;

import com.tw.prograd.dto.SuccessResponse;
import com.tw.prograd.image.dto.FavouriteImage;
import com.tw.prograd.image.dto.LikeImage;
import com.tw.prograd.image.dto.StoredImage;
import com.tw.prograd.image.dto.UploadImage;
import com.tw.prograd.image.exception.ImageNotFoundException;
import com.tw.prograd.image.exception.ImageStorageException;
import com.tw.prograd.user.entity.UserAuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/images")

public class ImageTransferController {



    private final ImageTransferService service;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    public ImageTransferController(ImageTransferService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    @Cacheable(value = "cacheImages")
    public ResponseEntity<Resource> serveImage(@PathVariable String name) throws IOException {

        Resource image = service.imageByName(name);

        return status(OK)
                .header(CONTENT_TYPE, service.contentType(image))
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                .body(image);
    }


    @GetMapping
    @Cacheable(value = "cacheImages")
    public ResponseEntity<StoredImage> serveImages(HttpServletRequest request) {
        return status(OK)
                .body(service.images(request.getRequestURL().toString()));

    }


    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @CacheEvict(value = "cacheImages",allEntries = true)
    public ResponseEntity<UploadImage> uploadImage(@RequestPart("image") MultipartFile file,@RequestPart("description") String description ,RedirectAttributes redirectAttributes) {

        UploadImage image = service.store(file,description);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + image.getName() + "!");

        return status(FOUND)
                .location(URI.create(servletContextPath + "/images/" + image.getName()))
                .body(image);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    private ResponseEntity<?> handleImageNotFoundException(ImageNotFoundException exception) {

        return status(NOT_FOUND).build();
    }

    @ExceptionHandler(ImageStorageException.class)
    private ResponseEntity<?> handleImageStoreException(ImageStorageException exception) {

        return status(FORBIDDEN).build();
    }

    @PutMapping
    public ResponseEntity<Object> likeImage(@RequestBody LikeImage likeImage, Authentication authentication) {
        UserAuthEntity user = (UserAuthEntity) authentication.getPrincipal();
        service.changeLikeStatus(likeImage);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(user.getId()));

    }

    @GetMapping("id/{userid}")
    public ResponseEntity<Object> likeImageStatus(@PathVariable int userid) {
        return status(OK)
                .body(service.imageStatus(userid));

    }

    @GetMapping("/count")
    public ResponseEntity<Object> likeImageCount() {

        return status(OK)
                .body(service.imageLikeCount());

    }

    @PutMapping("/favourite-image")
    public ResponseEntity<Object> favouriteImage(@RequestBody FavouriteImage favouriteImage, Authentication authentication) {
        UserAuthEntity user = (UserAuthEntity) authentication.getPrincipal();
       service.changeFavouriteStatus(favouriteImage);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(user.getId()));
    }

    @GetMapping("favourite-image/id/{userid}")
    public ResponseEntity<Object> favouriteImageStatus(@PathVariable int userid) {
        System.out.println("controller "+service.imageFavouriteStatus(userid));
        return status(OK)
                .body(service.imageFavouriteStatus(userid));

    }
}
