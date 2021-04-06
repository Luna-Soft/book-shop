package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.GenreDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.domain.model.entity.Genre;
import com.zutode.bookshopclone.shop.domain.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class GenreServiceTest {


    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreService genreService;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void shouldThrowExceptionWhenGenreAlreadyExists() {
        //given
        GenreDto genreDto = new GenreDto();
        String name = "name";
        genreDto.setName(name);
        when(genreRepository.existsByName(name)).thenReturn(true);

        //when
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> genreService.createGenre(genreDto));

        //then
        assertThat(exception.getMessage()).isEqualTo("Genre: " + name + " already exists");
    }



}