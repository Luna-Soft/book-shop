package com.zutode.bookshopclone.shop.application.controller;

import com.zutode.bookshopclone.shop.application.dto.AuthorDto;
import com.zutode.bookshopclone.shop.application.validator.RestConstraintValidator;
import com.zutode.bookshopclone.shop.domain.service.AuthorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rest/v1")
public class AuthorController {

    private final RestConstraintValidator validator;
    private final AuthorService authorService;

    public AuthorController(@Qualifier(value = "simpleValidator") RestConstraintValidator validator,
                            AuthorService authorService) {
        this.validator = validator;
        this.authorService = authorService;
    }


    @PostMapping("/author")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthorDto addAuthor(@Validated @RequestBody AuthorDto newAuthorDto, BindingResult bindingResult) {
        validator.validate(bindingResult);
        return authorService.createAuthor(newAuthorDto);
    }

    @GetMapping("/author/{id}")
    public AuthorDto getAuthor(@PathVariable("id") Long id) {
        return authorService.getAuthor(id);
    }


    @GetMapping("/authors")
    public List<AuthorDto> getPageableAuthors(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "5") int size) {
        return authorService.getPageableAuthors(page, size);

    }

    @PutMapping("/author/{id}")
    public AuthorDto updateAuthor(@Validated @RequestBody AuthorDto authorDto,
                                  @PathVariable("id") Long id,
                                  BindingResult bindingResult) {
        validator.validate(bindingResult);
        return authorService.updateAuthor(authorDto, id);
    }

    @DeleteMapping("/author/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }


}
