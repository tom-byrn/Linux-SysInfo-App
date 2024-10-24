package com.project.block1project;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import java.util.concurrent.atomic.AtomicInteger;



public class CPU {

    // Method to start monitoring CPU usage
    public static void runCpuUsageChart() {

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();

        // Create a new thread to monitor CPU usage
        Thread cpuUsageThread = new Thread(() -> {
            long[] prevTicks = processor.getSystemCpuLoadTicks();

            //Using an atomic integer so it can be accessed & modified while multithreading
            AtomicInteger time = new AtomicInteger();

            while (true) {
                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                    return;
                }

                long[] ticks = processor.getSystemCpuLoadTicks();
                double cpuUsage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100; // Convert to percentage
                prevTicks = ticks;

                // Update the chart on the JavaFX Application Thread
                Platform.runLater(() -> {
                    // Shift all existing data points to the left by 1 (x - 1)
                    for (XYChart.Data<Number, Number> data : HelloController.series.getData()) {
                        data.setXValue(data.getXValue().intValue() - 1);  // Shift left
                    }

                    // Add the new data point at the end (x=60)
                    HelloController.series.getData().add(new XYChart.Data<>(60, cpuUsage));

                    // Remove the oldest data point at x=0
                    if (HelloController.series.getData().size() > 60) {
                        HelloController.series.getData().remove(0);
                    }
                });


            }
        });

        cpuUsageThread.setDaemon(true); // Ensure the thread closes when the application exits
        cpuUsageThread.start();
    }
}

