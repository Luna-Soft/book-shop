package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.auth.domain.model.entity.UserAccount;
import com.zutode.bookshopclone.auth.domain.service.UserService;
import com.zutode.bookshopclone.shop.application.dto.BookReadDto;
import com.zutode.bookshopclone.shop.application.dto.BookRentalReadDto;
import com.zutode.bookshopclone.shop.application.dto.BookRentalWriteDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.domain.model.TimeProvider;
import com.zutode.bookshopclone.shop.domain.model.entity.Book;
import com.zutode.bookshopclone.shop.domain.model.entity.BookRental;
import com.zutode.bookshopclone.shop.domain.repository.BookRentalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
    private final ModelMapper modelMapper;
    private final int rentalDuration;
    private final BigDecimal chargePerDay;
    private final TimeProvider timeProvider;
    private final BookService bookService;

    public BookRentalService(BookRentalRepository bookRentalRepository,
                             ModelMapper modelMapper,
                             @Value("${bookshop.lending.duration}") int rentalDuration,
                             @Value("${bookshop.lending.charge}") BigDecimal chargePerDay,
                             TimeProvider timeProvider,
                             BookService bookService) {
        this.bookRentalRepository = bookRentalRepository;
        this.modelMapper = modelMapper;
        this.rentalDuration = rentalDuration;
        this.chargePerDay = chargePerDay;
        this.timeProvider = timeProvider;
        this.bookService = bookService;
    }


    @Transactional
    public BookRentalReadDto createBooksRental(BookRentalWriteDto bookRentalWriteDto) {
        BookRental bookRental = modelMapper.map(bookRentalWriteDto, BookRental.class);
        setRentalDates(bookRental);
        checkIfBookIsAlreadyRent(bookRental);
        BookRental saved = bookRentalRepository.save(bookRental);
        return modelMapper.map(saved, BookRentalReadDto.class);
    }


    private void setRentalDates(BookRental bookRental) {
        LocalDate initialDate = timeProvider.now();
        bookRental.setInitialDate(initialDate);
        bookRental.setExpectedReturnDate(initialDate.plusDays(rentalDuration));
    }

    private void checkIfBookIsAlreadyRent(BookRental bookRental) {
        if (bookRentalRepository.existsByBookIdAndReturnDateIsNull(bookRental.getBook().getId())) {
            throw new ResourceAlreadyExistsException("Book with id " + bookRental.getBook().getId() + " is already rent!");
        }
    }



    @Transactional
    public BookRentalReadDto extendRentedBook(Long bookId) {
        BookRental bookRental = bookRentalRepository.findByBookId(bookId);
        checkIfBookReturnDateIsNotExceeded(bookId, bookRental);
        checkIfBookRentalExtensionIsNotOverLimit(bookRental);
        increaseExtensionCounterAndPostponeReturnDate(bookRental);
        return modelMapper.map(bookRental, BookRentalReadDto.class);
    }


    private void checkIfBookReturnDateIsNotExceeded(Long bookId, BookRental bookRental) {
        if (bookRental.getExpectedReturnDate().isBefore(timeProvider.now())) {
            throw new IllegalStateException("Book with id " + bookId + " is after the expected return date, you cannot extend this rental!");
        }
    }

    private void checkIfBookRentalExtensionIsNotOverLimit(BookRental bookRental) {
        if (bookRental.getExtension() >= 2) {
            throw new IllegalStateException("You have just extended book twice!");
        }
    }

    private void increaseExtensionCounterAndPostponeReturnDate(BookRental bookRental) {
        bookRental.setExpectedReturnDate(bookRental.getExpectedReturnDate().plusDays(rentalDuration));
        bookRental.setExtension(bookRental.getExtension() + 1);
    }


    @Transactional
    public BookRentalReadDto returnRentedBook(Long bookId) {
        BookRental bookRental = bookRentalRepository.findByBookId(bookId);
        bookRental.setReturnDate(timeProvider.now());
        checkIfIsCharge(bookRental);
        return modelMapper.map(bookRental, BookRentalReadDto.class);
    }


    private void checkIfIsCharge(BookRental bookRental) {
        if (bookRental.getReturnDate().isAfter(bookRental.getExpectedReturnDate())) {
            long daysLate = DAYS.between(bookRental.getExpectedReturnDate(), bookRental.getReturnDate());
            BigDecimal bigDecimal = BigDecimal.valueOf(daysLate);
            bookRental.setCharge(chargePerDay.multiply(bigDecimal));
        }
    }


    @Transactional
    public BookRentalReadDto getBookRental(Long id) {
        BookRental bookRental = bookRentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book rental with id " + id + " does not exist"));
        return modelMapper.map(bookRental, BookRentalReadDto.class);
    }


    @Transactional
    public List<BookRentalReadDto> getPageableBooksRental(int page, int size, Authentication authentication) {
        PageRequest pageRequest = PageRequest.of(page, size);
        User principal = (User) authentication.getPrincipal();
        return bookRentalRepository.findBookRentalByUser(principal.getUsername(), pageRequest)
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
        } catch (Exception e) {
            throw new EntityNotFoundException("Book rental does not exist");
        }
    }


}
