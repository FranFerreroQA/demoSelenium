package steps;

import pages.Products;

import java.util.Map;
import org.junit.Assert; // assertEquals Se marca como deprecada en Java, import org.junit.Assert
import io.cucumber.java.en.*; // importa todos los comandos Gherkin

public class ProductsSteps {

    Products products = new Products();
    String[] items = {
        "sauce-labs-backpack",
        "sauce-labs-bike-light",
        "sauce-labs-bolt-t-shirt",
        "sauce-labs-fleece-jacket",
        "sauce-labs-onesie"
    };

    // Background
    @Then("the products inventory should be displayed")
    public void successLogin(){
        Assert.assertTrue("Some element is missing",products.elementsDisplayed(new String[] {
            products.appLogo,
            products.title,
            products.inventory,
            products.shoppingCart
        }));
        Assert.assertEquals("The text doesn't match","Swag Labs",products.logoText());
        Assert.assertEquals("The text doesn't match","Products",products.titleText());
    }

    // Scenario 1
    @Given("the user adds some items to the shopping cart")
    public void addItems(){
        Assert.assertFalse("The shopping cart badge is displayed",products.isNowDisplayed(products.shoppingCartBadge));
        products.addItems(items);
        Assert.assertTrue("The shopping cart badge is not displayed",products.elementDisplayed(products.shoppingCartBadge));
        Assert.assertTrue("The shopping cart count is not displayed",products.validateShoppingCartCount(String.valueOf(items.length)));
        Assert.assertTrue("The remove buttons are not displayed", products.validateRemoveButtons(items));
    }

    @When("he proceeds to checkout")
    public void goesToCheckout(){
        products.goToShoppingCart();
        Assert.assertEquals("The text doesn't match", "Your Cart", products.titleText());
        Assert.assertEquals("The text doesn't match", "Description", products.cartDescriptionText());
        Assert.assertTrue("The remove buttons are not displayed", products.validateRemoveButtons(items));
        products.checkout();
    }

    @And("he completes the billing information")
    public void completesBillingInformation(){
        Assert.assertEquals("The text doesn't match", "Checkout: Your Information", products.titleText());
        products.billingInformation("Bruce","Wayne","1234");
    }

    @Then("he should see the purchase overview and be able to complete the payment")
    public void completePayment(){
        Assert.assertTrue("Some element is missing",products.elementsDisplayed(new String[] {
            products.paymentInformation,
            products.shippingInformation,
            products.priceTotal,
            products.itemTotal,
            products.taxTotal,
            products.total,
            products.cancelButton,
            products.finishButton
        }));
        Assert.assertEquals("The text doesn't match", "Checkout: Overview", products.titleText());
        products.finishPurchase();
    }

    @And("a thank-you message should be displayed, allowing the user to continue shopping")
    public void thankYouMessage(){
        Assert.assertTrue("The thank you message is not displayed",products.elementDisplayed(products.thankYouMessage));
        Assert.assertEquals("The text doesn't match","Your order has been dispatched, and will arrive just as fast as the pony can get there!",products.thankYouMessageText());
        products.continueShopping();
        Assert.assertTrue("The inventory is not displayed",products.elementDisplayed(products.inventory));
        Assert.assertEquals("The text doesn't match","Products",products.titleText());
    }

    // Scenario 2
    @Given("The inventory is sort by name from A to Z by default")
    public void inventoryDefault(){
        Assert.assertEquals("The value doesn't match","az",products.selectedSortValue());
    }

    @When("^the user selects to sort products by (.+)$")
    public void sortProducts(String valueText){
        String value = Map.of(
            "Name (Z to A)", "za",
            "Name (A to Z)", "az",
            "Price (low to high)", "lohi",
            "Price (high to low)","hilo"
        ).get(valueText);

        if (value == null) {
            throw new IllegalArgumentException("Invalid valueText: " + valueText);
        }

        products.sortBy(value);
        Assert.assertEquals("The value doesn't match",value,products.selectedSortValue());

    }

    @Then("the products inventory should be reordered")
    public void inventoryReordered(){
        products.refresh();
    }

    // Scenario 3
    @Then("he should be able to cancel the purchase order on the overview page")
    public void cancelPurchaseOrder(){
        Assert.assertTrue("The cancel button is not displayed",products.elementDisplayed(products.cancelButton));
        products.cancelCheckout();
    }

    @And("the user should be redirected to the products inventory page")
    public void redirectedToProducts(){
        Assert.assertTrue("The inventory is not displayed",products.elementDisplayed(products.inventory));
        Assert.assertEquals("The text doesn't match","Products",products.titleText());
    }

    @And("the shopping cart should retain the added items")
    public void shoppingCartEmpty(){
        Assert.assertTrue("The shopping cart badge is not displayed",products.elementDisplayed(products.shoppingCartBadge));
        Assert.assertTrue("The shopping cart count is not displayed",products.validateShoppingCartCount(String.valueOf(items.length)));
    }

}