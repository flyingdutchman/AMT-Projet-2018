package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends AMTProjectPages {
    By inputEmail = By.id("inputEmail");
    By inputPassword = By.id("inputPassword");
    By buttonLogin = By.id("btnLogin");
    By buttonRegister = By.id("registerClickable");

    public Login(WebDriver driver) {
        super(driver);

        if (!"Login Page".equals(driver.getTitle())) {
            throw new IllegalStateException("Wrong page");
        }
    }

    public Login typeEmail(String email) {
        driver.findElement(inputEmail).sendKeys(email);
        return this;
    }

    public Login typePassword(String password) {
        driver.findElement(inputPassword).sendKeys(password);
        return this;
    }

    public Page submitLogin(Class<? extends Page> expectedPageClass, By button) {
        driver.findElement(button).click();
        Page targetPage = null;
        try {
            targetPage = expectedPageClass.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
        return targetPage;
    }

    public Login submitFormFailure() {
        driver.findElement(buttonLogin).click();
        return this;
    }

    public By getButtonLogin() {
        return buttonLogin;
    }

    public By getButtonRegister() {
        return buttonRegister;
    }
}
