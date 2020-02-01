package com.depromeet.todo.presentation.furniture;

import com.depromeet.todo.application.furniture.FurnitureService;
import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.furniture.FurnitureType;
import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FurnitureController {
    private final FurnitureService furnitureService;
    private final FurnitureResponseAssembler furnitureResponseAssembler;

    @PostMapping("/members/me/rooms/{roomId}/furnitures")
    public ApiResponse<FurnitureResponse> createFurniture(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long roomId,
            @RequestBody @Valid CreateFurnitureRequest createFurnitureRequest
    ) {
        Furniture furniture = furnitureService.createFurniture(
                memberId,
                roomId,
                FurnitureType.fromName(createFurnitureRequest.getFurnitureType())
        );
        FurnitureResponse furnitureResponse = furnitureResponseAssembler.toDisplayableFurniture(furniture);
        return ApiResponse.successFrom(furnitureResponse);
    }
}
