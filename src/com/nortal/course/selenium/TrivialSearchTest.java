package com.nortal.course.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.util.List;

public class TrivialSearchTest {

    private WebDriver driver;

    private void initWebDriver() {
        driver = new FirefoxDriver();
    }


    @Test
    public void shouldOpenMainPage() {
        initWebDriver();

		/* protocol specification is important - http:// */
        driver.navigate().to("http://www.neti.ee"); // ~ driver.get("http://www.neti.ee");

        searchFor("Nortal");

            // verify something..
            List<WebElement> nortalLinks =
                    //driver.findElements(new ByChained(By.className("result-item"), By.partialLinkText("Nortal")));
                    driver.findElements(By.xpath("//ul[@class='results-type-2']//a[contains(.,'Nortal')]"));

        for (WebElement nortalLink : nortalLinks) {
            System.out.println(nortalLink.getAttribute("href"));
        }

        Assert.assertTrue("there should be more than 1 link with 'Nortal' word", nortalLinks.size() > 1);
            WebElement firstNortalLink = nortalLinks.get(0);

            Assert.assertTrue("First link in search result should lead to 'www.nortal.ee'",
                    firstNortalLink.getAttribute("href").contains("www.nortal.ee"));

            firstNortalLink.click();
            Assert.assertEquals("http://www.nortal.ee/", driver.getCurrentUrl());

            // finalize stuff, closes driver/browser
            driver.quit();
            }

    private void searchFor(String keyword) {
        WebElement queryInput =
               // driver.findElement(By.id("search-bar")).findElement(By.cssSelector("input.input[name='query']"));
                driver.findElement(By.xpath("//div[@id='search-bar']//input[@name='query']"));
        queryInput.sendKeys(keyword);

        //driver.findElement(By.cssSelector("form[action='/cgi-bin/otsing'] input.button[type='submit']")).click();
        driver.findElement(By.xpath("//form[@action='/cgi-bin/otsing']//input[@type='submit']")).click();
    }
}
