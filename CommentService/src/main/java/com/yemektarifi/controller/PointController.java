package com.yemektarifi.controller;

import com.yemektarifi.dto.request.CreatePointRequestDto;
import com.yemektarifi.dto.response.CreatePointResponseDto;
import com.yemektarifi.repository.entity.Point;
import com.yemektarifi.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.yemektarifi.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(POINT)
public class PointController {
    private final PointService pointService;

    @PostMapping(CREATE + "/{token}")
    public ResponseEntity<CreatePointResponseDto> createPoint(@PathVariable String token, @RequestBody @Valid CreatePointRequestDto dto){
        return ResponseEntity.ok(pointService.createPoint(token, dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Point>> findAll(){
        return ResponseEntity.ok(pointService.findAll());
    }

    @DeleteMapping(DELETE_BY_ID + "/{token}/{pointId}")
    public ResponseEntity<Boolean> deletePoint(@PathVariable String token, @PathVariable String pointId){
        return ResponseEntity.ok(pointService.deletePoint(token, pointId));
    }
}
