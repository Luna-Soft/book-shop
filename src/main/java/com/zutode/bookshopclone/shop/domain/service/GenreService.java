package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.GenreDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.application.exception.ResourceNotFoundException;
import com.zutode.bookshopclone.shop.domain.model.entity.Genre;
import com.zutode.bookshopclone.shop.domain.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;


    @Transactional
    public GenreDto getGenre(Long id) {
        Genre genre = findGenreById(id);
        return mapEntityToDto(genre);
    }


    @Transactional
    public List<GenreDto> getPageableGenres(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return genreRepository.findAll(pageRequest)
                .stream()
                .map(genre -> mapEntityToDto(genre))
                .collect(Collectors.toList());

    }

    @Transactional
    public Set<Genre> getGenresByIds(Set<Long> ids) {
        Set<Genre> genres = genreRepository.findAllByIdIn(ids);
        if (genres.size() != ids.size()) {
            genres.forEach(genre -> ids.remove(genre.getId()));
            throw new ResourceNotFoundException("Cannot find genres with id: " + Arrays.toString(ids.toArray()));
        }
        return genres;
    }


    @Transactional
    public GenreDto createGenre(GenreDto genreDto) {
        checkIfGenreAlreadyExists(genreDto);
        Genre genre = mapDtoToEntity(genreDto);
        Genre saved = genreRepository.save(genre);
        return mapEntityToDto(saved);
    }


    @Transactional
    public GenreDto updateGenre(GenreDto genreDto, long id) {
        Genre genre = findGenreById(id);
        settingDtoValuesToEntity(genreDto, genre);
        Genre saved = genreRepository.save(genre);
        return mapEntityToDto(saved);
    }


    @Transactional
    public void deleteGenre(Long id) {
        Genre genre = findGenreById(id);
        genreRepository.delete(genre);


    }




    private GenreDto mapEntityToDto(Genre saved) {
        GenreDto returned = new GenreDto();
        settingEntityValuesToDto(saved, returned);
        return returned;
    }

    private void settingEntityValuesToDto(Genre saved, GenreDto returned) {
        returned.setName(saved.getName());
        returned.setId(saved.getId());
    }


    private Genre mapDtoToEntity(GenreDto genreDto) {
        Genre genre = new Genre();
        settingDtoValuesToEntity(genreDto, genre);
        return genre;
    }

    private void settingDtoValuesToEntity(GenreDto genreDto, Genre genre) {
        genre.setName(genreDto.getName());
    }


    private Genre findGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with id: " + id + " does not exist"));
    }


    private void checkIfGenreAlreadyExists(GenreDto genreDto) {
        if (genreRepository.existsByName(genreDto.getName())) {
            throw new ResourceAlreadyExistsException("Genre: " + genreDto.getName() + " already exists");
        }
    }

}
