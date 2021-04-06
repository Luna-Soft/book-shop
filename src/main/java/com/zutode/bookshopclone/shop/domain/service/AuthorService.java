package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.AuthorDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.application.exception.ResourceNotFoundException;
import com.zutode.bookshopclone.shop.domain.model.entity.Author;
import com.zutode.bookshopclone.shop.domain.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthorService {

    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;


    @Transactional
    public AuthorDto createAuthor(AuthorDto authorDto) {
        checkIfAuthorAlreadyExists(authorDto);
        Author author = modelMapper.map(authorDto, Author.class);
        Author saved = authorRepository.save(author);
        return modelMapper.map(saved, AuthorDto.class);
    }

    @Transactional
    public AuthorDto updateAuthor(AuthorDto authorDto, Long id) {
        Author author = findAuthorById(id);
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        return modelMapper.map(author, AuthorDto.class);
    }

    @Transactional
    public AuthorDto getAuthor(Long id) {
        Author author = findAuthorById(id);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Transactional
    public List<AuthorDto> getPageableAuthors(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return authorRepository.findAll(pageRequest)
                .stream()
                .map(author -> modelMapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Set<Author> getAuthorsByIds(Set<Long> ids) {
        Set<Author> authors = authorRepository.findAllByIdIn(ids);
        if (authors.size() != ids.size()) {
            authors.forEach(author -> ids.remove(author.getId()));
            throw new ResourceNotFoundException("Cannot find authors with id " + Arrays.toString(ids.toArray()));
        }
        return authors;
    }


    @Transactional
    public void deleteAuthor(Long id) {
        Author author = findAuthorById(id);
        authorRepository.delete(author);
    }



    private Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author by id: " + id + " does not exist"));
    }

    private void checkIfAuthorAlreadyExists(AuthorDto authorDto) {
        if (authorRepository.existsByNameAndSurname(authorDto.getName(), authorDto.getSurname())) {
            throw new ResourceAlreadyExistsException("Author: " + authorDto.getName() + " " + authorDto.getSurname() + " already exists");
        }
    }


}
