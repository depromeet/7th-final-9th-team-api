package com.depromeet.todo.presentation.furniture;

import com.depromeet.todo.application.furniture.DisplayableFurnitureAssembler;
import com.depromeet.todo.domain.furniture.Furniture;
import org.springframework.stereotype.Component;

@Component
public class FurnitureResponseAssembler implements DisplayableFurnitureAssembler {
    @Override
    public FurnitureResponse toDisplayableFurniture(Furniture furniture) {
        if (furniture == null) {
            return null;
        }
        FurnitureResponse furnitureResponse = new FurnitureResponse();
        furnitureResponse.setId(furniture.getFurnitureId());
        furnitureResponse.setType(furniture.getFurnitureType().getName());
        furnitureResponse.setCreatedAt(furniture.getCreatedAt());
        furnitureResponse.setUpdatedAt(furniture.getUpdatedAt());
        return furnitureResponse;
    }
}
