package io.github.alexcheng1982.jdkhttpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

/**
 * Save web page to a local file
 */
public class AsyncSaveToFile {

  void run() throws ExecutionException, InterruptedException, IOException {
    HttpClient httpClient = HttpClient.newBuilder()
        .followRedirects(Redirect.NORMAL)
        .connectTimeout(Duration.ofMinutes(1))
        .version(Version.HTTP_1_1)
        .build();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://www.baidu.com"))
        .GET()
        .build();
    Path savedFile = Files.createTempFile("jdk-http-client-", ".html");
    HttpResponse<Path> response = httpClient.sendAsync(request,
            BodyHandlers.ofFile(savedFile))
        .get();
    System.out.println("Response saved to " + response.body());
  }

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, IOException {
    new AsyncSaveToFile().run();
  }
}
