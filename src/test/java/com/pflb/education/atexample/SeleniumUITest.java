package com.pflb.education.atexample;

import com.pflb.education.atexample.config.ApplicationConfig;
import com.pflb.education.atexample.page.LoginPage;
import com.pflb.education.atexample.page.NavBarElement;
import com.pflb.education.atexample.page.UserPage;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v115.performance.Performance;
import org.openqa.selenium.devtools.v115.performance.model.Metric;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;


@TestInstance(Lifecycle.PER_CLASS)
    public class SeleniumUITest {
    private ChromeDriver driver;

    private WebDriverWait wait;

    private ApplicationConfig config;

    private DevTools devTools;

    @BeforeAll
    public void configInit() {
        config = new ApplicationConfig();
    }

    @BeforeEach
    public void init() throws MalformedURLException {
        //System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        devTools = driver.getDevTools();
        devTools.createSession();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void loginTest() {
        driver.get(config.baseUrl);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.fillLoginInput(config.login);
        loginPage.fillPasswordInput(config.password);
        loginPage.clickLoginBtn();
        String alertText = loginPage.getAlertText();
        loginPage.dismissAlert();

        Assertions.assertTrue(alertText.contains("Successful"), "Alert text doesn't contains info about successful auth");
    }

    @Test
    public void userSortBySex_AscTest() {

        driver.get(config.baseUrl + "/read/users");
        UserPage userPage = new UserPage(driver, wait);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));

        NavBarElement navBarElement = new NavBarElement(driver, wait);

        //сортировка по возрастанию
        navBarElement.sortBySex();

        List<String> usersSexes = new ArrayList<>(userPage.parseUsersSex());

        List<String> usersSexesCopy = new ArrayList<>(usersSexes);

        Collections.sort(usersSexesCopy);

        Assertions.assertEquals(usersSexesCopy, usersSexes, "Sorting users by Sex column by Ascending in users table is incorrect");

    }

    @Test
    public void userSortBySex_DescTest() {

        driver.get(config.baseUrl + "/read/users");
        UserPage userPage = new UserPage(driver, wait);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));

        NavBarElement navBarElement = new NavBarElement(driver, wait);

        //сортировка по убыванию
        navBarElement.sortBySex();
        navBarElement.sortBySex();

        List<String> usersIds = new ArrayList<>(userPage.parseUsersSex());

        List<String> userIdsCopy = new ArrayList<>(usersIds);

        Collections.sort(userIdsCopy, Collections.reverseOrder());

        Assertions.assertEquals(userIdsCopy, usersIds, "Sorting users by Sex column by Descending in users table is incorrect");
    }

    @Test
    @Description("Получаем метрики производительности страницы с автомобилями")
    public void carsPageSpeedMetrics_Test(){
        devTools.send(Performance.enable(Optional.empty()));
        List<Metric> performanceMetrics = devTools.send(Performance.getMetrics());

        driver.get(config.baseUrl + "/read/cars");

        attachPerformanceMetrics(performanceMetrics);

    }

    @Attachment(value = "Performance metrics:", type = "text/html")
    public String attachPerformanceMetrics(List<Metric> perfMetrics) {

        List<String> perfMetricsStrList = new ArrayList<>();

        for (Metric metric : perfMetrics) {
            perfMetricsStrList.add(metric.getName() + "=" + metric.getValue());
        }

        return String.join("\n", perfMetricsStrList);
    }
}