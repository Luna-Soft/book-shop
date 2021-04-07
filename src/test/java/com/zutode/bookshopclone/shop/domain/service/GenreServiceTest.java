package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.GenreDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.application.exception.ResourceNotFoundException;
import com.zutode.bookshopclone.shop.domain.model.entity.Genre;
import com.zutode.bookshopclone.shop.domain.repository.GenreRepository;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class GenreServiceTest {


    @Mock
    private ModelMapper modelMapper;
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


    @Test
    void shouldSaveCreatedGenre() {
        //given
        GenreDto genreDto = new GenreDto();
        String name = "name";
        Genre genre = new Genre();
        when((genreRepository.existsByName(name))).thenReturn(false);
        when(modelMapper.map(genreDto, Genre.class)).thenReturn(genre);
        when(genreRepository.save(genre)).thenReturn(genre);
        when(modelMapper.map(genre, GenreDto.class)).thenReturn(genreDto);

        //when
        GenreDto returned = genreService.createGenre(genreDto);

        //then
        assertThat(genreDto).isEqualTo(returned);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindGenreByIdInMethodGetGenre() {
        //given
        Long id = 123L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> genreService.getGenre(id));

        //then
        assertThat(exception.getMessage()).isEqualTo("Genre with id: " + id + " does not exist");
    }


    @Test
    void shouldGetGenre() {
        //given
        Genre genre = new Genre();
        Long id = 123L;
        GenreDto genreDto = new GenreDto();
        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(modelMapper.map(genre, GenreDto.class)).thenReturn(genreDto);

        //when
        GenreDto returned = genreService.getGenre(id);

        //then
        assertThat(genreDto).isEqualTo(returned);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindGenreByIdInMethodUpdateGenre() {
        //given
        GenreDto genreDto = new GenreDto();
        Long id = 123L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> genreService.updateGenre(genreDto, id));

        //then
        assertThat(exception.getMessage()).isEqualTo("Genre with id: " + id + " does not exist");
    }


    @Test
    void shouldSaveUpdatedGenre() {
        //given
        Genre genre = Mockito.spy(Genre.class);
        Long id = 123L;
        GenreDto genreDto = new GenreDto();
        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(modelMapper.map(genre, GenreDto.class)).thenReturn(genreDto);


        //when
        GenreDto returned = genreService.updateGenre(genreDto, id);

        //then
        verify(genre).setName(genreDto.getName());
        assertThat(genreDto).isEqualTo(returned);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindGenreByIdInMethodDeleteGenre() {
        //given
        Long id = 123L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> genreService.deleteGenre(id));

        //then
        assertThat(exception.getMessage()).isEqualTo("Genre with id: " + id + " does not exist");
    }


    @Test
    void shouldDeleteGenre() {
        //given
        Genre genre = new Genre();
        Long id = 123L;
        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));

        //when
        genreService.deleteGenre(id);

        //then
        verify(genreRepository).delete(genre);
    }


    @Test
    void shouldFindAllGenresByIds() {
        //given
        Genre genre1 = new Genre();
        Genre genre2 = new Genre();
        Set<Genre> genres = Set.of(genre1, genre2);
        Set<Long> ids = Set.of(13L, 132L);
        when(genreRepository.findAllByIdIn(ids)).thenReturn(genres);

        //when
        Set<Genre> returned = genreService.getGenresByIds(ids);

        //then
        assertThat(genres).isEqualTo(returned);
    }


    @Test
    void shouldGetPageableGenres() {
        //given
        Genre genre = new Genre();
        GenreDto genreDto = new GenreDto();
        int page = 1;
        int size = 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Genre> genres = List.of(genre);
        Page<Genre> pageGenres = new PageImpl<>(genres);
        when(genreRepository.findAll(pageRequest)).thenReturn(pageGenres);
        when(modelMapper.map(genre, GenreDto.class)).thenReturn(genreDto);

        //when
        List<GenreDto> result = genreService.getPageableGenres(page, size);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).contains(genreDto);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindGenresWithId() {
        //given
        Genre genre1 = new Genre();
        Long id = 132L;
        genre1.setId(id);
        Set<Genre> genres = Set.of(genre1);

        Set<Long> ids = new HashSet<>();
        ids.add(854L);
        ids.add(132L);
        when(genreRepository.findAllByIdIn(ids)).thenReturn(genres);

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> genreService.getGenresByIds(ids));

        //then
        assertThat(exception.getMessage()).isEqualTo("Cannot find genres with id: " + Arrays.toString(ids.toArray()));
    }


}