package com.depromeet.todo.presentation.room;

import com.depromeet.todo.application.room.RoomService;
import com.depromeet.todo.domain.room.Room;
import com.depromeet.todo.domain.room.RoomType;
import com.depromeet.todo.presentation.common.ApiResponse;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/members/me/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RoomResponse> createRoom(
            @RequestHeader(required = false, name = "Authorization") String authorization,
            @ApiIgnore @ModelAttribute("memberId") Long memberId,
            @RequestBody @Valid CreateRoomRequest createRoomRequest
    ) {
        Room room = roomService.createRoom(
                memberId,
                RoomType.valueOf(createRoomRequest.getRoomType())
        );
        RoomResponse roomResponse = roomResponseAssembler.toDisplayableMember(room);
        return ApiResponse.successFrom(roomResponse);
    }
}
