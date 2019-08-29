package pl.szymczak.dts.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.szymczak.dts.domain.Event;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ProcessingService {

    @Value("${dts.file.path}")
    private String filePath;
    @Value("${dts.file.name}")
    private String fileName;

    public void processEvents(){
        Gson gson = new Gson();

        try {
             List<Event> events =Arrays.asList(gson.fromJson(new FileReader(filePath+fileName), Event[].class));
             log.debug("events are {}",events);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
