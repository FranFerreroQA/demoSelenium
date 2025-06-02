package steps;

import pages.Login;
import pages.Products;

import org.junit.Assert; // assertEquals Se marca como deprecada en Java, import org.junit.Assert
import io.cucumber.java.en.*; // importa todos los comandos Gherkin

public class LoginSteps {

    Login login = new Login();
    Products products = new Products();

    // Scenario 1
    @Given("the user navigates to {word}")
    public void navigatoToSwagLabs(String url){
        login.navigateToSwagLabs(url); 
        Assert.assertTrue("Some element is missing", login.elementsDisplayed(new String[] {
            login.loginLogo,
            login.usernameInput,
            login.passwordInput,
            login.loginButton
        }));
    }

    @When("he enters username {word} and password {word}")
    public void enterCredentials(String username, String password){
        login.signIn(username, password);
    }

    @Then("he succesfully signs in")
    public void successLogin(){
        Assert.assertTrue("Some element is missing",products.elementsDisplayed(new String[] {
            products.appLogo,
            products.title,
            products.inventory
        }));
        Assert.assertEquals("The text doesn't match","Swag Labs",products.logoText());
        Assert.assertEquals("The text doesn't match","Products",products.titleText());
    }

    @And("he signs out")
    public void signsOut(){
        login.signOut();
    }

    @And("the user should be redirected to the Login Page")
    public void cerrarSesion(){
        Assert.assertTrue("Some element is missing",login.elementsDisplayed(new String[] {
            login.loginLogo,
            login.usernameInput,
            login.passwordInput,
            login.loginButton
        }));
        Assert.assertEquals("The text doesn't match","Swag Labs",login.logoText());
    }

    // Scenario 2
    @Then("an error message should be displayed")
    public void errorMessage(){
        Assert.assertEquals("The message is not displayed", "Epic sadface: Username and password do not match any user in this service",login.errorMessage());
    }

    @And("the user should not access to the website")
    public void notAccess(){
        Assert.assertTrue("The Login logo is not displayed",login.elementDisplayed(login.loginLogo));
        Assert.assertFalse("The inventory is displayed",products.isNowDisplayed(products.inventory));
    }

    // Scenario 3
    @Then("an informative message should be displayed")
    public void lockedMessage(){
        Assert.assertEquals("The message is not displayed", "Epic sadface: Sorry, this user has been locked out.",login.errorMessage());
    }

}