package com.yemektarifi.service;

import com.yemektarifi.dto.request.FromUserProfileServiceUpdateAddressRequestDto;
import com.yemektarifi.exception.AuthManagerException;
import com.yemektarifi.exception.ErrorType;
import com.yemektarifi.mapper.IAddressMapper;
import com.yemektarifi.repository.IAddressRepository;
import com.yemektarifi.repository.entity.Address;
import com.yemektarifi.repository.entity.Auth;
import com.yemektarifi.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService extends ServiceManager<Address, Long> {

    private final IAddressRepository addressRepository;
    private final AuthService authService;

    public AddressService(IAddressRepository addressRepository, AuthService authService) {
        super(addressRepository);
        this.addressRepository = addressRepository;
        this.authService = authService;
    }

    public Boolean updateAddress(FromUserProfileServiceUpdateAddressRequestDto dto) {
        Optional<Auth> optionalAuth = authService.findById(dto.getAuthId());
        if(optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        if(optionalAuth.get().getAddressId() == null){
            Address address = IAddressMapper.INSTANCE.FromUserProfileServiceUpdateAddressRequestDtoToAddressSave(dto);
            save(address);
            optionalAuth.get().setAddressId(address.getAddressId());
            authService.update(optionalAuth.get());
        }else{
            Optional<Address> optionalAddress = findById(optionalAuth.get().getAddressId());
            if(optionalAddress.isEmpty())
                throw new AuthManagerException(ErrorType.ADDRESS_NOT_FOUND);
            Address address = IAddressMapper.INSTANCE.FromUserProfileServiceUpdateAddressRequestDtoToAddressUpdate(dto, optionalAddress.get());
            update(address);
        }
        return true;
    }

    public List<Address> findAll(){
        return findAll();
    }
}
