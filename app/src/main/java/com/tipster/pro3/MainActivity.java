package com.tipster.pro3;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        webView.addJavascriptInterface(new ApiBridge(), "AndroidApi");

        webView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public static class ApiBridge {

        private static final String BASE_URL =
                "https://v3.football.api-sports.io";

        @JavascriptInterface
        public String fixtures(String date, String timezone, String apiKey) {
            String path = "/fixtures?date=" + encode(date)
                    + "&timezone=" + encode(timezone);

            return request(path, apiKey);
        }

        @JavascriptInterface
        public String prediction(String fixtureId, String apiKey) {
            String path = "/predictions?fixture=" + encode(fixtureId);
            return request(path, apiKey);
        }

        @JavascriptInterface
        public String headToHead(String homeId, String awayId, String apiKey) {
            String path = "/fixtures/headtohead?h2h="
                    + encode(homeId + "-" + awayId)
                    + "&last=10";

            return request(path, apiKey);
        }

        @JavascriptInterface
        public String teamStats(
                String leagueId,
                String season,
                String teamId,
                String apiKey
        ) {
            String path = "/teams/statistics?league="
                    + encode(leagueId)
                    + "&season="
                    + encode(season)
                    + "&team="
                    + encode(teamId);

            return request(path, apiKey);
        }

        private static String request(String path, String apiKey) {

            if (apiKey == null || apiKey.trim().isEmpty()) {
                return "{\"errors\":{\"key\":\"Falta API key\"},\"response\":[]}";
            }

            HttpURLConnection connection = null;

            try {

                URL url = new URL(BASE_URL + path);

                connection =
                        (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(20000);

                connection.setRequestProperty(
                        "x-apisports-key",
                        apiKey.trim()
                );

                connection.setRequestProperty(
                        "Accept",
                        "application/json"
                );

                int code = connection.getResponseCode();

                InputStream input;

                if (code >= 200 && code < 300) {
                    input = connection.getInputStream();
                } else {
                    input = connection.getErrorStream();
                }

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        input,
                                        StandardCharsets.UTF_8
                                )
                        );

                StringBuilder result =
                        new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();

                return result.toString();

            } catch (Exception e) {

                return "{\"errors\":{\"network\":\""
                        + escape(e.getMessage())
                        + "\"},\"response\":[]}";

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        private static String encode(String value) {

            try {

                return URLEncoder.encode(
                        value == null ? "" : value,
                        "UTF-8"
                );

            } catch (Exception e) {

                return value == null ? "" : value;
            }
        }

        private static String escape(String value) {

            if (value == null) {
                return "Error desconocido";
            }

            return value
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"");
        }
    }
        }
