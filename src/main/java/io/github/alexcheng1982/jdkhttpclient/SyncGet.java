package io.github.alexcheng1982.jdkhttpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

/**
 * Get web page content and display on console
 */
public class SyncGet {

  void run() throws IOException, InterruptedException {
    HttpClient httpClient = HttpClient.newBuilder()
        .followRedirects(Redirect.NORMAL)
        .connectTimeout(Duration.ofMinutes(1))
        .version(Version.HTTP_1_1)
        .build();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://www.baidu.com"))
        .GET()
        .build();
    HttpResponse<String> response = httpClient.send(request,
        BodyHandlers.ofString());
    System.out.println(response.body());
  }

  public static void main(String[] args)
      throws IOException, InterruptedException {
    new SyncGet().run();
  }
}
