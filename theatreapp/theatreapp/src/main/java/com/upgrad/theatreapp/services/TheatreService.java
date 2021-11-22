package com.upgrad.theatreapp.services;

import com.upgrad.theatreapp.entities.Theatre;

public interface TheatreService {

    public Theatre getTheatreDetails(int theatreId, int movieId);
}
