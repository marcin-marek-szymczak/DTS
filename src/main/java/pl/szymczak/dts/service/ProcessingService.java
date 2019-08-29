package pl.szymczak.dts.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.szymczak.dts.domain.Event;
import pl.szymczak.dts.dto.LogEntry;
import pl.szymczak.dts.repository.EventRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
@Slf4j
public class ProcessingService {

    private EventRepository eventRepository;

    @Value("${dts.file.path}")
    private String filePath;
    @Value("${dts.file.name}")
    private String fileName;

    public ProcessingService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void processEvents() {
        List<LogEntry> logEntries = readLogEntriesFromFile();
        List<Event> events = mapToEvents(logEntries);
        eventRepository.saveAll(events);
    }

    private List<Event> mapToEvents(List<LogEntry> logEntries) {
        List<Event> events = new ArrayList<>();
        Map<String, List<LogEntry>> logsGroupedById = logEntries.stream().collect(groupingBy(LogEntry::getId));
        final int numberOfLogGroups = logsGroupedById.keySet().size();
        int processedSoFar = 0;
        for (String logId : logsGroupedById.keySet()) {
            List<LogEntry> currentLogEventPair = logsGroupedById.get(logId);
            if (currentLogEventPair.size() != 2) {
                log.warn("No matching event for event with id: {}", logId);
                continue;
            }
            int eventDuration = Math.abs(currentLogEventPair.get(0).getTimestamp() - currentLogEventPair.get(1).getTimestamp());
            events.add(new Event(logId, eventDuration,currentLogEventPair.get(0).getType(),currentLogEventPair.get(0).getHost(),eventDuration>4));
            if(processedSoFar%10000 == 0) {
                log.debug("mapping progress is {}% ", (double)processedSoFar/numberOfLogGroups*100.0);
            }
            processedSoFar++;
        }
        log.info("mapping has finished");
        return events;
    }

    private List<LogEntry> readLogEntriesFromFile(){
        log.debug("Reading log entries");
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(filePath + fileName)){
            return Arrays.asList(gson.fromJson(fileReader, LogEntry[].class));
        } catch (IOException e) {
            log.error("File does not exist", e);
        }
        return new ArrayList<>();
    }
}
