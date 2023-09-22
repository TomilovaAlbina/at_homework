package com.pflb.education.atexample.page;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends AbstractPage{
    @FindBy(css = "input[name=email]")
    private WebElement loginInput;

    @FindBy(css = "input[name=password]")
    private WebElement passwordInput;

    @FindBy(css = "button[type=submit]")
    private WebElement loginButton;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Заполнить поле логина login = {login}")
    public void fillLoginInput(String login){
        loginInput.clear();
        loginInput.sendKeys(login);
    }
    @Step("Заполнить поле пароля значением password = {password}")
    public void fillPasswordInput(String password){
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }
    @Step("Нажать GO ")
    public void clickLoginBtn(){
        loginButton.click();
    }
    private Alert findAlert(){
        return wait.until(driver -> driver.switchTo().alert());
    }
    public String getAlertText(){
        return findAlert().getText();
    }

    public void dismissAlert(){
        findAlert().dismiss();
    }
}
