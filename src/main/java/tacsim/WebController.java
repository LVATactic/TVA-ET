package tacsim;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import tacsim.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.google.gson.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@RestController
@EnableAutoConfiguration
public class WebController {
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/tacsim", method = RequestMethod.POST, produces = "application/json")
    public String TacSim(
            @RequestParam(value="file", required = false) MultipartFile file,
            @RequestParam(value="threshold", required = false) String threshold
    ) throws IOException{
        Map<String, String> settings = new TreeMap<>();
        settings.put("threshold", "5");

        if (!threshold.equals("")) {
            settings.put("threshold", threshold);
        }

        System.out.println(settings.get("threshold"));

        String data = "";
        boolean usingFile = false;
        if(file != null){
            usingFile = true;
        }

        if(usingFile){
            System.out.println("USING CUSTOM DATA");
            InputStream stream = file.getInputStream();

            java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
            data = s.next();
        }

        HashMap<Integer, List<Double>> tactics = new HashMap<>();

        if(!usingFile) {
            System.out.println("USING OLD DATA");
            tactics = SampleData.getTactics();
        } else {
            String[] split_string = data.split("\\r?\\n");

            int index = 0;
            for(String row : split_string) {
                double[] numbers = Arrays.asList(row
                        .split(","))
                        .stream()
                        .map(String::trim)
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                ArrayList<Double> values = new ArrayList<>();
                for (Double num : numbers) {
                    values.add(num);
                }

                tactics.put(index++, values);
            }
        }

        long startTime = System.currentTimeMillis();
        Controller instance = new Controller(100, tactics, settings);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        ArrayList<ArrayList<Integer>> results = instance.getResults();
        ArrayList<Integer> baseline = results.get(0);
        ArrayList<Integer> proposed = results.get(1);

        Results r = new Results(results, instance.getDifferences(), usingFile, elapsedTime, settings);

        return new Gson().toJson(r, Results.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebController.class, args);
    }
}
