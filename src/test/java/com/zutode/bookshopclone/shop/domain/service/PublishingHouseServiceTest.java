package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.PublishingHouseDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.domain.model.entity.PublishingHouse;
import com.zutode.bookshopclone.shop.domain.repository.PublishingHouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PublishingHouseServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PublishingHouseRepository publishingHouseRepository;
    @InjectMocks
    private PublishingHouseService publishingHouseService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void shouldThrowExceptionWhenPublishingHouseAlreadyExists() {
        //given
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto();
        String name = "name";
        publishingHouseDto.setName(name);
        when(publishingHouseRepository.existsByName(name)).thenReturn(true);

        //when
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> publishingHouseService.createPublishingHouse(publishingHouseDto));

        //then
        assertThat(exception.getMessage()).isEqualTo("Publishing house by name: " + name + " already exists");
    }


    @Test
    void shouldSaveCreatedPublishingHouse() {
        //given
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto();
        String name = "name";
        PublishingHouse publishingHouse = new PublishingHouse();
        when(publishingHouseRepository.existsByName(name)).thenReturn(false);
        when(modelMapper.map(publishingHouseDto, PublishingHouse.class)).thenReturn(publishingHouse);
        when(publishingHouseRepository.save(publishingHouse)).thenReturn(publishingHouse);
        when(modelMapper.map(publishingHouse, PublishingHouseDto.class)).thenReturn(publishingHouseDto);

        //when
        PublishingHouseDto returned = publishingHouseService.createPublishingHouse(publishingHouseDto);

        //then
        assertThat(publishingHouseDto).isEqualTo(returned);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindPublishingHouseByIdInUpdatePublishingHouseMethod() {
        //given
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto();
        Long id = 123L;
        when(publishingHouseRepository.findById(id)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> publishingHouseService.updatePublishingHouse(publishingHouseDto, id));

        //then
        assertThat(exception.getMessage()).isEqualTo("Publishing House by id: " + id + " does not exist");
    }


    @Test
    void shouldSaveUpdatedPublishingHouse() {
        //given
        Long id = 123L;
        String name = "name";
        PublishingHouse publishingHouse = Mockito.spy(PublishingHouse.class);
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto();
        publishingHouseDto.setName(name);
        when(publishingHouseRepository.findById(id)).thenReturn(Optional.of(publishingHouse));
        when(modelMapper.map(publishingHouse, PublishingHouseDto.class)).thenReturn(publishingHouseDto);

        //when
        PublishingHouseDto returned = publishingHouseService.updatePublishingHouse(publishingHouseDto, id);

        //then
        verify(publishingHouse).setName(publishingHouseDto.getName());
        assertThat(publishingHouseDto).isEqualTo(returned);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindPublishingHouseByIdInGetPublishingHouseMethod() {
        //given
        Long id = 123L;
        when(publishingHouseRepository.findById(id)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> publishingHouseService.getPublishingHouse(id));

        //then
        assertThat(exception.getMessage()).isEqualTo("Publishing House by id: " + id + " does not exist");
    }


    @Test
    void shouldGetPublishingHouse() {
        //given
        PublishingHouse publishingHouse = new PublishingHouse();
        Long id = 132L;
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto();
        when(publishingHouseRepository.findById(id)).thenReturn(Optional.of(publishingHouse));
        when(modelMapper.map(publishingHouse, PublishingHouseDto.class)).thenReturn(publishingHouseDto);

        //when
        PublishingHouseDto returned = publishingHouseService.getPublishingHouse(id);

        //then
        assertThat(publishingHouseDto).isEqualTo(returned);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindPublishingHouseByIdInDeletePublishingHouseMethod() {
        //given
        Long id = 123L;
        when(publishingHouseRepository.findById(id)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> publishingHouseService.deletePublishingHouse(id));

        //then
        assertThat(exception.getMessage()).isEqualTo("Publishing House by id: " + id + " does not exist");
    }


    @Test
    void shouldDeletePublishingHouse() {
        //given
        PublishingHouse publishingHouse = new PublishingHouse();
        Long id = 123L;
        when(publishingHouseRepository.findById(id)).thenReturn(Optional.of(publishingHouse));

        //when
        publishingHouseService.deletePublishingHouse(id);

        //then
        verify(publishingHouseRepository).delete(publishingHouse);
    }


    @Test
    void ShouldGetPageablePublishingHouses() {
        //given
        int page = 1;
        int size = 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        PublishingHouse publishingHouse = new PublishingHouse();
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto();
        List<PublishingHouse> publishingHouses = List.of(publishingHouse);
        Page<PublishingHouse> pagePublishingHouses = new PageImpl<>(publishingHouses);
        when(publishingHouseRepository.findAll(pageRequest)).thenReturn(pagePublishingHouses);
        when(modelMapper.map(publishingHouse, PublishingHouseDto.class)).thenReturn(publishingHouseDto);

        //when
        List<PublishingHouseDto> result = publishingHouseService.getPageablePublishingHouses(page, size);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).contains(publishingHouseDto);
    }


}
