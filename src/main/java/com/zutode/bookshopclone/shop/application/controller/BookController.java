package com.zutode.bookshopclone.shop.application.controller;

import com.zutode.bookshopclone.shop.application.dto.BookReadDto;
import com.zutode.bookshopclone.shop.application.dto.BookWriteDto;
import com.zutode.bookshopclone.shop.application.validator.RestConstraintValidator;
import com.zutode.bookshopclone.shop.domain.service.BookService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rest/v1")
public class BookController {

    private final RestConstraintValidator validator;
    private final BookService bookService;

    public BookController(@Qualifier(value = "simpleValidator") RestConstraintValidator validator,
                          BookService bookService) {
        this.validator = validator;
        this.bookService = bookService;
    }


    @PostMapping("/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookReadDto addBook(@Validated @RequestBody BookWriteDto newBook,
                               BindingResult bindingResult) {
        validator.validate(bindingResult);
        return bookService.createBook(newBook);
    }

    @PutMapping("/book/{id}")
    public BookReadDto updateBook(@PathVariable("id") Long id,
                                  @Validated @RequestBody BookWriteDto bookWriteDto,
                                  BindingResult bindingResult) {
        validator.validate(bindingResult);
        return bookService.updateBook(bookWriteDto, id);
    }

    @GetMapping("/book/{id}")
    public BookReadDto getBook(@PathVariable("id") Long id) {
        return bookService.getBook(id);
    }


    @GetMapping("/books")
    public List<BookReadDto> getPageableBooks(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "5") int size) {
        return bookService.getPageableBooks(page, size);
    }


    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

}
