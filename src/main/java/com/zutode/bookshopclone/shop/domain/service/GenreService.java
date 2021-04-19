package com.zutode.bookshopclone.shop.domain.service;

import com.zutode.bookshopclone.shop.application.dto.GenreDto;
import com.zutode.bookshopclone.shop.application.exception.ResourceAlreadyExistsException;
import com.zutode.bookshopclone.shop.application.exception.ResourceNotFoundException;
import com.zutode.bookshopclone.shop.domain.model.entity.Genre;
import com.zutode.bookshopclone.shop.domain.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;


    @Transactional
    public GenreDto createGenre(GenreDto genreDto) {
        Genre genre = modelMapper.map(genreDto, Genre.class);
        checkIfGenreAlreadyExists(genre);
        Genre saved = genreRepository.save(genre);
        return modelMapper.map(saved, GenreDto.class);

    }


    @Transactional
    public GenreDto updateGenre(GenreDto genreDto, long id) {
        Genre genre = findGenreById(id);
        genre.setName(genreDto.getName());
        return modelMapper.map(genre, GenreDto.class);
    }


    @Transactional
    public GenreDto getGenre(Long id) {
        Genre genre = findGenreById(id);
        return modelMapper.map(genre, GenreDto.class);
    }


    @Transactional
    public List<GenreDto> getPageableGenres(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return genreRepository.findAll(pageRequest)
                .stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
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
    public void deleteGenre(Long id) {
        Genre genre = findGenreById(id);
        genreRepository.delete(genre);
    }




    private Genre findGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with id: " + id + " does not exist"));
    }


    private void checkIfGenreAlreadyExists(Genre genre) {
        if (genreRepository.existsByName(genre.getName())) {
            throw new ResourceAlreadyExistsException("Genre: " + genre.getName() + " already exists");
        }
    }

}
