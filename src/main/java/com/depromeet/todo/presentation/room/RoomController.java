package com.depromeet.todo.presentation.room;

import com.depromeet.todo.application.room.RoomService;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomType;
import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final RoomResponseAssembler roomResponseAssembler;

    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RoomResponse> createRoom(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @RequestBody @Valid CreateRoomRequest createRoomRequest
    ) {
        Room room = roomService.createRoom(
                memberId,
                RoomType.fromName(createRoomRequest.getRoomType())
        );
        RoomResponse roomResponse = roomResponseAssembler.toDisplayableRoom(room);
        return ApiResponse.successFrom(roomResponse);
    }

    @GetMapping("/rooms")
    public ApiResponse<RoomResponse> getRooms(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @ApiIgnore @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<RoomResponse> roomPage = roomService.getRooms(memberId, pageable)
                .map(roomResponseAssembler::toDisplayableRoom);
        return ApiResponse.successFrom(roomPage);
    }

    @GetMapping("/rooms/{roomId}")
    public ApiResponse<RoomResponse> getRoom(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @PathVariable Long roomId
    ) {
        Room room = roomService.getRoom(memberId, roomId);
        RoomResponse roomResponse = roomResponseAssembler.toDisplayableRoom(room);
        return ApiResponse.successFrom(roomResponse);
    }
}

