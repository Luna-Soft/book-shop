package com.zutode.bookshopclone.shop.application.controller;

import com.zutode.bookshopclone.shop.application.dto.BookRentalReadDto;
import com.zutode.bookshopclone.shop.application.dto.BookRentalWriteDto;
import com.zutode.bookshopclone.shop.application.validator.RestConstraintValidator;
import com.zutode.bookshopclone.shop.domain.service.BookRentalService;
import com.zutode.bookshopclone.shop.domain.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rest/v1")
public class BookRentalController {

    private final BookRentalService bookRentalService;
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final RestConstraintValidator validator;

    public BookRentalController(BookRentalService bookRentalService,
                                BookService bookService,
                                ModelMapper modelMapper,
                                @Qualifier(value = "simpleValidator") RestConstraintValidator validator) {
        this.bookRentalService = bookRentalService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }


    @PostMapping("/booksRental")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookRentalReadDto addBookRental(@RequestBody @Validated BookRentalWriteDto bookRentalWriteDto,
                                           BindingResult bindingResult) {
        validator.validate(bindingResult);
        return bookRentalService.createBooksRental(bookRentalWriteDto);
    }


    @PutMapping("/booksRental/{bookId}/returned")
    public BookRentalReadDto returnRentedBook(@PathVariable("bookId") Long bookId) {
        return bookRentalService.returnRentedBook(bookId);
    }

    @PutMapping("/booksRental/{bookId}/extend")
    public BookRentalReadDto extendRentedBook(@PathVariable("bookId") Long bookId){
        return bookRentalService.extendRentedBook(bookId);
    }

    @GetMapping("/booksRental/{id}")
    public BookRentalReadDto getBookRental(@PathVariable("id") Long id){
        return bookRentalService.getBookRental(id);
    }

    @GetMapping("/booksRentals")
    public List<BookRentalReadDto> getPageableBooksRentals(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "5") int size){
        return bookRentalService.getPageableBooksRental(page, size);
    }


    @DeleteMapping("/booksRental/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookRental(@PathVariable("bookId") Long bookId){
        bookRentalService.deleteBookRental(bookId);
    }



}
