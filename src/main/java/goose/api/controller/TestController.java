package goose.api.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class TestController {

    public static String resolvePythonScriptPath(String filename) {
        File file = new File("config/" + filename);
        return file.getAbsolutePath();
    }
    public static List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

    @CrossOrigin
    @PostMapping("/getImg")
    public List<String> getImg(@RequestBody String requestBody) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String name = "config/" + jsonObject.getString("name") + ".py";
        String nodeSum = jsonObject.getString("nodeSum");
        String isH = jsonObject.getString("isH");
        String[] command = new String[] {"python",name, nodeSum ,isH};
        Process process = Runtime.getRuntime().exec(command);
        List<String> results = readProcessOutput(process.getInputStream());
        System.out.println("results = " + results);
        return results;
    }
}
