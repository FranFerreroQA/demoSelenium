package pages;

public class Login extends BasePage {

    // Locators    
    public String loginLogo = "//div[@class='login_logo']";
    public String usernameInput = "//input[@id='user-name']"; 
    public String passwordInput = "//input[@id='password']";
    public String loginButton = "//input[@id='login-button']";
    public String burgerMenu = "//button[@id='react-burger-menu-btn']";
    public String logoutButton = "//a[@id='logout_sidebar_link']";
    
    // Constructor
    public Login(){
        super(driver);
    }

    // Methods
    public void navigateToSwagLabs(String url) {
        navigateTo(url);
    }

    public void signIn(String username, String password){
        write(usernameInput, username);
        write(passwordInput, password);
        clickElement(loginButton);
    }

    public void signOut(){
        clickElement(burgerMenu);
        esperar(1000);
        clickElement(logoutButton);
    }

    public String logoText(){
        return textFromElement(loginLogo);
    }

    public String errorMessage(){
        return textFromMessage("Epic sadface");
    }

}