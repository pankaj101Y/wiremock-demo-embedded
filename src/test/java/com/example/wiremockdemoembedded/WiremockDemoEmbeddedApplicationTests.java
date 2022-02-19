package com.example.wiremockdemoembedded;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureWireMock(port = 9999)
class WiremockDemoEmbeddedApplicationTests {

    @Autowired
    MyController myController;

    @Test
    void test() {
        System.out.println(myController.call());
    }
}
