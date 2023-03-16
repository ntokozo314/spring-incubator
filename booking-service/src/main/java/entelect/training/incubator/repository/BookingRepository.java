package entelect.training.incubator.repository;

import entelect.training.incubator.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Integer> {

    List<Booking> findByCustomerId(Integer customerId);
    List<Booking> findByReferenceNumber(String referenceNumber);

}
