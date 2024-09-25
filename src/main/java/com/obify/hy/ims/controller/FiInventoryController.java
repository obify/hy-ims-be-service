package com.obify.hy.ims.controller;

import com.obify.hy.ims.entity.fi.FiPlannedInventory;
import com.obify.hy.ims.entity.fi.FiProductIngredient;
import com.obify.hy.ims.entity.fi.Ingredient;
import com.obify.hy.ims.repository.fi.IngredientRepository;
import com.obify.hy.ims.repository.fi.PlannedInventoryRepository;
import com.obify.hy.ims.repository.fi.ProductIngredientRepository;
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
    private IngredientRepository ingredientRepository;
    @Autowired
    private ProductIngredientRepository productIngredientRepository;
    @Autowired
    private PlannedInventoryRepository plannedInventoryRepository;

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
    @PostMapping("/fi/ingredients/save")
    public ResponseEntity<String> saveProductIngredients(@RequestBody FiProductIngredient ingredient){
        productIngredientRepository.save(ingredient);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
    @GetMapping("/fi/inventory")
    public ResponseEntity<List<FiPlannedInventory>> getAllInventory(){
        List<FiPlannedInventory> inventories = plannedInventoryRepository.findAll();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }
    @PostMapping("/fi/inventory/save")
    public ResponseEntity<String> saveProductInventory(@RequestBody FiPlannedInventory fiPlannedInventory){
        plannedInventoryRepository.save(fiPlannedInventory);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
}
