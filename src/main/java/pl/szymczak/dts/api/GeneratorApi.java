package pl.szymczak.dts.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.szymczak.dts.service.GenerationService;

@RestController
@Slf4j
@AllArgsConstructor
public class GeneratorApi {

    private GenerationService generationService;

    @PostMapping("/generate")
    public void generateFile(@RequestParam Integer amountOfEventPairs) {
        log.info("User wants to generate data for file");
        generationService.generateData(amountOfEventPairs);
    }
}
