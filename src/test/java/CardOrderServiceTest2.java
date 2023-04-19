import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderServiceTest2 {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUpClass() {

        driver = new ChromeDriver();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldEmptyForm() {
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldEmptyNameField() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876545454");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNegativeValueNameField() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Petya");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876545454");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldEmptyPhoneField() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Васильев-Иванов");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNegativeValuePhoneField() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Петя");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89876545454");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldInactiveCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Петя");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89876545454");
        driver.findElement(By.cssSelector("button.button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__text")).getText().trim();
        assertEquals(expected, actual);
    }
}
