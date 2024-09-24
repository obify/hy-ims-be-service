package com.obify.hy.ims.controller;

import com.obify.hy.ims.entity.fi.Ingredient;
import com.obify.hy.ims.repository.fi.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/test")
public class FiInventoryController {

    @Autowired
    IngredientRepository ingredientRepository;

    @PostMapping("/fi/ingredients")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient){
        ingredient = ingredientRepository.save(ingredient);
        return new ResponseEntity<>(ingredient, HttpStatus.CREATED);
    }

    @GetMapping("/fi/ingredients")
    public ResponseEntity<List<Ingredient>> getAllIngredient(){
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        return new ResponseEntity<>(ingredientList, HttpStatus.OK);
    }
}
