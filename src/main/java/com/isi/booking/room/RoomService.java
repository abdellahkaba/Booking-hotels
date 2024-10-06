package com.isi.booking.room;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repository;
    private final RoomMapper mapper;
    private final AwsS3Service awsS3Service ;
    public Integer addRoom(RoomRequest request) {
        String imageUrl = awsS3Service.saveImageToS3(request.roomPhotoUrl());
        Room room = mapper.toRoom(request);
        room.setRoomPhotoUrl(imageUrl);
        return repository.save(room).getId();
    }
}
