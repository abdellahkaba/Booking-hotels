package com.isi.booking.room;


import com.isi.booking.exceptionHandler.BusinessErrorCodes;
import com.isi.booking.file.FileStorageService;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
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

    public List<String> getAllRoomTypes() {
        return repository.findDistinctRoomTypes();
    }

    public RoomResponse getRoomById(Integer roomId) {
        return repository.findById(roomId)
                .map(mapper::fromRoom)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription() + " ID " + roomId));
    }

    public void updateRoom(RoomUpdateRequest request) {
        String photo = null ;
        if (StringUtils.isNotBlank(String.valueOf(request.roomPhotoUrl()))){
            photo = awsS3Service.saveImageToS3(request.roomPhotoUrl());
        }
        var room = repository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription()));
        if (StringUtils.isNotBlank(request.roomType())){
            room.setRoomType(request.roomType());
        }
        if (request.roomPrice() != null){
            room.setRoomPrice(request.roomPrice());
        }
        if (StringUtils.isNotBlank(request.roomDescription())){
            room.setRoomDescription(request.roomDescription());
        }
        if (photo != null) {
            room.setRoomPhotoUrl(photo);
        }
        repository.save(room);
    }

    public void deleteRoom(Integer roomId) {
        var room = repository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription() + " ID :: " + roomId));
        repository.delete(room);
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
