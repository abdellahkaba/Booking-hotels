package com.isi.booking.room;


import com.isi.booking.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repository;
    private final RoomMapper mapper;
    private final AwsS3Service awsS3Service ;
    private final FileStorageService fileStorageService;
    public Integer addRoom(RoomRequest request) {
        String imageUrl = awsS3Service.saveImageToS3(request.roomPhotoUrl());
        Room room = mapper.toRoom(request);
        room.setRoomPhotoUrl(imageUrl);
        return repository.save(room).getId();
    }
    public List<RoomResponse> getAllRooms() {
        return repository.findAll()
                .stream()
                .map(mapper::fromRoom)
                .collect(Collectors.toList());
    }

//    public void uploadRoomPhoto(
//            MultipartFile file,
//            Integer roomId
//    ){
//        Room room = repository.findById(roomId)
//                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription() + "ID:: " + roomId));
//        var filePicture = fileStorageService.saveFile(file,roomId);
//        room.setRoomPhotoUrl(filePicture);
//        repository.save(room);
//    }

}
