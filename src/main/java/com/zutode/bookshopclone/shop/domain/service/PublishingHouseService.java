package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.PublishingHouseDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.domain.model.entity.PublishingHouse;
import com.zutode.bookshopclone.shop.domain.repository.PublishingHouseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublishingHouseService {

    private final ModelMapper modelMapper;
    private final PublishingHouseRepository publishingHouseRepository;


    @Transactional
    public PublishingHouseDto createPublishingHouse(PublishingHouseDto publishingHouseDto) {
        checkIfPublishingHouseAlreadyExists(publishingHouseDto);
        PublishingHouse publishingHouse = modelMapper.map(publishingHouseDto, PublishingHouse.class);
        PublishingHouse saved = publishingHouseRepository.save(publishingHouse);
        return modelMapper.map(saved,PublishingHouseDto.class);
    }

    @Transactional
    public PublishingHouseDto updatePublishingHouse(PublishingHouseDto publishingHouseDto, Long id) {
        PublishingHouse publishingHouse = findPublishingHouseById(id);
        publishingHouse.setName(publishingHouseDto.getName());
        PublishingHouse saved = publishingHouseRepository.save(publishingHouse);
        return modelMapper.map(saved,PublishingHouseDto.class);
    }

    @Transactional
    public PublishingHouseDto getPublishingHouse(Long id) {
        PublishingHouse publishingHouse = findPublishingHouseById(id);
        return modelMapper.map(publishingHouse,PublishingHouseDto.class);
    }

    @Transactional
    public List<PublishingHouseDto> getPageablePublishingHouses(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return publishingHouseRepository.findAll(pageRequest)
                .stream()
                .map(publishingHouse -> modelMapper.map(publishingHouse, PublishingHouseDto.class))
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


    private void checkIfPublishingHouseAlreadyExists(PublishingHouseDto publishingHouseDto) {
        if (publishingHouseRepository.existsByName(publishingHouseDto.getName())) {
            throw new ResourceAlreadyExistsException("Publishing house by name: " + publishingHouseDto.getName() + " already exists");
        }
    }
}

