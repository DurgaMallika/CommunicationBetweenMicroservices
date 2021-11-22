package com.upgrad.movieapp.dao;

import com.upgrad.movieapp.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This layer is used to talk to the databases
 */
public interface MovieDAO extends JpaRepository<Movie, Integer> {

}
