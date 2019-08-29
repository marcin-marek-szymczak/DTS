package pl.szymczak.dts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymczak.dts.domain.EventType;

@Data
@AllArgsConstructor
public class LogEntry {
    private String id;
    private EventState state;
    private EventType type;
    private String host;
    private Integer timestamp;
}
