package pl.szymczak.dts.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long technicalId;
    private String id;
    private Integer eventDuration;
    private EventType type;
    private String host;
    private Boolean alert;

    public Event(String id, Integer eventDuration, EventType type, String host, Boolean alert) {
        this.id = id;
        this.eventDuration = eventDuration;
        this.type = type;
        this.host = host;
        this.alert = alert;
    }
}
