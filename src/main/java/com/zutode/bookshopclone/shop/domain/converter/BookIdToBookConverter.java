package com.zutode.bookshopclone.shop.domain.converter;

import com.zutode.bookshopclone.shop.application.dto.id.BookIdDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceNotFoundException;
import com.zutode.bookshopclone.shop.domain.model.entity.Book;
import com.zutode.bookshopclone.shop.domain.repository.BookRepository;
import com.zutode.bookshopclone.shop.domain.service.BookFinder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookIdToBookConverter extends AbstractConverter<BookIdDto, Book> {
    private final BookFinder bookFinder;

    @Override
    protected Book convert(BookIdDto bookIdDto) {
        return bookFinder.findBookById(bookIdDto.getId());
    }
}
