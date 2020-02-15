package com.depromeet.todo.presentation.furniture;

import com.depromeet.todo.application.furniture.DisplayableFurnitureAssembler;
import com.depromeet.todo.domain.furniture.Furniture;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FurnitureResponseAssembler implements DisplayableFurnitureAssembler {

    @Override
    public FurnitureResponse toDisplayableFurniture(Furniture furniture) {
        if (furniture == null) {
            return null;
        }
        FurnitureResponse furnitureResponse = new FurnitureResponse();
        furnitureResponse.setId(furniture.getId());
        furnitureResponse.setType(furniture.getFurnitureType().getName());
        furnitureResponse.setCreatedAt(furniture.getCreatedAt());
        furnitureResponse.setUpdatedAt(furniture.getUpdatedAt());
        return furnitureResponse;
    }

    public List<FurnitureResponse> toDisplayableFurniture(List<Furniture> furnitures) {
        return furnitures.stream()
                .map(this::toDisplayableFurniture)
                .collect(Collectors.toList());
    }
}
