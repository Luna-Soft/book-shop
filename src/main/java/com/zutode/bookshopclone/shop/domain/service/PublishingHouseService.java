package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.PublishingHouseDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.domain.model.entity.PublishingHouse;
import com.zutode.bookshopclone.shop.domain.repository.PublishingHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublishingHouseService {

    private final PublishingHouseRepository publishingHouseRepository;

    @Transactional
    public PublishingHouseDto createPublishingHouse(PublishingHouseDto publishingHouseDto) {
        checkIfPublishingHouseAlreadyExists(publishingHouseDto);
        PublishingHouse publishingHouse = mapDtoToEntity(publishingHouseDto);
        PublishingHouse saved = publishingHouseRepository.save(publishingHouse);
        return mapEntityToDto(saved);
    }

    @Transactional
    public PublishingHouseDto updatePublishingHouse(PublishingHouseDto publishingHouseDto, Long id) {
        PublishingHouse publishingHouse = findPublishingHouseById(id);
        settingDtoValuesToEntity(publishingHouseDto, publishingHouse);
        PublishingHouse saved = publishingHouseRepository.save(publishingHouse);
        return mapEntityToDto(saved);
    }

    @Transactional
    public PublishingHouseDto getPublishingHouse(Long id) {
        PublishingHouse publishingHouse = findPublishingHouseById(id);
        return mapEntityToDto(publishingHouse);
    }

    @Transactional
    public List<PublishingHouseDto> getPageablePublishingHouses(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return publishingHouseRepository.findAll(pageRequest)
                .stream()
                .map(publishingHouse -> mapEntityToDto(publishingHouse))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePublishingHouse(Long id) {
        PublishingHouse publishingHouse = findPublishingHouseById(id);
        publishingHouseRepository.delete(publishingHouse);
    }


    public PublishingHouse findPublishingHouseById(Long id) {
        return publishingHouseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publishing House by id: " + id + " does not exist"));
    }

    private PublishingHouseDto mapEntityToDto(PublishingHouse saved) {
        PublishingHouseDto returned = new PublishingHouseDto();
        settingEntityValuesToDto(saved, returned);
        return returned;
    }

    private void settingEntityValuesToDto(PublishingHouse saved, PublishingHouseDto returned) {
        returned.setId(saved.getId());
        returned.setName(saved.getName());
    }

    private PublishingHouse mapDtoToEntity(PublishingHouseDto publishingHouseDto) {
        PublishingHouse publishingHouse = new PublishingHouse();
        settingDtoValuesToEntity(publishingHouseDto, publishingHouse);
        return publishingHouse;
    }

    private void settingDtoValuesToEntity(PublishingHouseDto publishingHouseDto, PublishingHouse publishingHouse) {
        publishingHouse.setName(publishingHouseDto.getName());
    }

    private void checkIfPublishingHouseAlreadyExists(PublishingHouseDto publishingHouseDto) {
        if (publishingHouseRepository.existsByName(publishingHouseDto.getName())) {
            throw new ResourceAlreadyExistsException("Publishing house by name: " + publishingHouseDto.getName() + " already exists");
        }
    }
}

