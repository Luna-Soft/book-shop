package com.zutode.bookshopclone.integration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "com.zutode.bookshopclone.integration.stepdefs"
)


public class BookshopcloneApplicationTests {

}
