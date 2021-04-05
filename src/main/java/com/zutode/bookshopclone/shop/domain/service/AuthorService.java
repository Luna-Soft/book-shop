package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.AuthorDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.application.exception.ResourceNotFoundException;
import com.zutode.bookshopclone.shop.domain.model.entity.Author;
import com.zutode.bookshopclone.shop.domain.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
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

    private final AuthorRepository authorRepository;


    @Transactional
    public AuthorDto getAuthor(Long id) {
        Author author = findAuthorById(id);
        return mappedEntityToDto(author);
    }

    @Transactional
    public List<AuthorDto> getPageableAuthors(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return authorRepository.findAll(pageRequest)
                .stream()
                .map(author -> mappedEntityToDto(author))
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
    public AuthorDto createAuthor(AuthorDto authorDto) {
        checkIfAuthorAlreadyExists(authorDto);
        Author author = mappedDtoToEntity(authorDto);
        Author saved = authorRepository.save(author);
        return mappedEntityToDto(saved);
    }

    @Transactional
    public AuthorDto updateAuthor(AuthorDto authorDto, Long id) {
        Author author = findAuthorById(id);
        settingDtoValuesToEntity(authorDto, author);
        Author saved = authorRepository.save(author);
        return mappedEntityToDto(saved);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = findAuthorById(id);
        authorRepository.delete(author);
    }


    private AuthorDto mappedEntityToDto(Author saved) {
        AuthorDto returned = new AuthorDto();
        settingEntityValuesToDto(saved, returned);
        return returned;
    }

    private void settingEntityValuesToDto(Author saved, AuthorDto returned) {
        returned.setId(saved.getId());
        returned.setName(saved.getName());
        returned.setSurname(saved.getSurname());
    }

    private Author mappedDtoToEntity(AuthorDto authorDto) {
        Author author = new Author();
        settingDtoValuesToEntity(authorDto, author);
        return author;
    }

    private void settingDtoValuesToEntity(AuthorDto authorDto, Author author) {
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
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
