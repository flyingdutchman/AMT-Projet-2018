package selenium;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.probedock.client.annotations.ProbeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.pages.*;

public class AMTProjectTest {

    private String baseURL = "http://localhost:8080/AMT-Project/";
    private WebDriver driver;
    private String validMail = "jimmy.verdasca@heig-vd.ch";
    private String validPassword = "asdf";

    public void create25Application() {
        driver.get(baseURL + "apps/new");
        Login login = new Login(driver);
        login.typeEmail(validMail);
        login.typePassword(validPassword);
        Home home = (Home)login.submitLogin(Home.class, login.getButtonLogin());
        Apps apps = (Apps)login.submitLogin(Apps.class, home.getButtonListApp());
        AppNew appNew = (AppNew) apps.submitNewApp(AppNew.class, apps.getButtonNew());
        for (int i = 0; i < 25; i++) {
            appNew.typeName("newApp");
            appNew.typeDescription("blabla just a description");
            apps = (Apps) appNew.submitNewApp(Apps.class, appNew.getButtonSubmitNewApp());
            appNew = (AppNew) apps.submitNewApp(AppNew.class, apps.getButtonNew());
        }
    }


    @Before
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nathan\\Desktop\\DocHEIG-VD\\3emeAnnee\\AMT\\Labos\\Projet\\ProjectTests\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void canCreateAnAccount() {
        driver.get(baseURL);
        Login login = new Login(driver);
        Register register = (Register)login.submitLogin(Register.class, login.getButtonRegister());

        //create random user
        String generatedString = RandomStringUtils.random(10, true, true);
        register.typeFirstName(generatedString);
        register.typeLastName(generatedString);
        register.typeEmail(generatedString + "@gmail.ch");
        register.typePassword(generatedString);
        Login newLogin = (Login)register.submitRegistration(Login.class, register.getButtonRegister());

        //login with this user to be sure he has been created
        newLogin.typeEmail(generatedString + "@gmail.ch");
        newLogin.typePassword(generatedString);
        Home home = (Home)newLogin.submitLogin(Home.class, newLogin.getButtonLogin());
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void canLogIn() {
        driver.get(baseURL);
        Login login = new Login(driver);
        login.typeEmail(validMail);
        login.typePassword(validPassword);
        Home home = (Home)login.submitLogin(Home.class, login.getButtonLogin());
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void userCanCreateApplications() {
        create25Application();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void userCanBrowseThoughPaginationOfApplications() {
        create25Application();
        //retourne dans la liste des applications ou devraient appara'itre les 10 premiers
        driver.get(baseURL + "apps");
        Apps apps = new Apps(driver);
        //click sur next pagination (11-20)
        apps.submitNewApp(Apps.class, apps.getButtonNext());
        //clieck sur next pagination (21-25)
        apps.submitNewApp(Apps.class, apps.getButtonNext());
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void canLogOut() {
        driver.get(baseURL);
        Login login = new Login(driver);
        login.typeEmail(validMail);
        login.typePassword(validPassword);
        Home home = (Home)login.submitLogin(Home.class, login.getButtonLogin());

        // clique sur le bouton profile
        Home newHome = (Home)home.submitHome(Home.class, home.getButtonProfile());
        //clique sur le bouton logout
        Login newLogin = (Login)newHome.submitHome(Login.class, newHome.getButtonLogout());
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void unidentifiedUserTryingAccesListAppPageIsRedirectedToLoginAndThenToAppsOnceLogged() {
        driver.get(baseURL + "apps");
        Login login = new Login(driver);
        login.typeEmail(validMail);
        login.typePassword(validPassword);
        Apps apps = (Apps)login.submitLogin(Apps.class, login.getButtonLogin());
    }

    @After
    public void closeBrowser() {
        driver.close();
    }
}
