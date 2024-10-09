package org.example.ProcesoFuture;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ProcessBuilder ps = new ProcessBuilder();

        ps.command("bash","-c","traceroute google.com");
        ExecutorService pool= Executors.newSingleThreadExecutor();
        try{
            Process process = ps.start();

            ProcessReadTask task = new ProcessReadTask(process.getInputStream());

            Future<List<String>> future = pool.submit(task);
            System.out.println("Tareas...");
            Thread.sleep(1000);

            System.out.println("fin de tareas...");
            List<String> result = future.get(5,TimeUnit.SECONDS);
            result.forEach(line -> System.out.println(line));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }catch (  InterruptedException e2){
            System.out.println(e2.getStackTrace());

    } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }}
