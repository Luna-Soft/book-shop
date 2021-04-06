package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.BookReadDto;
import com.zutode.bookshopclone.shop.application.dto.BookWriteDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.domain.model.entity.Book;
import com.zutode.bookshopclone.shop.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final PublishingHouseService publishingHouseService;

    @Transactional
    public BookReadDto createBook(BookWriteDto bookWriteDto) {
        checkIfBookAlreadyExists(bookWriteDto);
        Book book = mapDtoToEntity(bookWriteDto);
        Book saved = bookRepository.save(book);
        return mapEntityToDto(saved);
    }

    @Transactional
    public BookReadDto updateBook(BookWriteDto bookWriteDto, Long id) {
        Book book = findBookById(id);
        settingDtoValuesToEntity(bookWriteDto,book);
        return mapEntityToDto(book);
    }

    @Transactional
    public BookReadDto getBook(Long id) {
        Book book = findBookById(id);
        return mapEntityToDto(book);
    }

    @Transactional
    public List<BookReadDto> getPageableBooks(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return bookRepository.findAll(pageRequest)
                .stream()
                .map(book -> mapEntityToDto(book))
                .collect(Collectors.toList());
    }


    @Transactional
    public void deleteBook(Long id) {
        Book book = findBookById(id);
        bookRepository.delete(book);
    }




    private BookReadDto mapEntityToDto(Book saved) {
        BookReadDto returned = new BookReadDto();
        settingEntityValuesToDto(saved, returned);
        return returned;
    }

    private void settingEntityValuesToDto(Book saved, BookReadDto returned) {
        returned.setId(saved.getId());
        returned.setISBN(saved.getISBN());
        returned.setTitle(saved.getTitle());
        returned.setDescription(saved.getDescription());
        returned.setPrice(saved.getPrice());
    }

    private Book mapDtoToEntity(BookWriteDto bookWriteDto) {
        Book book = new Book();
        settingDtoValuesToEntity(bookWriteDto, book);
        return book;
    }

    private void settingDtoValuesToEntity(BookWriteDto bookWriteDto, Book book) {
        book.setISBN(bookWriteDto.getISBN());
        book.setTitle(bookWriteDto.getTitle());
        book.setPublishingHouse(publishingHouseService.findPublishingHouseById(bookWriteDto.getPublishingHouse()));
        book.setDescription(bookWriteDto.getDescription());
        book.setAuthors(authorService.getAuthorsByIds(bookWriteDto.getAuthors()));
        book.setGenres(genreService.getGenresByIds(bookWriteDto.getGenres()));
        book.setPrice(bookWriteDto.getPrice());
    }

    @Transactional
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: " + id + " does not exist"));
    }

    private void checkIfBookAlreadyExists(BookWriteDto bookWriteDto) {
        if (bookRepository.existsByISBN(bookWriteDto.getISBN())) {
            throw new ResourceAlreadyExistsException("Book in ISBN: " + bookWriteDto.getISBN() + "already exists");
        }
    }
}
