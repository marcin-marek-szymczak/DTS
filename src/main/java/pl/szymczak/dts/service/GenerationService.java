package pl.szymczak.dts.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.szymczak.dts.domain.Event;
import pl.szymczak.dts.domain.EventState;
import pl.szymczak.dts.domain.EventType;

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
        ArrayList<Event> events = new ArrayList<>();
        for (int i = 0; i < amountOfEventPairs; i++) {
            generateEventPair(events);
        }
        Collections.shuffle(events);
        saveToFile(events);
    }

    void generateEventPair(ArrayList<Event> events) {
        String eventId = RandomStringUtils.random(10, true, false);
        boolean isApplicationLog = (new Random()).nextBoolean();
        EventType eventType = isApplicationLog ? EventType.APPLICATION_LOG : null;
        String hostName = isApplicationLog ? RandomStringUtils.random(5, false, true) : null;
        int minEventDuration = 1;
        int maxEventDuration = 10;
        int eventDuration = ThreadLocalRandom.current().nextInt(minEventDuration, maxEventDuration + 1);
        long taskStartEpochSecond = Instant.now().getEpochSecond();
        Event event1 = new Event(eventId, EventState.STARTED, eventType, hostName, taskStartEpochSecond);
        Event event2 = new Event(eventId, EventState.FINISHED, eventType, hostName, taskStartEpochSecond + eventDuration);

        events.add(event1);
        events.add(event2);
    }

    private void saveToFile(List<Event> event1) {
        try (Writer writer = new FileWriter(filePath + fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(event1, writer);
        } catch (IOException e) {
            log.error("Error during saving to file", e);
        }
    }


}
