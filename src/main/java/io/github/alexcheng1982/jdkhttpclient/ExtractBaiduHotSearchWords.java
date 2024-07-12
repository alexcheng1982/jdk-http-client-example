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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract hot search words from Baidu
 */
public class ExtractBaiduHotSearchWords {

  List<String> extract() throws IOException, InterruptedException {
    HttpClient httpClient = HttpClient.newBuilder()
        .followRedirects(Redirect.NORMAL)
        .connectTimeout(Duration.ofMinutes(1))
        .version(Version.HTTP_1_1)
        .build();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://top.baidu.com/board"))
        .GET()
        .build();
    HttpResponse<String> response = httpClient.send(request,
        BodyHandlers.ofString());
    Pattern pattern = Pattern.compile(
        "\"word\":\"(.*?)\"",
        Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(response.body());
    List<String> result = new ArrayList<>();
    int count = 0;
    while (matcher.find() && count < 10) {
      result.add(matcher.group(1));
      count++;
    }
    return result;
  }

  public static void main(String[] args)
      throws IOException, InterruptedException {
    new ExtractBaiduHotSearchWords().extract().forEach(System.out::println);
  }
}
