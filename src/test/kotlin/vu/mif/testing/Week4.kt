package vu.mif.testing

import com.codeborne.selenide.Selectors.byId
import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.*
import net.bytebuddy.asm.Advice.Enter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.openqa.selenium.Keys
import java.io.File
import java.time.Duration
import kotlin.random.Random
import com.codeborne.selenide.Selenide.`$` as S

class Week4 {
    companion object {
        var email = ""
        val pass = "Password1"

        @JvmStatic
        @BeforeAll
        fun createUser(): Unit {
            email = "someuser${Random.nextInt()}${Random.nextInt()}@lol.com"
            open("https://demowebshop.tricentis.com")
            S(byXpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[2]/a")).click()
            S(byXpath("/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[1]/div[1]/div[3]/input")).click()
            S(byId("gender-male")).click()
            S(byId("FirstName")).sendKeys("Tester")
            S(byId("LastName")).sendKeys("Tester")
            S(byId("Email")).sendKeys(email)
            S(byId("Password")).sendKeys(pass)
            S(byId("ConfirmPassword")).sendKeys(pass)
            S(byId("register-button")).click()
            S(byXpath("/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[2]/input")).click()
            webdriver().driver().webDriver.quit() // Close session
            Thread.interrupted()

        }
    }

    @Test
    fun file1() = doStuff("data1.txt")

    @Test
    fun file2() = doStuff("data2.txt")

    private fun doStuff(file: String) {
        open("https://demowebshop.tricentis.com")
        webdriver().driver().webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        S(byXpath("/html/body/div[4]/div[1]/div[1]/div[2]/div[1]/ul/li[2]/a")).click()
        S(byId("Email")).sendKeys(email)
        S(byId("Password")).sendKeys(pass)
        S(byXpath("/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[1]/div[2]/div[2]/form/div[5]/input")).click()


        S(byXpath("/html/body/div[4]/div[1]/div[4]/div[1]/div[1]/div[2]/ul/li[5]/a")).click()

        val lines = File(file).readLines()

        for (line in lines) {
            S(byXpath("//div[@class='item-box']//h2[@class='product-title']/a[text() = '$line']/ancestor::div[@class='product-item']//input[@type='button'] ")).click()
            Thread.sleep(200)
        }
        S(".cart-label").click()
        S(byId("termsofservice")).click()
        S(byId("checkout")).click()
        if (S("#billing-address-select").exists()) {
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

        S(byId("BillingNewAddress_City")).sendKeys("Random")
        S(byId("BillingNewAddress_Address1")).sendKeys("Random")
        S(byId("BillingNewAddress_ZipPostalCode")).sendKeys("12345")
        S(byId("BillingNewAddress_PhoneNumber")).sendKeys("12345")
        S(byXpath("//*[@id=\"billing-buttons-container\"]/input")).click()
        S(byXpath("//*[@id=\"payment-method-buttons-container\"]/input")).click()
        S(byXpath("//*[@id=\"payment-info-buttons-container\"]/input")).click()
        S(byXpath("//*[@id=\"confirm-order-buttons-container\"]/input")).click()
        Assertions.assertTrue(
            S(byXpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/div/div[1]/strong")).exists(),
            "Order did not go through"
        )
        webdriver().driver().webDriver.quit()
        Thread.interrupted()
    }
}

fun main() {
//    val t = Week4()
//    t.doStuff("d1.txt");
//    Thread.sleep(5000)
//    closeWindow()
//    closeWebDriver()
//    println("DONE")
}