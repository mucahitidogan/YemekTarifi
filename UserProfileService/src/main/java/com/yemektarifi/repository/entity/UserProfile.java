package com.yemektarifi.repository.entity;

import com.yemektarifi.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class UserProfile extends Base {

    @Id
    private String userProfileId;
    private Long authId;
    private String name;
    private String surname;
    @Indexed(unique = true)
    private String email;
    private String password;
    @Indexed(unique = true)
    private String username;
    private String street;
    private String neighbourhood;
    private String district;
    private String province;
    private String country;
    private String buildingNumber;
    private String apartmentNumber;
    private String postCode;
    private String phone;
    private String avatar;
    @Builder.Default
    private EStatus status = EStatus.PENDING;
    @Builder.Default
    private List<String> favoriteRecipeIds = new ArrayList<>();

}
