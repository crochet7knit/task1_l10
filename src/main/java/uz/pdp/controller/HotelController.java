package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Hotel;
import uz.pdp.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getAll() {
        List<Hotel> hotelList = hotelRepository.findAll();
        return hotelList;
    }


    @GetMapping("/{id}")
    public Hotel getOne(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            return hotel;
        }
        return new Hotel();
    }


    @PostMapping
    public String create(@RequestBody Hotel hotel) {
        Hotel hotel1 = new Hotel();
        hotel1.setName(hotel.getName());
        hotelRepository.save(hotel1);
        return "Hotel saved!";
    }


    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, @RequestBody Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent()) {
            return "Not found hotel's id!";
        }
        Hotel hotel1 = optionalHotel.get();
        hotel1.setName(hotel.getName());


        hotelRepository.save(hotel1);
        return "Hotel updated!";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        boolean exists = hotelRepository.existsById(id);
        if (!exists) {
            return "not found given id";
        }
        hotelRepository.deleteById(id);
        return "HOTEL deleted!";
    }
}
