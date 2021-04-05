package com.zutode.bookshopclone.shop.application.controller;

import com.zutode.bookshopclone.shop.application.dto.PublishingHouseDto;
import com.zutode.bookshopclone.shop.application.validator.RestConstraintValidator;
import com.zutode.bookshopclone.shop.domain.service.PublishingHouseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rest/v1")
public class PublishingHouseController {

    private final RestConstraintValidator validator;
    private final PublishingHouseService publishingHouseService;

    public PublishingHouseController(@Qualifier(value = "simpleValidator") RestConstraintValidator validator, PublishingHouseService publishingHouseService) {
        this.validator = validator;
        this.publishingHouseService = publishingHouseService;
    }


    @PostMapping("/publishingHouse")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PublishingHouseDto addPublishingHouse(@Validated @RequestBody PublishingHouseDto newPublishingHouse,
                                                 BindingResult bindingResult) {
        validator.validate(bindingResult);
        return publishingHouseService.createPublishingHouse(newPublishingHouse);
    }


    @PutMapping("/publishingHouse/{id}")
    public PublishingHouseDto updatePublishingHouse(@Validated @RequestBody PublishingHouseDto publishingHouseDto,
                                                    @PathVariable("id") Long id,
                                                    BindingResult bindingResult) {
        validator.validate(bindingResult);
        return publishingHouseService.updatePublishingHouse(publishingHouseDto, id);
    }


    @GetMapping("/publishingHouse/{id}")
    public PublishingHouseDto getPublishingHouse(@PathVariable("id") Long id) {
        return publishingHouseService.getPublishingHouse(id);
    }

    @GetMapping("/publishingHouses")
    public List<PublishingHouseDto> getPageablePublishingHouses(@RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "5") int size) {
        return publishingHouseService.getPageablePublishingHouses(page, size);
    }

    @DeleteMapping("/publishingHouse/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublishingHouse(@PathVariable("id") Long id) {
        publishingHouseService.deletePublishingHouse(id);
    }


}
