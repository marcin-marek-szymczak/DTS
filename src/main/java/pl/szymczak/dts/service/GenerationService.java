package pl.szymczak.dts.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.szymczak.dts.domain.Event;
import pl.szymczak.dts.dto.EventState;
import pl.szymczak.dts.domain.EventType;
import pl.szymczak.dts.dto.LogEntry;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class GenerationService {

    @Value("${dts.file.path}")
    private String filePath;
    @Value("${dts.file.name}")
    private String fileName;

    public void generateData(int amountOfEventPairs) {
        ArrayList<LogEntry> logEntries = new ArrayList<>();
        for (int i = 0; i < amountOfEventPairs; i++) {
            generateEventPair(logEntries);
        }
        Collections.shuffle(logEntries);
        saveToFile(logEntries);
    }

    void generateEventPair(ArrayList<LogEntry> logEntries) {
        String eventId = RandomStringUtils.random(10, true, false);
        boolean isApplicationLog = (new Random()).nextBoolean();
        EventType eventType = isApplicationLog ? EventType.APPLICATION_LOG : null;
        String hostName = isApplicationLog ? RandomStringUtils.random(5, false, true) : null;
        int minEventDuration = 1;
        int maxEventDuration = 10;
        int eventDuration = ThreadLocalRandom.current().nextInt(minEventDuration, maxEventDuration + 1);
        int taskStartEpochSecond = (int) Instant.now().getEpochSecond();
        LogEntry logEntry1 = new LogEntry(eventId, EventState.STARTED, eventType, hostName, taskStartEpochSecond);
        LogEntry logEntry2 = new LogEntry(eventId, EventState.FINISHED, eventType, hostName, taskStartEpochSecond + eventDuration);

        logEntries.add(logEntry1);
        logEntries.add(logEntry2);
    }

    private void saveToFile(List<LogEntry> logEntries) {
        try (Writer writer = new FileWriter(filePath + fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(logEntries, writer);
        } catch (IOException e) {
            log.error("Error during saving to file", e);
        }
    }


}
