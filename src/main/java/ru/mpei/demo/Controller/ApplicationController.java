package ru.mpei.demo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mpei.demo.Service.ApplicationService;

@RestController
@Slf4j
public class ApplicationController {
    @Autowired
    private ApplicationService service;

    @PostMapping("/data/upload")
    public void uploadFile(@RequestParam MultipartFile file) {
//        service.parseMeasurementFile(file);
        log.info("File is uploaded!");
    };

    @GetMapping("/saveSetPoint")
    public void receiveSetPoint(@RequestParam double setPoint) {
        service.saveSetPoint(setPoint);
        log.info("SETpOINT is received: "+setPoint);
    }

    @GetMapping("/data/findFault")
    public void receiveSearchIndexes(@RequestParam int startIndex, int endIndex) {
        service.findFaultInInterval(startIndex, endIndex);
        log.info("SearchIndexes are received: "+ startIndex +", "+endIndex);
    }

}
