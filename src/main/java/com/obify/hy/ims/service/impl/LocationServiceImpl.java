package com.obify.hy.ims.service.impl;

import com.obify.hy.ims.dto.ErrorDTO;
import com.obify.hy.ims.dto.LocationDTO;
import com.obify.hy.ims.entity.Location;
import com.obify.hy.ims.exception.BusinessException;
import com.obify.hy.ims.repository.LocationRepository;
import com.obify.hy.ims.service.ImsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements ImsService<LocationDTO, LocationDTO> {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public LocationDTO add(LocationDTO input) {
        Location location = new Location();
        BeanUtils.copyProperties(input, location);
        location = locationRepository.save(location);
        BeanUtils.copyProperties(location, input);
        return input;
    }

    @Override
    public LocationDTO update(LocationDTO input, String id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(()->{
                    List<ErrorDTO> errors =
                            List.of(new ErrorDTO("LOC_001", "Error occurred while saving new location"));
                    return new BusinessException(errors);
                });
        BeanUtils.copyProperties(input, location);
        location = locationRepository.save(location);
        BeanUtils.copyProperties(location, input);
        return input;
    }

    @Override
    public String delete(String id) {
        locationRepository.deleteById(id);
        return "Deleted successfully with Id: "+id;
    }

    @Override
    public LocationDTO get(String id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(()->{
                    List<ErrorDTO> errors =
                            List.of(new ErrorDTO("LOC_003", "Error occurred while while finding the element"));
                    return new BusinessException(errors);
                });
        LocationDTO locationDTO = new LocationDTO();
        BeanUtils.copyProperties(location, locationDTO);
        return locationDTO;
    }

    @Override
    public List<LocationDTO> getAll() {
        List<Location> locations = locationRepository.findAll();
        LocationDTO locationDTO =  null;
        List<LocationDTO> dtos = new ArrayList<>();
        for(Location location: locations){
            locationDTO = new LocationDTO();
            BeanUtils.copyProperties(location, locationDTO);
            dtos.add(locationDTO);
        }
        return dtos;
    }

    @Override
    public List<LocationDTO> search(LocationDTO input) {

        List<Location> locations = null;
        if(input.getName() != null){
            locations = locationRepository.findAllByNameContaining(input.getName());
        }
        LocationDTO locationDTO =  null;
        List<LocationDTO> dtos = new ArrayList<>();
        for(Location location: locations){
            locationDTO = new LocationDTO();
            BeanUtils.copyProperties(location, locationDTO);;
            dtos.add(locationDTO);
        }
        return dtos;
    }
}
