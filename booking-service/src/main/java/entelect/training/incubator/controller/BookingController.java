package entelect.training.incubator.controller;

import entelect.training.incubator.model.Booking;
import entelect.training.incubator.model.dto.BookingRequestDto;
import entelect.training.incubator.model.dto.CustomerSearchDto;
import entelect.training.incubator.model.dto.ReferenceDto;
import entelect.training.incubator.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("booking")
public class BookingController {

    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> makeBooking(@RequestBody BookingRequestDto customer) {
        LOGGER.info("Processing customer creation request for customer={}", customer);

        final Booking savedCustomer = bookingService.createBooking(customer.toEntity());

        LOGGER.trace("Customer created");
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Booking> getCustomerById(@PathVariable Integer id) {
        LOGGER.info("Processing customer search request for customer id={}", id);
        Booking customer = this.bookingService.getBooking(id);

        if (customer != null) {
            LOGGER.trace("Found customer");
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }

        LOGGER.trace("Customer not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("search")
    public ResponseEntity<List<Booking>> searchByCustomerID(@RequestBody CustomerSearchDto customer)
    {
        return ResponseEntity.ok(bookingService.getBookingsByCustomerID(customer.getCustomerID()));
    }

    @PostMapping("search_ref")
    public ResponseEntity<List<Booking>> searchByReferenceNumber(@RequestBody ReferenceDto reference)
    {
        return ResponseEntity.ok(bookingService.getBookingsByReferenceNumber(reference.getReferenceNumber()));
    }



    @GetMapping("queue/{name}")
    public String queueTest(@PathVariable("name") String name)
    {
        bookingService.sendNotification(name);
        return name;
    }


}