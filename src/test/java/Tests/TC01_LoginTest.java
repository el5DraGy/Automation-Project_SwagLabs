package Tests;

import Listeners.IInvokedListenersClass;
import Listeners.ITestListenersClass;
import Pages.P01_LoginPage;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyData;
import static Utilities.Utility.implicitWait;

@Listeners({IInvokedListenersClass.class, ITestListenersClass.class})
public class TC01_LoginTest {
    private final String USERNAME = getJsonData("validLogin", "usernameValue");
    private final String PASSWORD = getJsonData("validLogin", "passWordValue");

    @BeforeMethod
    public void setup() throws IOException {
        setUpDriver(getPropertyData("environment", "Browser"));
        LogsUtils.info("starting Edge browser");
        getDriver().get(getPropertyData("environment", "LoginPage_URL"));
        LogsUtils.info("get the URL and loading the page");
        implicitWait(getDriver(), 10);
    }

    @Test
    public void validLogInTC() throws IOException {
        new P01_LoginPage(getDriver())
                .sendUserName(USERNAME)
                .sendPassword(PASSWORD)
                .logInButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLogin(getPropertyData("environment", "HomePage_URL")));
    }

    //TODO: Add a test for invalid Username
    @Test
    public void invalidUsernameTC() throws IOException {
        info("Starting invalidUsernameTC test");
        new P01_LoginPage(getDriver())
                .sendUserName("invalidUser")
                .sendPassword(PASSWORD)
                .logInButton();
        info("Submitted login form with invalid username");
        softAssert.assertTrue(new P01_LoginPage(getDriver()).isErrorDisplayed(), "Error message should be displayed for invalid username.");
        info("Checked if error message is displayed");
        softAssert.assertEquals(new P01_LoginPage(getDriver()).getErrorMessage(), INVALID_CREDENTIALS_ERROR, "Error message text should match expected.");
        info("Verified error message text for invalid username");
        softAssert.assertNotEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "HomePage_URL"), "User should not be redirected to the home page.");
        softAssert.assertAll();
    }

    //TODO: Add a test for invalid Password
    @Test
    public void invalidPasswordTC() throws IOException {
        info("Starting invalidPasswordTC test");
        new P01_LoginPage(getDriver())
                .sendUserName(USERNAME)
                .sendPassword("wrongPass")
                .logInButton();
        info("Submitted login form with invalid password");
        softAssert.assertTrue(new P01_LoginPage(getDriver()).isErrorDisplayed(), "Error message should be displayed for invalid password.");
        info("Checked if error message is displayed");
        softAssert.assertEquals(new P01_LoginPage(getDriver()).getErrorMessage(), INVALID_CREDENTIALS_ERROR, "Error message text should match expected.");
        info("Verified error message text for invalid password");
        softAssert.assertNotEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "HomePage_URL"), "User should not be redirected to the home page.");
        softAssert.assertAll();
    }

    // TODO : Test for empty Username
    @Test
    public void emptyUsernameTC() throws IOException {
        info("Starting emptyUsernameTC test");
        new P01_LoginPage(getDriver())
                .sendUserName("")
                .sendPassword(PASSWORD)
                .logInButton();
        info("Submitted login form with empty username");
        info("Checking if error message is displayed for empty username");
        softAssert.assertTrue(new P01_LoginPage(getDriver()).isErrorDisplayed(), "Error message should be displayed for empty username.");
        info("Verifying error message text for empty username");
        softAssert.assertEquals(new P01_LoginPage(getDriver()).getErrorMessage(), USERNAME_REQUIRED_ERROR, "Error message text should match expected for empty username.");
        info("Checking that user is not redirected to the home page for empty username");
        softAssert.assertNotEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "HomePage_URL"), "User should not be redirected to the home page.");
        info("Verified error message and URL for empty username");
        softAssert.assertAll();
    }

    // TODO : Test for empty Password
    @Test
    public void emptyPasswordTC() throws IOException {
        info("Starting emptyPasswordTC test");
        new P01_LoginPage(getDriver())
                .sendUserName(USERNAME)
                .sendPassword("")
                .logInButton();
        info("Submitted login form with empty password");
        softAssert.assertTrue(new P01_LoginPage(getDriver()).isErrorDisplayed(), "Error message should be displayed for empty password.");
        softAssert.assertEquals(new P01_LoginPage(getDriver()).getErrorMessage(), PASSWORD_REQUIRED_ERROR, "Error message text should match expected for empty password.");
        softAssert.assertNotEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "HomePage_URL"), "User should not be redirected to the home page.");
        info("Verified error message and URL for empty password");
        softAssert.assertAll();
    }

    @Test
    public void bothFieldsEmptyTC() throws IOException {
        info("Starting bothFieldsEmptyTC test");
        new P01_LoginPage(getDriver())
                .sendUserName("")
                .sendPassword("")
                .logInButton();
        info("Submitted login form with both fields empty");
        softAssert.assertTrue(new P01_LoginPage(getDriver()).isErrorDisplayed(), "Error message should be displayed for empty fields.");
        softAssert.assertEquals(new P01_LoginPage(getDriver()).getErrorMessage(), USERNAME_REQUIRED_ERROR, "Error message text should match expected for both fields empty.");
        softAssert.assertNotEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "HomePage_URL"), "User should not be redirected to the home page.");
        info("Verified error message and URL for both fields empty");
        softAssert.assertAll();
    }

    //TODO: Add a test for case sensitivity in Username and Password
    @Test
    public void caseSensitivityTC() throws IOException {
        info("Starting caseSensitivityTC test");
        new P01_LoginPage(getDriver())
                .sendUserName(USERNAME.toUpperCase())
                .sendPassword(PASSWORD.toLowerCase())
                .logInButton();
        info("Submitted login form with case sensitivity mismatch");
        softAssert.assertTrue(new P01_LoginPage(getDriver()).isErrorDisplayed(), "Error message should be displayed for case sensitivity mismatch.");
        info("Checked if error message is displayed for case sensitivity");
        softAssert.assertEquals(new P01_LoginPage(getDriver()).getErrorMessage(), INVALID_CREDENTIALS_ERROR, "Error message text should match expected for case sensitivity mismatch.");
        info("Verified error message text for case sensitivity");
        softAssert.assertNotEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "HomePage_URL"), "User should not be redirected to the home page.");
        softAssert.assertAll();
    }

    //TODO: some edge cases for the login
    @Test
    public void whitespaceInFieldsTC() throws IOException {
        info("Starting whitespaceInFieldsTC test");
        new P01_LoginPage(getDriver())
                .sendUserName(" " + USERNAME + " ")
                .sendPassword(" " + PASSWORD + " ")
                .logInButton();
        info("Submitted login form with whitespace in fields");
        softAssert.assertTrue(new P01_LoginPage(getDriver()).isErrorDisplayed(), "Error message should be displayed for whitespace in fields.");
        info("Checked if error message is displayed for whitespace in fields");
        softAssert.assertEquals(new P01_LoginPage(getDriver()).getErrorMessage(), INVALID_CREDENTIALS_ERROR, "Error message text should match expected for whitespace in fields.");
        info("Verified error message text for whitespace in fields");
        softAssert.assertNotEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "HomePage_URL"), "User should not be redirected to the home page.");
        info("Verified error message and URL for whitespace in fields");
        softAssert.assertAll();
    }

    //TODO: Add a test for spaces only in Username and Password
    @Test
    public void spacesOnlyTC() throws IOException {
        info("Starting spacesOnlyTC test");
        new P01_LoginPage(getDriver())
                .sendUserName("   ")
                .sendPassword("   ")
                .logInButton();
        info("Submitted login form with spaces only in both fields");
        softAssert.assertTrue(new P01_LoginPage(getDriver()).isErrorDisplayed(), "Error message should be displayed for spaces only.");
        softAssert.assertEquals(new P01_LoginPage(getDriver()).getErrorMessage(), INVALID_CREDENTIALS_ERROR, "Error message text should match expected for spaces only.");
        softAssert.assertNotEquals(getDriver().getCurrentUrl(), getPropertyData("environment", "HomePage_URL"), "User should not be redirected to the home page.");
        info("Verified error message and URL for spaces only in both fields");
        softAssert.assertAll();
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
