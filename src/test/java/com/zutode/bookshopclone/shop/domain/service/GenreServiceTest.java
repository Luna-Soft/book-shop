package com.zutode.bookshopclone.shop.domain.service;


import com.zutode.bookshopclone.shop.domain.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.testng.annotations.Test;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;



class GenreServiceTest{

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreService genreService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenCannotFindGenreById(){
        //given
        Long id = 1L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> genreService.createGenre(null));

        //then
        assertThat(exception.getMessage()).isEqualTo("Cannot find genre with id: " + id);
    }

}