package pl.szymczak.dts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szymczak.dts.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

}
