package com.ch.carinspection.imgupl.controller;



import com.ch.carinspection.imgupl.model.Image;
import com.ch.carinspection.imgupl.model.ScanStatus;
import com.ch.carinspection.imgupl.service.repository.ImageRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
public class ImageGraphQLController {

    private final ImageRepository imageRepository;

    public ImageGraphQLController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @QueryMapping
    public Image getImageById(@Argument Long id) {
        Optional<Image> image = imageRepository.findById(id);
        return image.orElse(null);
    }

    @QueryMapping
    public List<Image> allImages() {
        return imageRepository.findAll();
    }

    @MutationMapping
    public Image createImage(
            @Argument String imageName,
            @Argument String imageUrl,
            @Argument Double sizeMb,
            @Argument String timestamp,
            @Argument String contentType,
            @Argument Integer approxNetworkCalls,
            @Argument Double approxIops,
            @Argument ScanStatus scanStatus
    ) {
        Image img = new Image();
        img.setImageName(imageName);
        img.setImageUrl(imageUrl);
        img.setSizeMb(sizeMb);
        img.setTimestamp(Instant.parse(timestamp));
        img.setContentType(contentType);
        img.setApproxNetworkCalls(approxNetworkCalls);
        img.setApproxIops(approxIops);
        img.setScanStatus(scanStatus);

        return imageRepository.save(img);
    }

    @MutationMapping
    public Image updateScanStatus(@Argument Long id, @Argument ScanStatus scanStatus) {
        Optional<Image> imgOpt = imageRepository.findById(id);
        if (imgOpt.isPresent()) {
            Image img = imgOpt.get();
            img.setScanStatus(scanStatus);
            return imageRepository.save(img);
        }
        return null;
    }
}
