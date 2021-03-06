
package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.BookRentalReadDto;
import com.zutode.bookshopclone.shop.application.dto.BookRentalWriteDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.domain.model.TimeProvider;
import com.zutode.bookshopclone.shop.domain.model.entity.Book;
import com.zutode.bookshopclone.shop.domain.model.entity.BookRental;
import com.zutode.bookshopclone.shop.domain.repository.BookRentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class BookRentalServiceTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BookRentalRepository bookRentalRepository;
    @Mock
    private BookService bookService;
    @Mock
    private TimeProvider timeProvider;


    private BookRentalService bookRentalService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        bookRentalService = new BookRentalService(bookRentalRepository, modelMapper,
                10, new BigDecimal("0.4"), timeProvider, bookService);
    }


    @Test
    void shouldThrowExceptionWhenBookRentalAlreadyExists() {
        //given
        Long bookId = 123L;

        BookRentalWriteDto bookRentalWriteDto = new BookRentalWriteDto();
        BookRental bookRental = Mockito.spy(BookRental.class);
        Book book = new Book();
        LocalDate initialDate = LocalDate.of(2021, 4, 9);
        book.setId(bookId);
        bookRental.setBook(book);

        when(modelMapper.map(bookRentalWriteDto, BookRental.class)).thenReturn(bookRental);
        when(bookService.findBookById(bookId)).thenReturn(book);
        when(timeProvider.now()).thenReturn(initialDate);
        when(bookRentalRepository.existsByBookIdAndReturnDateIsNull(bookId)).thenReturn(true);


        //when
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> bookRentalService.createBooksRental(bookRentalWriteDto));


        //then
        verify(bookRental).setBook(book);
        verify(bookRental).setInitialDate(initialDate);
        verify(bookRental).setExpectedReturnDate(initialDate.plusDays(10));
        assertThat(exception.getMessage()).isEqualTo("Book with id " + bookId + " is already rent!");
    }


    @Test
    void shouldSaveCreatedRentalBook() {
        //given
        Long bookId = 123L;

        BookRentalWriteDto bookRentalWriteDto = new BookRentalWriteDto();
        BookRentalReadDto bookRentalReadDto = new BookRentalReadDto();
        BookRental bookRental = Mockito.spy(BookRental.class);
        LocalDate initialDate = LocalDate.of(2021, 4, 9);
        Book book = new Book();
        bookRental.setBook(book);
        book.setId(bookId);

        when(modelMapper.map(bookRentalWriteDto, BookRental.class)).thenReturn(bookRental);
        when(bookService.findBookById(bookId)).thenReturn(book);
        when(bookRentalRepository.existsByBookIdAndReturnDateIsNull(bookId)).thenReturn(false);
        when(timeProvider.now()).thenReturn(initialDate);
        when(bookRentalRepository.save(bookRental)).thenReturn(bookRental);
        when(modelMapper.map(bookRental, BookRentalReadDto.class)).thenReturn(bookRentalReadDto);

        //when
        BookRentalReadDto returned = bookRentalService.createBooksRental(bookRentalWriteDto);

        //then
        verify(bookRental).setBook(book);
        verify(bookRental).setInitialDate(initialDate);
        verify(bookRental).setExpectedReturnDate(initialDate.plusDays(10));
        assertThat(bookRentalReadDto).isEqualTo(returned);
    }


}

