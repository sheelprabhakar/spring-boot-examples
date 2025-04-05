package org.c4c.mysql;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TestExecutor {
    private static final String API_URL = "http://localhost:8080/users"; // Replace with your actual API URL
    private static final int THREAD_COUNT = 50;
    private static final int TENANT_COUNT = 500;

    private static final AtomicLong totalResponseTime = new AtomicLong(0); // To track total response time
    private static final AtomicLong requestCount = new AtomicLong(0); // To track the number of requests

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 1; i <= TENANT_COUNT; i++) {
            final int tenantId = i;
            executorService.submit(() -> {
                try {
                    createUserForTenant(tenantId);
                } catch (Exception e) {
                    System.err.println("Error creating user for tenant_" + tenantId + ": " + e.getMessage());
                }
            });

            if(i ==499){
                Thread.sleep(100);
                i=0;
            }
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.MINUTES)) {
                System.err.println("Executor did not terminate in the specified time.");
            }
        } catch (InterruptedException e) {
            System.err.println("Executor was interrupted: " + e.getMessage());
        }

        // Calculate and print the average response time
        if (requestCount.get() > 0) {
            double averageResponseTime = totalResponseTime.get() / (double) requestCount.get();
            System.out.println("Average Response Time: " + averageResponseTime + " ms");
        } else {
            System.out.println("No requests were processed.");
        }
    }

    private static void createUserForTenant(int tenantId) throws Exception {
        String tenantHeader = "tenant_" + tenantId;
        String randomUsername = "user_" + tenantId + "_" + System.currentTimeMillis();
        String requestBody = String.format(
            "{\"username\":\"%s\",\"password\":\"password123\",\"email\":\"%s@example.com\",\"tenantId\":\"%s\"}",
            randomUsername, randomUsername, tenantHeader
        );

        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("tenant_id", tenantHeader);
        connection.setDoOutput(true);

        long startTime = System.currentTimeMillis(); // Start time for the request

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes());
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        long endTime = System.currentTimeMillis(); // End time for the request

        long responseTime = endTime - startTime; // Calculate response time
        totalResponseTime.addAndGet(responseTime); // Add to total response time
        requestCount.incrementAndGet(); // Increment the request count

        if (responseCode == 200 || responseCode == 201) {
            System.out.println("User created successfully for tenant: " + tenantHeader + " (Response Time: " + responseTime + " ms)");
        } else {
            System.err.println("Failed to create user for tenant: " + tenantHeader + ". Response code: " + responseCode + " (Response Time: " + responseTime + " ms)");
        }

        connection.disconnect();
    }
}
