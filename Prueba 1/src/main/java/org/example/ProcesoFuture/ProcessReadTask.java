package org.example.ProcesoFuture;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ProcessReadTask implements Callable<List<String>> {
    public InputStream inputStream;
    public ProcessReadTask(InputStream inputStream) {
        this.inputStream=inputStream;
    }

    @Override
    public List<String> call() throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(this.inputStream));
        return bf.lines().collect(Collectors.toList());
    }
}
