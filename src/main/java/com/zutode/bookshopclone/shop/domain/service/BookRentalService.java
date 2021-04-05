package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.auth.domain.model.entity.UserAccount;
import com.zutode.bookshopclone.auth.domain.service.UserService;
import com.zutode.bookshopclone.shop.application.dto.BookReadDto;
import com.zutode.bookshopclone.shop.application.dto.BookRentalReadDto;
import com.zutode.bookshopclone.shop.application.dto.BookRentalWriteDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.application.exception.ResourceNotFoundException;
import com.zutode.bookshopclone.shop.domain.model.entity.Book;
import com.zutode.bookshopclone.shop.domain.model.entity.BookRental;
import com.zutode.bookshopclone.shop.domain.model.entity.Genre;
import com.zutode.bookshopclone.shop.domain.repository.BookRentalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
public class BookRentalService {

    private final BookRentalRepository bookRentalRepository;
    private final BookService bookService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final int rentalDuration;
    private final BigDecimal chargePerDay;

    public BookRentalService(BookRentalRepository bookRentalRepository,
                             BookService bookService,
                             UserService userService,
                             ModelMapper modelMapper,
                             @Value("${bookshop.lending.duration}") int rentalDuration,
                             @Value("${bookshop.lending.charge}") BigDecimal chargePerDay) {
        this.bookRentalRepository = bookRentalRepository;
        this.bookService = bookService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.rentalDuration = rentalDuration;
        this.chargePerDay = chargePerDay;
    }


    @Transactional
    public BookRentalReadDto createBooksRental(BookRentalWriteDto bookRentalWriteDto) {
        if (bookRentalRepository.existsByBookIdAndReturnDateIsNull(bookRentalWriteDto.getBookId())) {
            throw new ResourceAlreadyExistsException("Book with id " + bookRentalWriteDto.getBookId() + " is already rent!");
        }
        BookRental bookRental = new BookRental();
        Book book = bookService.findBookById(bookRentalWriteDto.getBookId());
        bookRental.setBook(book);

        UserAccount user = userService.findUserById(bookRentalWriteDto.getUserId());
        bookRental.setUser(user);
        LocalDate initialDate = LocalDate.now();
        bookRental.setInitialDate(initialDate);
        bookRental.setExpectedReturnDate(initialDate.plusDays(rentalDuration));


        BookRental saved = bookRentalRepository.save(bookRental);
        return modelMapper.map(saved, BookRentalReadDto.class);
    }


    @Transactional
    public BookRentalReadDto returnRentedBook(Long bookId) {
        BookRental bookRental = bookRentalRepository.findByBookId(bookId);
        bookRental.setReturnDate(LocalDate.now());
        if (bookRental.getReturnDate().isAfter(bookRental.getExpectedReturnDate())) {
            long daysLate = DAYS.between(bookRental.getExpectedReturnDate(), bookRental.getReturnDate());
            BigDecimal bigDecimal = BigDecimal.valueOf(daysLate);
            bookRental.setCharge(chargePerDay.multiply(bigDecimal));
        }
        return modelMapper.map(bookRental, BookRentalReadDto.class);
    }


    @Transactional
    public BookRentalReadDto extendRentedBook(Long bookId) {
        BookRental bookRental = bookRentalRepository.findByBookId(bookId);
        if (bookRental.getExpectedReturnDate().isBefore(LocalDate.now())) {
            long daysLate = DAYS.between(bookRental.getExpectedReturnDate(), LocalDate.now());
            BigDecimal bigDecimal = BigDecimal.valueOf(daysLate);
            bookRental.setCharge(chargePerDay.multiply(bigDecimal));
            throw new IllegalStateException("Book with id " + bookId + " has a charge, you cannot extend its!");
        }
        if (bookRental.getExtension() >= 2) {
            throw new IllegalStateException("You have just extended book twice!");
        }
        bookRental.setExpectedReturnDate(bookRental.getExpectedReturnDate().plusDays(rentalDuration));
        bookRental.setExtension(bookRental.getExtension() + 1);
        return modelMapper.map(bookRental, BookRentalReadDto.class);
    }

    @Transactional
    public BookRentalReadDto getBookRental(Long id) {
        BookRental bookRental = bookRentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book rental with id " + id + " does not exist"));
        return modelMapper.map(bookRental, BookRentalReadDto.class);
    }

    @Transactional
    public List<BookRentalReadDto> getPageableBooksRental(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return bookRentalRepository.findAll(pageRequest)
                .stream()
                .map(bookRental -> modelMapper.map(bookRental, BookRentalReadDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public void deleteBookRental(Long bookId) {
        try {
            BookRental bookRental = bookRentalRepository.findByBookId(bookId);
            bookRentalRepository.delete(bookRental);
            BookReadDto book = bookService.getBook(bookId);
            System.out.println("You have to pay punishment: " + book.getPrice());
        } catch (Exception e){
            throw new EntityNotFoundException("Book rental does not exist");
        }
    }




}
