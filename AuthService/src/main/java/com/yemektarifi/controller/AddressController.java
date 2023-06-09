package com.yemektarifi.controller;
import com.yemektarifi.dto.request.FromUserProfileServiceUpdateAddressRequestDto;
import com.yemektarifi.repository.entity.Address;
import com.yemektarifi.service.AddressService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yemektarifi.constants.ApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(ADDRESS)
public class AddressController  {

    private final AddressService addressService;

    @Hidden
    @PutMapping(UPDATE_ADDRESS)
    public ResponseEntity<Boolean> updateAddress(@RequestBody FromUserProfileServiceUpdateAddressRequestDto dto){
        return ResponseEntity.ok(addressService.updateAddress(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Address>> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }
}
