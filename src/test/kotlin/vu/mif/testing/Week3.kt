package vu.mif.testing

import com.codeborne.selenide.ClickOptions
import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.SelenideWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration
import com.codeborne.selenide.Selenide.`$` as S

class Week3 {
    companion object {
        val clickable =
            mapOf(
                "consent" to "/html/body/div[3]/div[2]/div[1]/div[2]/div[2]/button[1]",
                "widgets" to "//*[@id=\"app\"]/div/div/div[2]/div/div[4]",
                "progressBar" to "//span[text()='Progress Bar']",
                "startProgress" to "//*[@id=\"startStopButton\"]",
                "reset" to "//*[@id=\"resetButton\"]",

                "elements" to "//*[@id=\"app\"]/div/div/div[2]/div/div[1]",
                "add" to "//*[@id=\"addNewRecordButton\"]"
                )
        val observed =
            mapOf(
                "progress" to "//*[@id=\"progressBar\"]/div"
            )
    }

    fun test1() {
        open("https://demoqa.com")
        webdriver().driver().webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        webdriver().driver().webDriver.manage().window().maximize()
        S(byXpath(clickable["consent"]!!)).click()
        S(byXpath(clickable["widgets"]!!)).click()

        val wait = SelenideWait(webdriver().driver().webDriver, Duration.ofSeconds(20).toMillis(),100)


        S(byXpath(clickable["progressBar"]!!)).click(ClickOptions.usingJavaScript())
        S(byXpath(clickable["startProgress"]!!)).click()


        wait.until(ExpectedConditions.textToBePresentInElementLocated(byXpath(observed["progress"]!!), "100%"))
        S(byXpath(clickable["reset"]!!)).click(ClickOptions.usingJavaScript())
        assert(S(byXpath(observed["progress"]!!)).text() == "0%")

    }

    fun test2() {
        open("https://demoqa.com")
        webdriver().driver().webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        webdriver().driver().webDriver.manage().window().maximize()
        S(byXpath(clickable["consent"]!!)).click()

        S(byXpath(clickable["elements"]!!)).click()
        S(byXpath("//*[@id=\"item-3\"]")).click(ClickOptions.usingJavaScript())

        repeat(8) {
            S(byXpath(clickable["add"]!!)).click()
            S(byXpath("//*[@id=\"firstName\"]")).sendKeys("Name")
            S(byXpath("//*[@id=\"lastName\"]")).sendKeys("Lastname")
            S(byXpath("//*[@id=\"userEmail\"]")).sendKeys("test@test.com")
            S(byXpath("//*[@id=\"age\"]")).sendKeys("21")
            S(byXpath("//*[@id=\"salary\"]")).sendKeys("10000")
            S(byXpath("//*[@id=\"department\"]")).sendKeys("IDK")
            S(byXpath("//*[@id=\"submit\"]")).click()
        }


        S(byXpath("//button[text()='Next'][1]")).click()
        S(byXpath("//*[@title='Delete'][1]")).click(ClickOptions.usingJavaScript())

        check(S(byXpath("//span[@class='-totalPages'][1]")).text() == "1")
    }
}

fun main() {
    val t = Week3()

    t.test1()
    closeWindow()

    t.test2()
    closeWindow()
    closeWebDriver()
    println("DONE")
}