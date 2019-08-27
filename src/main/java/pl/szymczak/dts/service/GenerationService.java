package pl.szymczak.dts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.szymczak.dts.domain.Event;
import pl.szymczak.dts.domain.EventState;
import pl.szymczak.dts.domain.EventType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

@Service
@Slf4j
public class GenerationService {

    @Value("${dts.file.path}")
    private String filePath;
    @Value("${dts.file.name}")
    private String fileName;

    public void generateData() {

        Event event1 = new Event("asdf", EventState.STARTED, EventType.APPLICATION_LOG, "1234", Instant.now().getEpochSecond());

        saveTextToFile(event1.toString());
    }

    private void saveTextToFile(String str) {
        log.debug("Writing data inside path {} in file with name {}", filePath, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+fileName))){
            writer.write(str);
        } catch (IOException e) {
            log.error("Can not write to file {}", e);
        }
    }
}
