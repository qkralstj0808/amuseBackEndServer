package com.example.amusetravelproejct.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@Component
@Endpoint(id = "heap-size")
public class HeapSizeEndpoint {

    @ReadOperation
    public String getMaxHeapSize() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();

        long maxHeapSize = heapMemoryUsage.getMax();
        long maxHeapSizeInMB = maxHeapSize / (1024 * 1024);

        return "Max Heap Size: " + maxHeapSizeInMB + " MB";
    }
}
