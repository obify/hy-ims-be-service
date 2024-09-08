package com.obify.hy.ims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "locations")
public class Location {
    @Id
    private String id;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String street;
    private String locality;
    private String city;
    private String zipCode;
    private String province;
    private String country;
}
