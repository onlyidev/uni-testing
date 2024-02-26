package vu.mif.testing

import com.codeborne.selenide.Selectors
import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.open
import org.openqa.selenium.Keys

fun main() {
    open("https://demowebshop.tricentis.com")
    val xpaths = mapOf(
        "giftCards" to "//a[@href='/gift-cards']",
        "moreThan99" to "//span[contains(@class, 'actual-price') and number(text())>99][1]/ancestor::div[contains(@class, 'item-box')][1]//a[1]",
        "recipientName" to "//input[contains(@class, 'recipient-name')][1]",
        "senderName" to "//input[contains(@class, 'sender-name')][1]",
        "qtyInput" to "//input[contains(@class, 'qty-input')][1]",
        "addToCart" to "//input[@type='button' and contains(@class,'add-to-cart-button')][1]",
        "addToWishlist" to "//input[@type='button' and contains(@class,'add-to-wishlist-button')][1]",
        "jewelry" to "//a[@href='/jewelry']",
        "create-it-yourself-jewelry" to "//a[@href='/create-it-yourself-jewelry']",
        "material-select" to "//select[1]",
        "wishlist" to "//a[@href='/wishlist']",
        "addToCartCheckboxes" to "//input[@type='checkbox' and @name='addtocart']",
        "addToCartButtonLast" to "//input[@type='checkbox' and @name='addtocartbutton']"
    )
    `$`(byXpath(xpaths["giftCards"]!!)).click()
    `$`(byXpath(xpaths["moreThan99"]!!)).click()
    `$`(byXpath(xpaths["recipientName"]!!)).sendKeys("Test recipient name")
    `$`(byXpath(xpaths["senderName"]!!)).sendKeys("Test sender name")
    `$`(byXpath(xpaths["qtyInput"]!!)).apply {
        sendKeys(Keys.chord(Keys.CONTROL, "a"))
        sendKeys("5000")
    }
    `$`(byXpath(xpaths["addToCart"]!!)).click()
    Thread.sleep(1000)
    `$`(Selectors.byId("add-to-wishlist-button-4")).apply {
        sendKeys("a")
        sendKeys(Keys.ENTER)
    }
    `$`(byXpath(xpaths["jewelry"]!!)).click()
    `$`(byXpath(xpaths["create-it-yourself-jewelry"]!!)).click()
    `$`(byXpath(xpaths["material-select"]!!)).selectOptionContainingText("Silver (1")
    `$`(Selectors.byId("product_attribute_71_10_16")).sendKeys("80")
    `$`(Selectors.byId("product_attribute_71_11_17_50")).click()
    `$`(byXpath(xpaths["qtyInput"]!!)).apply {
        sendKeys(Keys.chord(Keys.CONTROL, "a"))
        sendKeys("26")
    }
    `$`(byXpath(xpaths["addToCart"]!!)).click()
    Thread.sleep(1000)

    `$`(Selectors.byId("add-to-wishlist-button-71")).apply {
        sendKeys("a")
        sendKeys(Keys.ENTER)
    }

    `$`(byXpath(xpaths["wishlist"]!!)).click()
    `$`(byXpath("(" + xpaths["addToCartCheckboxes"]!! + ")[1]")).click()
    `$`(byXpath("(" + xpaths["addToCartCheckboxes"]!! + ")[2]")).click()

    `$`(byXpath(xpaths["addToCartButtonLast"]!!)).click()
    assert(`$`(byXpath("//span[@class='product-price'][1]")).text == "18200.00")
}