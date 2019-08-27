package pl.szymczak.dts.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szymczak.dts.service.GenerationService;

@RestController
@Slf4j
@AllArgsConstructor
public class GeneratorApi {

    private GenerationService generationService;

    @GetMapping("/generate")
    public void generateFile(){
        log.info("User wants to generate data for file");
        generationService.generateData();
    }
}