package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Home extends AMTProjectPages {

    By buttonProfile = By.id("userDropdown");
    By buttonLogout = By.id("btnLogout");
    By buttonListApp = By.id("btnListApp");

    public Home(WebDriver driver) {
        super(driver);

        if (!"AMT Project 2018".equals(driver.getTitle())) {
            System.out.println("Wrong page: " + driver.getTitle());
            throw new IllegalStateException("Wrong page: " + driver.getTitle());
        }
    }

    public Page submitHome(Class<? extends Page> expectedPageClass, By button) {
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

    public By getButtonProfile() {
        return buttonProfile;
    }

    public By getButtonLogout() {
        return buttonLogout;
    }

    public By getButtonListApp() { return buttonListApp; }
}
