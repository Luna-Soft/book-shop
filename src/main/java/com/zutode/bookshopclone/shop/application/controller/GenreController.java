package com.zutode.bookshopclone.shop.application.controller;

import com.zutode.bookshopclone.shop.application.dto.GenreDto;
import com.zutode.bookshopclone.shop.application.validator.RestConstraintValidator;
import com.zutode.bookshopclone.shop.domain.service.GenreService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/rest/v1")
public class GenreController {

    private final RestConstraintValidator validator;
    private final GenreService genreService;


    public GenreController(@Qualifier(value = "simpleValidator") RestConstraintValidator validator,
                           GenreService genreService) {
        this.validator = validator;
        this.genreService = genreService;
    }


    @PostMapping("/genre")
    @PreAuthorize("hasRole('ROLE_MAINTAINER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GenreDto addGenre(@RequestBody @Validated GenreDto newGenreDto,
                             BindingResult bindingResult) {
        validator.validate(bindingResult);
        return genreService.createGenre(newGenreDto);
    }


    @PutMapping("/genre/{id}")
    @PreAuthorize("hasRole('ROLE_MAINTAINER')")
    public GenreDto updateGenre(@PathVariable Long id,
                                @RequestBody @Validated GenreDto genreDto,
                                BindingResult bindingResult) {
        validator.validate(bindingResult);
        return genreService.updateGenre(genreDto, id);
    }

    @GetMapping("/genre/{id}")
    @PreAuthorize("hasRole('ROLE_READER')")
    public GenreDto getGenre(@PathVariable Long id) {
        return genreService.getGenre(id);
    }


    @GetMapping("/genres")
    @PreAuthorize("hasRole('ROLE_READER')")
    public List<GenreDto> getPageableGenres(@RequestParam(name = "page", defaultValue = "0") int page,
                                            @RequestParam(name = "size", defaultValue = "5") int size) {
        return genreService.getPageableGenres(page, size);
    }


    @DeleteMapping("/genre/{id}")
    @PreAuthorize("hasRole('ROLE_MAINTAINER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
    }

}
