package com.depromeet.todo.presentation.furniture;

import com.depromeet.todo.application.furniture.FurnitureApplicationService;
import com.depromeet.todo.domain.furniture.Furniture;
import com.depromeet.todo.domain.furniture.FurnitureType;
import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FurnitureController {
    private final FurnitureApplicationService furnitureApplicationService;
    private final FurnitureResponseAssembler furnitureResponseAssembler;

    @PostMapping("/rooms/{roomId}/furnitures")
    public ApiResponse<FurnitureResponse> createFurniture(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long roomId,
            @RequestBody @Valid CreateFurnitureRequest createFurnitureRequest
    ) {
        Furniture furniture = furnitureApplicationService.createFurniture(
                memberId,
                roomId,
                FurnitureType.fromName(createFurnitureRequest.getFurnitureType())
        );
        FurnitureResponse furnitureResponse = furnitureResponseAssembler.toDisplayableFurniture(furniture);
        return ApiResponse.successFrom(furnitureResponse);
    }

    @GetMapping("/rooms/{roomId}/furnitures")
    public ApiResponse<FurnitureResponse> getFurnitures(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long roomId,
            @ApiIgnore @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<FurnitureResponse> furniturePage = furnitureApplicationService.getFurnitures(memberId, roomId, pageable)
                .map(furnitureResponseAssembler::toDisplayableFurniture);
        return ApiResponse.successFrom(furniturePage);
    }

    @GetMapping("/rooms/{roomId}/furnitures/{furnitureId}")
    public ApiResponse<FurnitureResponse> getFurniture(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long roomId,
            @PathVariable Long furnitureId
    ) {
        Furniture furniture = furnitureApplicationService.getFurniture(memberId, roomId, furnitureId);
        FurnitureResponse furnitureResponse = furnitureResponseAssembler.toDisplayableFurniture(furniture);
        return ApiResponse.successFrom(furnitureResponse);
    }
}
