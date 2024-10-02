package com.obify.hy.ims.controller;

import com.obify.hy.ims.client.SquareupFeignClient;
import com.obify.hy.ims.client.model.LocationModel;
import com.obify.hy.ims.client.model.LocationModelWrapper;
import com.obify.hy.ims.client.model.SingleLocationModelWrapper;
import com.obify.hy.ims.dto.square.OverviewRequestDTO;
import com.obify.hy.ims.dto.square.OverviewResponseDTO;
import com.obify.hy.ims.dto.square.RequestLocationDTO;
import com.obify.hy.ims.entity.User;
import com.obify.hy.ims.entity.fi.FiIngredient;
import com.obify.hy.ims.entity.fi.FiPlannedInventory;
import com.obify.hy.ims.entity.fi.FiProductIngredient;
import com.obify.hy.ims.entity.fi.Ingredient;
import com.obify.hy.ims.entity.square.SqSale;
import com.obify.hy.ims.repository.UserRepository;
import com.obify.hy.ims.repository.fi.IngredientRepository;
import com.obify.hy.ims.repository.fi.PlannedInventoryRepository;
import com.obify.hy.ims.repository.fi.ProductIngredientRepository;
import com.obify.hy.ims.repository.square.SqSalesRepository;
import com.obify.hy.ims.service.SquareupService;
import com.obify.hy.ims.service.impl.FiInventoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private SquareupFeignClient squareupFeignClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FiInventoryServiceImpl fiInventoryService;
    @Autowired
    private SqSalesRepository sqSalesRepository;

    @PutMapping("/fi/locations/{locationId}/users/{email}")
    public ResponseEntity<String> updateLocation(@PathVariable String locationId, @PathVariable String email){
        Optional<User> optUser = userRepository.findByEmail(email);
        if(optUser.isPresent()){
            optUser.get().setLocationId(locationId);
            userRepository.save(optUser.get());
        }
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
    }

    @PostMapping("/fi/locations")
    public ResponseEntity<List<LocationModel>> getAllLocations(@RequestBody RequestLocationDTO locationDTO){
        ResponseEntity<LocationModelWrapper> lmw = squareupFeignClient.getAllLocations(locationDTO.getToken());
        List<LocationModel> locations = lmw.getBody().getLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @PostMapping("/fi/location-detail/{locationId}")
    public ResponseEntity<SingleLocationModelWrapper> getLocationDetail(@RequestBody RequestLocationDTO locationDTO, @PathVariable String locationId){
        ResponseEntity<SingleLocationModelWrapper> lmw = squareupFeignClient.getLocationDetail(locationDTO.getToken(), locationId);
        return lmw;
    }

    @PostMapping("/fi/ingredients")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient){
        ingredient = ingredientRepository.save(ingredient);
        return new ResponseEntity<>(ingredient, HttpStatus.CREATED);
    }

    @GetMapping("/fi/ingredients-detail/{ingredientId}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable String ingredientId){
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @GetMapping("/fi/ingredients/{merchantId}")
    public ResponseEntity<List<Ingredient>> getAllIngredient(@PathVariable String merchantId){
        List<Ingredient> ingredientList = ingredientRepository.findAllByMerchantId(merchantId);
        return new ResponseEntity<>(ingredientList, HttpStatus.OK);
    }
    @PostMapping("/fi/ingredients/save")
    public ResponseEntity<String> saveProductIngredients(@RequestBody FiProductIngredient ingredient){
        ingredient.setCreatedAt(LocalDateTime.now());
        productIngredientRepository.save(ingredient);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @GetMapping("/fi/available-inventory/{ingredientId}")
    public ResponseEntity<FiIngredient> getAvailableInventory(@PathVariable String ingredientId){
        FiIngredient availableIngredient = plannedInventoryRepository.findByIngredientId(ingredientId);
        return new ResponseEntity<>(availableIngredient, HttpStatus.OK);
    }
    @PostMapping("/fi/inventory/save")
    public ResponseEntity<String> saveProductInventory(@RequestBody FiIngredient fiPlannedInventory){
        FiIngredient fiIngredient =  plannedInventoryRepository.findByIngredientId(fiPlannedInventory.getIngredientId());
        if(fiIngredient != null){
            fiIngredient.setRemainingQty(fiIngredient.getRemainingQty() + fiPlannedInventory.getQuantity());
            fiIngredient.setUpdatedAt(LocalDateTime.now());
            plannedInventoryRepository.save(fiIngredient);
        }else{
            fiPlannedInventory.setRemainingQty(fiPlannedInventory.getQuantity());
            fiPlannedInventory.setQuantity(fiPlannedInventory.getQuantity());
            fiPlannedInventory.setCreatedAt(LocalDateTime.now());
            fiPlannedInventory.setUpdatedAt(LocalDateTime.now());
            plannedInventoryRepository.save(fiPlannedInventory);
        }

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
    @PostMapping("/fi/inventory/overview")
    public ResponseEntity<OverviewResponseDTO> inventoryOverview(@RequestBody OverviewRequestDTO requestDTO){
//        LocalDateTime sdt = LocalDateTime.parse(requestDTO.getStartAt());
//        LocalDateTime edt = LocalDateTime.parse(requestDTO.getEndAt());
//        List<SqSale> sales = sqSalesRepository.findByUpdatedAtBetween(sdt, edt);
        //FiIngredient firstFi = plannedInventoryRepository.findFirstByMerchantId(requestDTO.getMerchantId());
//        requestDTO.setStartAt(firstFi.getUpdatedAt().toString());
//        if(firstFi.getSalesToDtTime() != null){
//            requestDTO.setEndAt(firstFi.getSalesToDtTime().toString());
//        }else{
//            requestDTO.setEndAt(LocalDateTime.now().toString());
//        }

        OverviewResponseDTO dto = fiInventoryService.inventoryOverview(requestDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
