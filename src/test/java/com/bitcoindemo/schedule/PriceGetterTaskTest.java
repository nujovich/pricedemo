package com.bitcoindemo.schedule;

import static org.mockito.Mockito.atLeast;

import static org.mockito.Mockito.verify;

import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

@SpringJUnitConfig(SpringRunner.class)
public class PriceGetterTaskTest {

    @SpyBean
    private PriceGetterTask task;

    @Test
    public void test_getPrice() {
        Awaitility.await().atMost(Duration.TEN_SECONDS).untilAsserted(() -> verify(task, atLeast(1)).getPrice());
    }
    
}