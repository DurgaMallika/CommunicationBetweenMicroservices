package com.upgrad.theatreapp.services;

import com.upgrad.theatreapp.entities.Theatre;
import com.upgrad.theatreapp.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheatreServiceImpl implements TheatreService{

    @Autowired
    private TheatreRepository theatreRepository;

    @Override
    public Theatre getTheatreDetails(int theatreId, int movieId) {

        Theatre requestedTheatre = theatreRepository.findByTheatreIdAndMovieId(theatreId, movieId);

        if(requestedTheatre!=null)
            return requestedTheatre;
        return null;
    }
}
