package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Hotel;
import uz.pdp.entity.Room;
import uz.pdp.payload.RoomDto;
import uz.pdp.repository.HotelRepository;
import uz.pdp.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Room> getAll() {
        List<Room> roomList = roomRepository.findAll();
        return roomList;
    }


    @GetMapping("/{id}")
    public Room getOne(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) {
        return new Room();
        }
            Room room = optionalRoom.get();
            return room;
    }

    @GetMapping("/byHotelId/{hotelId}")
    public List<Room> getAllByHotelId(@PathVariable Integer hotelId) {
        List<Room> allByHotelId = roomRepository.findAllByHotelId(hotelId);
        return allByHotelId;
    }

    @PostMapping
    public String create(@RequestBody RoomDto roomDto) {
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) {
            return "Not found id of hotel";
        }
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "ROOM SAVED!";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) {
            return "Not found id of room";
        }
        Room room = optionalRoom.get();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) {
            return "Not found id of hotel";
        }
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "Room updated!";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) {
            return "Not found id of room";
        }
        roomRepository.deleteById(id);
        return "ROOM DELETED!";
    }
}
