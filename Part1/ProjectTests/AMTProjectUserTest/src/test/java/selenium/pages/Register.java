package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Register extends AMTProjectPages {
    By buttonRegister = By.id("btnRegister");
    By textFirstName = By.id("inputFirstName");
    By textLastName = By.id("inputLastName");
    By textEmail = By.id("inputEmail");
    By textPassword = By.id("inputPassword");

    public Register(WebDriver driver) {
        super(driver);

        if (!"Register Page".equals(driver.getTitle())) {
            throw new IllegalStateException("Wrong page");
        }
    }

    public Register typeFirstName(String firstName) {
        driver.findElement(textFirstName).sendKeys(firstName);
        return this;
    }

    public Register typeLastName(String lastName) {
        driver.findElement(textLastName).sendKeys(lastName);
        return this;
    }

    public Register typeEmail(String email) {
        driver.findElement(textEmail).sendKeys(email);
        return this;
    }

    public Register typePassword(String password) {
        driver.findElement(textPassword).sendKeys(password);
        return this;
    }

    public Page submitRegistration(Class<? extends Page> expectedPageClass, By button) {
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

    public By getButtonRegister() {
        return buttonRegister;
    }
}
