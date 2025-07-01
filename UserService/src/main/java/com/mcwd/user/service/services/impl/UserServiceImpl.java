package com.mcwd.user.service.services.impl;

import com.mcwd.user.service.entities.Hotel;
import com.mcwd.user.service.entities.Rating;
import com.mcwd.user.service.entities.User;
import com.mcwd.user.service.exceptions.ResourceNotFoundException;
import com.mcwd.user.service.repositories.UserRepository;
import com.mcwd.user.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    //get single user
    @Override
    public User getUser(String userId) {
        //get user from database with the help of the repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server :: " + userId));
        //fetch rating of the above user from RATING SERVICE
        //http://localhost:8083/ratings/users/8ca8a42a-b188-448a-a070-998ae680f01b
        Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("" , ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).collect(Collectors.toList());

        List<Rating>ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to hotel
            // http://localhost:8082/hotels
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId() , Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("Response status code: " , forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);

            //return rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }
}
