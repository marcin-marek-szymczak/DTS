package pl.szymczak.dts.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szymczak.dts.service.ProcessingService;

@RestController
@AllArgsConstructor
@Slf4j
public class ProcessorApi {

    private ProcessingService processingService;

    @PostMapping("/process")
    public void processEvents() {
        log.info("User wants to process events");
        processingService.processEvents();
    }
}
