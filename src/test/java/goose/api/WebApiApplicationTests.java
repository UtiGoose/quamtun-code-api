package goose.api;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


@SpringBootTest
class WebApiApplicationTests {
    private static final String PATH = "D:\\workspace\\python\\liangzi\\main.py";

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

    @Test
    void test () throws IOException {
        String[] command = new String[] {"python", "D:/workspace/liangzi/web_api/config/main.py", "5" , "Y"};
        Process process = Runtime.getRuntime().exec(command);
        List<String> results = readProcessOutput(process.getInputStream());
        System.out.println("results = " + results);
    }


    @Test
    void testMethod1() throws IOException, InterruptedException
    {
        final ProcessBuilder processBuilder = new ProcessBuilder("python", PATH);
        processBuilder.redirectErrorStream(true);

        final Process process = processBuilder.start();

        final BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s = null;
        while ((s = in.readLine()) != null)
        {
            System.out.println(s);
        }

        final int exitCode = process.waitFor();
        System.out.println(exitCode == 0);
    }


    @Test
    public boolean testPython(String filePath){
        String command = "cmd.exe /c cd"
                + PATH //此处插入python文件的路径
                + "&& start python xxx.py " +
                "-f C:\\Users\\l00018749\\Desktop\\demo\\";//这里利用了python的命令行机制可以传入参数
        try {
            Process p = Runtime.getRuntime().exec(command);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
