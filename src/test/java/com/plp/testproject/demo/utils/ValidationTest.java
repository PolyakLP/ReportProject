package com.plp.testproject.demo.utils;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidationTest {

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        System.out.println("@BeforeEach - executes before each test method in this class");
    }

    @DisplayName("Single test successful")
    @Test
    void testSingleSuccessTest() {
        System.out.println("Success");
    }

    @Test
    @Disabled("Not implemented yet")
    void testShowSomething() {
    }

    @AfterEach
    void tearDown() {
        System.out.println("@AfterEach - executed after each test method.");
    }

    @AfterAll
    static void done() {
        System.out.println("@AfterAll - executed after all test methods.");
    }




}