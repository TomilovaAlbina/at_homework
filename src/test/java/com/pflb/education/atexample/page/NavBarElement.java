package com.pflb.education.atexample.page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavBarElement extends AbstractPage{
    @FindBy(css = "button[class='btn btn-secondary']:nth-child(3)")
    private WebElement ID_Sort_Button;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(4)")
    private WebElement FirstName_Sort_Button;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(5)")
    private WebElement LastName_Sort_Button;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(6)")
    private WebElement Age_Sort_Button;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(7)")
    private WebElement Sex_Sort_Button;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(8)")
    private WebElement Money_Sort_Button;

    public NavBarElement(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Нажать кнопку сортировки по полю Sex")
    public void sortBySex(){
        Sex_Sort_Button.click();
    }
}
