package vu.mif.testing

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selectors.byId
import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.Selenide.webdriver
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.Keys
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.time.Duration
import kotlin.random.Random
import com.codeborne.selenide.Selenide.`$` as S

class Week4 {
    companion object {
        var email = ""
        val pass = "Password1"
        val logger: Logger = LoggerFactory.getLogger(Week4::class.java)

        @JvmStatic
        @BeforeAll
        fun createUser(): Unit {
            Configuration.browserCapabilities = ChromeOptions().apply {
                addArguments("--headless")
            }
            email = "someuser${Random.nextInt()}${Random.nextInt()}@lol.com"
            logger.info("Generated email: $email")
            open("https://demowebshop.tricentis.com")
            S(byXpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[2]/a")).click()
            S(byXpath("/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[1]/div[1]/div[3]/input")).click()
            logger.info("Clicked some buttons...")
            S(byId("gender-male")).click()
            S(byId("FirstName")).sendKeys("Tester")
            S(byId("LastName")).sendKeys("Tester")
            S(byId("Email")).sendKeys(email)
            S(byId("Password")).sendKeys(pass)
            S(byId("ConfirmPassword")).sendKeys(pass)
            S(byId("register-button")).click()
            logger.info("Clicked register...")
            S(byXpath("/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[2]/input")).click()
            logger.info("Quiting sesssion...")
            webdriver().driver().webDriver.quit() // Close session
        }
    }

    @BeforeEach
    fun configDriver() {
        Configuration.browserCapabilities = ChromeOptions().apply {
            addArguments("--headless")
        }
    }

    @Test
    fun file1() = doStuff("data1.txt")

    @Test
    fun file2() = doStuff("data2.txt")

    private fun doStuff(file: String) {
        open("https://demowebshop.tricentis.com")
        webdriver().driver().webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        logger.info("Got webdriver, used implicit wait of 10 seconds...")
        S(byXpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[2]/a")).click()
        S(byId("Email")).sendKeys(email)
        S(byId("Password")).sendKeys(pass)
        S(byXpath("/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[1]/div[2]/div[2]/form/div[5]/input")).click()

        logger.info("Clicked some buttons...")

        S(byXpath("/html/body/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ul/li[5]/a")).click()

        val lines = File(file).readLines()
        logger.info("Read from file $file")

        for (line in lines) {
            S(byXpath("//div[@class='item-box']//h2[@class='product-title']/a[text() = '$line']/ancestor::div[@class='product-item']//input[@type='button'] ")).click()
            logger.info("Clicked on $line, now waiting 200ms...")
            Thread.sleep(200)
        }
        S(".cart-label").click()
        S(byId("termsofservice")).click()
        S(byId("checkout")).click()
        if (S("#billing-address-select").exists()) {
            logger.info("Billing address select exists...")
            S("#billing-address-select").apply {
                click()
                sendKeys("n")
                sendKeys(Keys.ENTER)
            }
        }
        S(byXpath("//*[@id=\"BillingNewAddress_CountryId\"]")).apply {
            click()
            sendKeys("lit")
            sendKeys(Keys.ENTER)
        }
        logger.info("Sent some keys...")

        S(byId("BillingNewAddress_City")).sendKeys("Random")
        S(byId("BillingNewAddress_Address1")).sendKeys("Random")
        S(byId("BillingNewAddress_ZipPostalCode")).sendKeys("12345")
        S(byId("BillingNewAddress_PhoneNumber")).sendKeys("12345")
        S(byXpath("//*[@id=\"billing-buttons-container\"]/input")).click()
        S(byXpath("//*[@id=\"payment-method-buttons-container\"]/input")).click()
        S(byXpath("//*[@id=\"payment-info-buttons-container\"]/input")).click()
        S(byXpath("//*[@id=\"confirm-order-buttons-container\"]/input")).click()
        logger.info("Performing assertion...")
        Assertions.assertTrue(
            S(byXpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/div/div[1]/strong")).exists(),
            "Order did not go through"
        )
        logger.info("Quiting session...")
        webdriver().driver().webDriver.quit()
    }
}