package pl.szymczak.dts.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.szymczak.dts.dto.LogEntry;
import pl.szymczak.dts.repository.EventRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public void processEvents() throws FileNotFoundException {
        Gson gson = new Gson();
        List<LogEntry> logEntries = Arrays.asList(gson.fromJson(new FileReader(filePath + fileName), LogEntry[].class));
        Map<String, List<LogEntry>> logsGroupedById = logEntries.stream().collect(groupingBy(LogEntry::getId));
        for (String logId : logsGroupedById.keySet()) {
            List<LogEntry> currentLogEventPair = logsGroupedById.get(logId);
            if (currentLogEventPair.size() != 2) {
                log.warn("No matching event for event with id: {}", logId);
                continue;
            }
            int eventDuration = Math.abs(currentLogEventPair.get(0).getTimestamp() - currentLogEventPair.get(1).getTimestamp());
            log.debug("event duration is {}", eventDuration);
        }



    }
}
