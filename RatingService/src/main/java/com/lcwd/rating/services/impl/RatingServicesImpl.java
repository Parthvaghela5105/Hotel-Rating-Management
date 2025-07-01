package com.lcwd.rating.services.impl;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.repository.RatingReporitory;
import com.lcwd.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RatingServicesImpl implements RatingService {
    @Autowired
    private RatingReporitory ratingReporitory;

    @Override
    public Rating create(Rating rating) {
        return ratingReporitory.save(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return ratingReporitory.findAll();
    }

    @Override
    public List<Rating> getRatingsByUserId(String userId) {
        return ratingReporitory.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingsByHotelId(String hotelId) {
        return ratingReporitory.findByHotelId(hotelId);
    }
}
