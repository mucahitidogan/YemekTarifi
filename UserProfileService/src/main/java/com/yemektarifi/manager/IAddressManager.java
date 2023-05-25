package com.yemektarifi.manager;

import com.yemektarifi.dto.request.ToAuthServiceUpdateAddressRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        url = "http://localhost:8080/api/v1/address",
        name = "userprofile-address"
)
public interface IAddressManager {

    @PutMapping("/update-address")
    public ResponseEntity<Boolean> updateAddress(@RequestBody ToAuthServiceUpdateAddressRequestDto dto);
}
