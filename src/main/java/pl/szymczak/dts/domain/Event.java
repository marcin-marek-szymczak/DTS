package pl.szymczak.dts.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Event {
    private String id;
    private EventState state;
    private EventType type;
    private String host;
    private Long timestamp;
}
