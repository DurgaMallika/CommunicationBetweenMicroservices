package com.upgrad.movieapp.services;

import com.upgrad.movieapp.entities.Movie;
import com.upgrad.movieapp.entities.Theatre;
import com.upgrad.movieapp.entities.User;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface MovieService {

    public Movie acceptMovieDetails(Movie movie);

    public List<Movie> acceptMultipleMovieDetails(List<Movie> movie);

    public Movie getMovieDetails(int id);

    public Movie updateMovieDetails(int id, Movie movie);

    public boolean deleteMovie(int id);

    public List<Movie> getAllMovies();

    public Page<Movie> getPaginatedMovieDetails(Pageable pageable);

    Boolean bookMovie(User user, Movie movie, Theatre theatre);
}
