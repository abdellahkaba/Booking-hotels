package com.isi.booking.room;


import com.isi.booking.common.PageResponse;
import com.isi.booking.exception.AvailableRoomsByDateAndTypeException;
import com.isi.booking.handler.BusinessErrorCodes;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repository;
    private final RoomMapper mapper;
    private final AwsS3Service awsS3Service ;
    //private final FileStorageService fileStorageService;
    public Integer addRoom(RoomRequest request) {
        String imageUrl = awsS3Service.saveImageToS3(request.roomPhotoUrl());
        Room room = mapper.toRoom(request);
        room.setRoomPhotoUrl(imageUrl);
        return repository.save(room).getId();
    }
    public PageResponse getAllRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Room> rooms = repository.findAll(pageable);
        List<RoomResponse> roomResponses = rooms
                .stream()
                .map(mapper::fromRoom)
                .toList();
        return new PageResponse<>(
            roomResponses,
                rooms.getNumber(),
                rooms.getSize(),
                rooms.getTotalElements(),
                rooms.getTotalPages(),
                rooms.isFirst(),
                rooms.isLast()
        );
    }
    public PageResponse getAvailableRoomsByDateAndType(int page, int size, LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        if (checkInDate == null || roomType == null || roomType.isBlank() || checkOutDate == null){
            throw new AvailableRoomsByDateAndTypeException(BusinessErrorCodes.INVALID_AVAILABLE_ROOMS_BY_DATE_AND_TYPE.getDescription());
        }
        Pageable pageable = PageRequest.of(page,size, Sort.by("checkInDate").descending());
        Page<Room> rooms = repository.findAvailableRoomsByDatesAndTypes(pageable, checkInDate,checkOutDate,roomType);
        List<RoomResponse> roomResponses = rooms
                .stream()
                .map(mapper::fromRoom)
                .toList();
        return new PageResponse<>(
              roomResponses,
                rooms.getNumber(),
                rooms.getSize(),
                rooms.getTotalElements(),
                rooms.getTotalPages(),
                rooms.isFirst(),
                rooms.isLast()
        );
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
    public PageResponse getAvailableRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Room> rooms = repository.getAvailableRooms(pageable);
        List<RoomResponse> roomResponses = rooms
                .stream()
                .map(mapper::fromRoom)
                .toList();
       return new PageResponse<>(
               roomResponses,
                   rooms.getNumber(),
                   rooms.getSize(),
                   rooms.getTotalElements(),
                   rooms.getTotalPages(),
                   rooms.isFirst(),
                   rooms.isLast()
       );

    }




    /*public void uploadRoomPhoto(
            MultipartFile file,
            Integer roomId
    ){
        Room room = repository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(BusinessErrorCodes.ENTITY_NOT_FOUND.getDescription() + "ID:: " + roomId));
        var filePicture = fileStorageService.saveFile(file,roomId);
        room.setRoomPhotoUrl(filePicture);
        repository.save(room);
    }*/

}
