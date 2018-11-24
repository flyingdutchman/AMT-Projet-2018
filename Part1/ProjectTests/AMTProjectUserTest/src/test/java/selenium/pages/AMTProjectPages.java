package selenium.pages;

import org.openqa.selenium.WebDriver;

/**
 * classe parente des pages de l'arborescence du projet AMT-Project
 */
public abstract class AMTProjectPages extends Page {

    protected WebDriver driver;

    public AMTProjectPages(WebDriver driver) {
        this.driver = driver;
    }
}
