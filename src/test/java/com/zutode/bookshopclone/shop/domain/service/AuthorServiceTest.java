package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.AuthorDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.application.exception.ResourceNotFoundException;
import com.zutode.bookshopclone.shop.domain.model.entity.Author;
import com.zutode.bookshopclone.shop.domain.model.entity.Genre;
import com.zutode.bookshopclone.shop.domain.repository.AuthorRepository;
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
import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;


public class AuthorServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorService authorService;


    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void shouldThrowExceptionWhenAuthorAlreadyExists(){
        //given
        AuthorDto authorDto = new AuthorDto();
        String name = "name";
        String surname = "surname";
        authorDto.setName(name);
        authorDto.setSurname(surname);
        when(authorRepository.existsByNameAndSurname(name, surname)).thenReturn(true);

        //when
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> authorService.createAuthor(authorDto));

        //then
        assertThat(exception.getMessage()).isEqualTo("Author: " + name + " " + surname + " already exists");
    }


    @Test
    void shouldSaveCreatedAuthor(){
        //given
        Author author = new Author();
        AuthorDto authorDto = new AuthorDto();
        String name = "name";
        String surname = "surname";
        when(authorRepository.existsByNameAndSurname(name, surname)).thenReturn(false);
        when(modelMapper.map(authorDto, Author.class)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        //when
        AuthorDto returned = authorService.createAuthor(authorDto);


        //then
        assertThat(authorDto).isEqualTo(returned);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindAuthorByIdInMethodGetAuthor(){
        //given
        Long id = 123L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authorService.getAuthor(id));

        //then
        assertThat(exception.getMessage()).isEqualTo("Author by id: " + id + " does not exist");
    }


    @Test
    void shouldGetAuthor(){
        //given
        Author author = new Author();
        AuthorDto authorDto = new AuthorDto();
        Long id = 123L;
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        //when
        AuthorDto returned = authorService.getAuthor(id);

        //then
        assertThat(authorDto).isEqualTo(returned);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindAuthorByIdInMethodUpdateAuthor(){
        //given
        AuthorDto authorDto = new AuthorDto();
        Long id = 123L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authorService.updateAuthor(authorDto, id));

        //then
        assertThat(exception.getMessage()).isEqualTo("Author by id: " + id + " does not exist");
    }


    @Test
    void shouldSaveUpdatedAuthor(){
        //given
        Author author = Mockito.spy(Author.class);
        AuthorDto authorDto = Mockito.spy(AuthorDto.class);
        Long id = 123L;
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        //when
        AuthorDto returned = authorService.updateAuthor(authorDto, id);

        //then
        verify(author).setName(authorDto.getName());
        verify(author).setSurname(authorDto.getSurname());
        assertThat(authorDto).isEqualTo(returned);
    }


    @Test
    void shouldThrowExceptionWhenCannotFindAuthorByIdInMethodDeleteAuthor(){
        //given
        Long id = 123L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        //when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> authorService.deleteAuthor(id));

        //then
        assertThat(exception.getMessage()).isEqualTo("Author by id: " + id + " does not exist");
    }


    @Test
    void shouldDeleteAuthor(){
        //given
        Author author = new Author();
        Long id = 123L;
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        //when
        authorService.deleteAuthor(id);

        //then
        verify(authorRepository).delete(author);
    }


    @Test
    void shouldFindAllAuthorsByIds(){
        //given
        Author author1 = new Author();
        Author author2 = new Author();
        Set<Author> authors = Set.of(author1, author2);
        Set<Long> ids = Set.of(123L, 456L);
        when(authorRepository.findAllByIdIn(ids)).thenReturn(authors);

        //when
        Set<Author> result = authorService.getAuthorsByIds(ids);

        //then
        assertThat(authors).isEqualTo(result);
    }


    @Test
    void shouldGetPageableAuthors(){
        //given
        Author author = new Author();
        AuthorDto authorDto = new AuthorDto();
        int page = 1;
        int size = 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Author> authors = List.of(author);
        Page<Author> pageAuthors = new PageImpl<>(authors);
        when(authorRepository.findAll(pageRequest)).thenReturn(pageAuthors);
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);

        //when
        List<AuthorDto> result = authorService.getPageableAuthors(page, size);


        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).contains(authorDto);
    }


    @Test
    void shouldGetAuthorsByIds(){
        //given
        Author author = new Author();
        Long id = 123L;
        Set<Author> authors = Set.of(author);
        Set<Long> ids = new HashSet<>();
        ids.add(123L);
        ids.add(456L);
        when(authorRepository.findAllByIdIn(ids)).thenReturn(authors);

        //when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> authorService.getAuthorsByIds(ids));

        //then
        assertThat(exception.getMessage()).isEqualTo("Cannot find authors with id " + Arrays.toString(ids.toArray()));
    }










}
