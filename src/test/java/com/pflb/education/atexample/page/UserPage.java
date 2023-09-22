package com.pflb.education.atexample.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserPage extends AbstractPage {
    @FindBy(css = "table tbody tr")
    private List<WebElement> userRows;

    @FindBy(css = "#root table")
    private WebElement usertTable;
    private static final int SEX_COL = 4;

    public UserPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    private List<WebElement> findUsersTrs() {
        return usertTable.findElements(By.cssSelector("tbody tr"));
    }

    public List<String> parseUsersSex() {
        List<WebElement> usersTrs = findUsersTrs();
        List<String> usersSexesList = new ArrayList<>();
        for (WebElement usersTr : usersTrs) {
            WebElement sexColumn = usersTr.findElements(By.cssSelector("td")).get(SEX_COL);

            usersSexesList.add(sexColumn.getText());
        }
        return usersSexesList;
    }
}