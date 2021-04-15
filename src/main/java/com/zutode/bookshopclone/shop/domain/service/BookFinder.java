package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.domain.model.entity.Book;
import com.zutode.bookshopclone.shop.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class BookFinder {

    private final BookRepository bookRepository;

    @Transactional
    //Created to avoid code duplication and circular bean injection (caused by ModelMapper)
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: " + id + " does not exist"));
    }
}
