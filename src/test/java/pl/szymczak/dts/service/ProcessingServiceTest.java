package pl.szymczak.dts.service;

import org.junit.Test;
import pl.szymczak.dts.domain.Event;
import pl.szymczak.dts.dto.EventState;
import pl.szymczak.dts.dto.LogEntry;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcessingServiceTest {

    private ProcessingService processingService = new ProcessingService(null);

    @Test
    public void shouldMapEmptyEventLogList() {
        //given
        final ArrayList<LogEntry> logEntries = new ArrayList<>();

        //when
        final List<Event> events = processingService.mapToEvents(logEntries);

        //then
        assertThat(events).isEmpty();
    }

    @Test
    public void shouldMapSinglePair() {
        //given
        final LogEntry event1 = new LogEntry("a", EventState.STARTED, null, null, 123);
        final LogEntry event2 = new LogEntry("a", EventState.FINISHED, null, null, 124);
        final ArrayList<LogEntry> logEntries = new ArrayList<>();
        logEntries.add(event1);
        logEntries.add(event2);

        //when
        final List<Event> events = processingService.mapToEvents(logEntries);

        //then
        assertThat(events).hasSize(1);
    }

    @Test
    public void shouldIgnoreIncorrectPairs() {
        //given
        final LogEntry event1 = new LogEntry("a", EventState.STARTED, null, null, 123);
        final ArrayList<LogEntry> logEntries = new ArrayList<>();
        logEntries.add(event1);

        //when
        final List<Event> events = processingService.mapToEvents(logEntries);

        //then
        assertThat(events).isEmpty();
    }

    @Test
    public void shouldNotTriggerAlarmWhenEventTakes4ms() {
        //given
        final LogEntry event1 = new LogEntry("a", EventState.STARTED, null, null, 120);
        final LogEntry event2 = new LogEntry("a", EventState.FINISHED, null, null, 124);
        final ArrayList<LogEntry> logEntries = new ArrayList<>();
        logEntries.add(event1);
        logEntries.add(event2);

        //when
        final List<Event> events = processingService.mapToEvents(logEntries);

        //then
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getAlert()).isFalse();
    }

    @Test
    public void shouldTriggerAlarmWhenEventTakes5ms() {
        //given
        final LogEntry event1 = new LogEntry("a", EventState.STARTED, null, null, 120);
        final LogEntry event2 = new LogEntry("a", EventState.FINISHED, null, null, 125);
        final ArrayList<LogEntry> logEntries = new ArrayList<>();
        logEntries.add(event1);
        logEntries.add(event2);

        //when
        final List<Event> events = processingService.mapToEvents(logEntries);

        //then
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getAlert()).isTrue();
    }

}
