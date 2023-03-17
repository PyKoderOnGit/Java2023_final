package ru.mpei.demo.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mpei.demo.Repository.ApplicationRepository;
import ru.mpei.demo.Repository.Measurement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class ApplicationService {

    @Autowired
    private ApplicationRepository appRepo;

    private double setPoint = 0.0;

    @SneakyThrows
    public void parseMeasurementFile(MultipartFile file) {
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = bufferedReader.readLine();
        line = bufferedReader.readLine();
        while (line != null){
            String[] stringParts = line.split(",");
            String time = stringParts[0];
            double ia = Double.parseDouble(stringParts[1]);
            double ib = Double.parseDouble(stringParts[2]);
            double ic = Double.parseDouble(stringParts[3]);
            appRepo.saveMeasurement(new Measurement(ia, ib, ic));
            log.info("Time = {}, ia = {}, ib = {}, ic = {}", time, ia, ib, ic);

            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        log.info("FIle read successfully");
    }

    public String findFaultInInterval(int startIndex, int endIndex){
        double ampSetPoint = Math.sqrt(2) * setPoint;
        HashSet<String> faultyPhasesSet = new HashSet<String>();

        List<Measurement> measurementsInInterval = appRepo.getMeasurementsInInterval(startIndex, endIndex);
        for (Measurement m : measurementsInInterval) {
//            log.info(m.toString());
//            log.info(String.valueOf(m.getIa()), String.valueOf(m.getIa()));
            if (Math.abs(m.getIa()) > ampSetPoint) {
                faultyPhasesSet.add("A");
            }
            if (Math.abs(m.getIb()) > ampSetPoint) {
                faultyPhasesSet.add("B");
            }
            if (Math.abs(m.getIc()) > ampSetPoint) {
                faultyPhasesSet.add("C");
            }
        }

        String faultyPhases = String.join("", faultyPhasesSet);
        return faultyPhases;
    };

    public void saveSetPoint (double newSetPoint){
        setPoint = newSetPoint;
        log.info("setPoint is updated: ", setPoint);
    };


}
