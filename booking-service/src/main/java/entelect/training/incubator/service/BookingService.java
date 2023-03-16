package entelect.training.incubator.service;

import entelect.training.incubator.model.Booking;
import entelect.training.incubator.model.dto.CaptureRewardsRequestDto;
import entelect.training.incubator.model.dto.CaptureRewardsResponse;
import entelect.training.incubator.model.dto.Customer;
import entelect.training.incubator.model.dto.Flight;
import entelect.training.incubator.repository.BookingRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookingService extends WebServiceGatewaySupport {

    private final BookingRepository customerRepository;
    private JmsTemplate jmsTemplate;


    public BookingService(BookingRepository customerRepository,JmsTemplate jmsTemplate) {
        this.customerRepository = customerRepository;
        this.jmsTemplate = jmsTemplate;
    }

    public Booking createBooking(Booking booking) {
        RestTemplate restTemplate = new RestTemplate();
        String customerEndpoint = "http://localhost:8200/customers/" + booking.getCustomerId();
        String flightEndpoint = "http://localhost:8202/flights/" + booking.getFlightID();
        Customer customer = restTemplate.getForObject(customerEndpoint, Customer.class);
        Flight flight = restTemplate.getForObject(flightEndpoint, Flight.class);


        if(customer == null || flight == null)
        {
            return null;
        }
        else
        {
            return customerRepository.save(booking);
        }

    }


    public List<Booking> getCustomers() {
        Iterable<Booking> customerIterable = customerRepository.findAll();

        List<Booking> result = new ArrayList<>();
        customerIterable.forEach(result::add);

        return result;
    }

    public Booking getBooking(Integer id) {
        Optional<Booking> customerOptional = customerRepository.findById(id);
        return customerOptional.orElse(null);
    }

    public List<Booking> getBookingsByCustomerID(Integer id) {
        List<Booking> customerOptional = customerRepository.findByCustomerId(id);
        return customerOptional;
    }

    public List<Booking> getBookingsByReferenceNumber(String refNumber) {
        List<Booking> customerOptional = customerRepository.findByReferenceNumber(refNumber);
        return customerOptional;
    }

    public void sendSomething()
    {
        //getWebServiceTemplate().marshalSendAndReceive(request);
        CaptureRewardsRequestDto requestDto = new CaptureRewardsRequestDto();
        CaptureRewardsResponse response = (CaptureRewardsResponse) getWebServiceTemplate().marshalSendAndReceive(requestDto);
        System.out.println(response);
    }

    public void sendNotification(String message)
    {
        jmsTemplate.convertAndSend("CoolNewQueue",message);
    }
}