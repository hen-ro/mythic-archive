package com.techelevator.controller;


import com.techelevator.model.CardCollection;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class CollectionController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public List<CardCollection> getAllCollections() {
        return new ArrayList<>();

    }







}
