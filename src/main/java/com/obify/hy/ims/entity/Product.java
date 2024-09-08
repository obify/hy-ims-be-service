package com.obify.hy.ims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private Double price;
    private Integer quantity;
    private String categoryId;
    private String managerId;
    @DocumentReference(collection = "locations")
    private List<Location> locations;
    private String vendorId;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private ECurrency currency;
}
