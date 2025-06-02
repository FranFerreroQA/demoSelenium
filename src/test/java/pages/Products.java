package pages;

public class Products extends BasePage {
    
    // Locators

    // Inventory Page
    public String appLogo = "//div[@class='app_logo']";
    public String title = "//span[@class='title']";
    public String burgerMenu = "//button[@id='react-burger-menu-btn']";
    public String inventory = "//div[@id='inventory_container']";
    public String addButton = "//button[@id='add-to-cart-%s']";
    public String removeButton = "//button[@id='remove-%s']";
    public String shoppingCart = "//a[@class='shopping_cart_link']";
    public String shoppingCartBadge = "//span[@class='shopping_cart_badge']";
    public String shoppingCartCount = "//span[@class='shopping_cart_badge' and text()='%s']";

    // Checkout Page
    public String cartDescription = "//div[@class='cart_desc_label']";
    public String checkoutButton = "//button[@id='checkout']";
    public String firstNameInput = "//input[@id='first-name']";
    public String lastNameInput = "//input[@id='last-name']";
    public String postalCodeInput = "//input[@id='postal-code']";
    public String continueButton = "//input[@id='continue']";
    public String paymentInformation = "//div[text()='Payment Information:']";
    public String shippingInformation = "//div[text()='Shipping Information:']";
    public String priceTotal = "//div[text()='Price Total']";
    public String itemTotal = "//div[@class='summary_subtotal_label']";
    public String taxTotal = "//div[@class='summary_tax_label']";
    public String total = "//div[@class='summary_total_label']";
    public String cancelButton = "//button[@id='cancel']";
    public String finishButton = "//button[@id='finish']";
    public String thankYouMessage = "//div[@class='complete-text']";
    public String homeButton = "//button[@id='back-to-products']";
    public String sortContainer = "//select[@class='product_sort_container']";

    // Constructor
    public Products(){
        super(driver);
    }

    // Methods
    public String logoText(){
        return textFromElement(appLogo);
    }

    public String titleText(){
        return textFromElement(title);
    }

    public void addItems(String[] items){
        for (String item : items) {
            String varItem = String.format(addButton, item);
            clickElement(varItem);
        }
    }

    public void removeItems(String[] items){
        for (String item : items) {
            String varItem = String.format(removeButton, item);
            clickElement(varItem);
        }
    }

    public boolean validateRemoveButtons(String[] items){
        for (String item : items) {
            String varItem = String.format(removeButton, item);
            if (!elementDisplayed(varItem)) {
                return false;
            }
        }
        return true;
    }

    public Boolean validateShoppingCartCount(String count){
        String varCount = String.format(shoppingCartCount, count);
        return elementDisplayed(varCount);
    }

    public void goToShoppingCart(){
        clickElement(shoppingCart);
    }

    public String cartDescriptionText(){
        return textFromElement(cartDescription);
    }

    public void checkout(){
        clickElement(checkoutButton);
    }

    public void billingInformation(String firstName, String lastName, String zip){
        write(firstNameInput,firstName);
        write(lastNameInput,lastName);
        write(postalCodeInput,zip);
        clickElement(continueButton);
    }

    public void finishPurchase(){
        clickElement(finishButton);
    }

    public String thankYouMessageText(){
        return textFromElement(thankYouMessage);
    }

    public void continueShopping(){
        clickElement(homeButton);
    }

    public void sortBy(String value){
        selectFromDropdownByValue(sortContainer,value);
    }

    public String selectedSortValue(){
        return getElementValue(sortContainer);
    }

    public void cancelCheckout(){
        clickElement(cancelButton);
    }

}