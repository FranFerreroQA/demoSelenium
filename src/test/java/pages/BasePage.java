package pages;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.TimeoutException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

    // Declaracion de una variable estatica driver de tipo WebDriver
    // Esta variable va a ser compartida por todas las instancias de BasePage y sus subclases
    protected static WebDriver driver;

    // Declaramos la variable de instancia wait de tipo WebDriverWait
    // Se inicializa inmediatamente con una instancia de WebDriverWait utilizando el driver estatico
    // WebDriverWait se usa para definir esperas implicitas del driver para ejecutar una accion
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    
    // Configura el WebDriver para Chrome usando WebDriverManager 
    // WebDriverManager va a estar descargando y configurando automaticamente el driver de cada version de Chrome
    static {
    WebDriverManager.chromedriver().setup();
    ChromeOptions chromeOptions = new ChromeOptions();

    // Agregar preferencias del navegador
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    chromeOptions.setExperimentalOption("prefs", prefs);

    // Flags para eliminar advertencias y popups
    chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
    chromeOptions.addArguments("--disable-features=PasswordCheck");
    chromeOptions.addArguments("--disable-features=AutofillServerCommunication");
    chromeOptions.addArguments("--disable-features=SafetyTipUI");
    chromeOptions.addArguments("--no-default-browser-check");
    chromeOptions.addArguments("--disable-popup-blocking");
    chromeOptions.addArguments("--disable-notifications");
    chromeOptions.addArguments("--incognito");

    // Crear el WebDriver DESPUÉS de configurar todo
    driver = new ChromeDriver(chromeOptions);
    driver.manage().window().maximize();
    }
    
    // Constructor de la clase BasePage que se heredara a las otras clases
    public BasePage(WebDriver driver){
        BasePage.driver = driver;
    } 

    // Comandos browser
    public static void navigateTo(String url) {
        driver.get(url);
    } 

    public void refresh(){
        driver.navigate().refresh();
    }

    public void pageBack(){
        driver.navigate().back();
    }

    public String getTitle(){
        return driver.getTitle();
    }

    public String getUrl(){
        return driver.getCurrentUrl();
    }

    public void verElemento(String locator){
        elementIsEnabled(locator);
    }
    
    // Cerrar ventana 
    public static void driverClose(){
        driver.close();
    }    

    // Cierra el driver y el broswer
    public static void closeBrowser() {
        driver.quit(); 
    } 

    // Esperar siempre para encontrar un elemento
    public WebElement find(String locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    } 

    public List<WebElement> findElements(String locator){
        return driver.findElements(By.xpath(locator));
    }

    // Esperar para que un elemento sea clickeable
    public WebElement elementIsClickable(String locator){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
    }

    public void forceClick(String locator) {
        WebElement  element = driver.findElement(By.xpath(locator));  
        JavascriptExecutor ex = (JavascriptExecutor)driver;
        ex.executeScript("arguments[0].click()", element);
    }

    public void waitForClick(String locator) {
        WebElement webElement = driver.findElement(By.xpath(locator));
            if(!webElement.getAttribute("class").contains("disabled")){
            webElement.click();
        }
    }

    // Esperar por un texto en un elemento
    public boolean waitForText(String locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(locator),text));
    }

    // Espera explicita
    public void esperar(int tiempo){
        try {
        Thread.sleep(tiempo);
        } catch (Exception e) {
        System.out.println(e);
        }
    }

    // Esperar siempre antes de hacer click en un elemento
    public void clickElement(String locator) {
        find(locator).click();
    }

    public void hoverElement(String locator){
        WebElement x = find(locator);
        Actions action = new Actions(driver);
        action.moveToElement(x).perform();
    }

    public void hoverAndClick(String locator){
        WebElement x = find(locator);
        Actions action = new Actions(driver);
        action.moveToElement(x).click().build().perform();
    }

    // Locators por texto
    public String locatorByEqualText(String text){
        String elementToFind = String.format("//*[text()=\"%s\"]", text);
        return elementToFind;
    }

    public WebElement byEqualText(String text) {
        String elementToFind = String.format("//*[text()=\"%s\"]", text);
        return driver.findElement(By.xpath(elementToFind));
    }

    public String locatorByText(String text){
        String elementToFind = String.format("//*[contains(text(),\"%s\")]", text);
        return elementToFind;
    }

    public WebElement byContainsText(String text) {
        String elementToFind = String.format("//*[contains(text(),\"%s\")]", text);
        return driver.findElement(By.xpath(elementToFind));
    }

    // Antes de escribir limpia el campo de texto
    public void write(String locator, String keysToSend) {
        find(locator).clear();
        find(locator).sendKeys(keysToSend);
    }

    // Borrar campos de texto
    public void backSpace(String locator, int spaces) {
        for (int i = 0; i < spaces; i++) { 
            find(locator).sendKeys(Keys.BACK_SPACE); 
        }
    }

    // Desplazarse a la izquierda en campos de texto
    public void leftArrow(String locator, int spaces) {
        for (int i = 0; i < spaces; i++) { 
            find(locator).sendKeys(Keys.ARROW_LEFT); 
        }
    }

    // Retornar textos
    public String textFromElement(String locator){
        return find(locator).getText();
    }

    public String textFromMessage(String message){
        return textFromElement(locatorByText(message));
    }

    // Validar que un elemento es visualizado, con espera implicita
    public boolean elementDisplayed(String locator){
        try {
            return find(locator).isDisplayed(); // true o false
        } catch (org.openqa.selenium.StaleElementReferenceException j){ // si el HTML cambia
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            return find(locator).isDisplayed(); // Vuelve a intentar encontrar el elemento
        } catch (TimeoutException x){ // Si pasa el tiempo definido de wait
            return false;
        } catch (Exception e){ // cualquier otra excepcion
            e.printStackTrace();
            return false;
        }
    }

    public boolean elementsDisplayed(String[] locators){
        for (String locator : locators) {
            if (!elementDisplayed(locator)) {
                return false; // Si al menos uno no se encuentra, retorna false
            }
        }
        return true; // Si todos los elementos estan visibles, retorna true
    }

    // Sin espera, lista vacia
    public boolean isNowDisplayed(String locator){
        return !driver.findElements(By.xpath(locator)).isEmpty();
    }

    // Metodos con css selector
    public WebElement findSelector(String selector) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
    }

    public void clickElementSelector(String selector) {
        findSelector(selector).click();
    }

    public void writeSelector(String selector, String keysToSend) {
        findSelector(selector).clear();
        findSelector(selector).sendKeys(keysToSend);
    } 

    // El objeto Select se importa de Selenium, instanciamos el elemento dropdown
    public void selectFromDropdownByValue (String locator, String value) {
        Select dropdown = new Select(find(locator));
        dropdown.selectByValue(value);
    }

    public void selectFromDropdownByIndex (String locator, Integer index) {
        Select dropdown = new Select(find(locator));
        dropdown.selectByIndex(index);
    }
    
    public int dropdownSize(String locator) {
        Select dropdown = new Select(find(locator));
        List<WebElement> dropdownOptions = dropdown.getOptions();
        return dropdownOptions.size();
    }

    public List<String> getDropdownValues(String locator) {
        Select dropdown = new Select(find(locator));
        List<WebElement> dropdownOptions = dropdown.getOptions();
        List<String> values = new ArrayList<>();
        for (WebElement option : dropdownOptions) {
            values.add(option.getText());
        }  
        return values; 
    }

    public boolean visualizarObjeto(WebElement elementName, int timeout) {
        try {
            System.out.println("Valida si es visible el elemento buscado.");
            wait.until(ExpectedConditions.visibilityOf(elementName));
            System.out.println("Es visible el elemento buscado: " + elementName.getText());
            System.out.println("==============================================");
            System.out.println();
            return true;
        } catch (Exception var3) {
            System.out.println("No es visible el elemento a buscado.");
            return false;
        }
    }

    // Abrir un link en otra pestaña
    public void handlesTabs(String locator){
        String originalWindow = driver.getWindowHandle();
        assert driver.getWindowHandles().size() == 1; // Cuantas ventanas hay = 1
        clickElement(locator);
        for (String windowHandle : driver.getWindowHandles()){
            if (!originalWindow.contentEquals(windowHandle)){
                driver.switchTo().window(windowHandle); // Cambiar de ventana
                break;
            }
        }
    }

    // Cambiar de ventana - Si cerramos una ventana hay que cambiar a otra
    public static void cambiarVentana(int pantalla){ // numero de ventanas ordenadas en un array
        try {
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(pantalla));
        } catch (Exception e){
            System.out.println(e);
        }
    }

    // Ir a un hipervinculo en la misma ventana
    public void getLinkText(String linkText){
        driver.findElement(By.linkText(linkText)).click();
    }

    // Validar que un elemento este habilitado
    public boolean elementIsEnabled(String locator){
        return find(locator).isEnabled(); // true o false
    }

    // Validar que un elemento este seleccionado
    public boolean elementIsSelected(String locator){
        return find(locator).isSelected(); // true o false
    }

    // Obtener una lista de elementos localizados en una clase, indicando xpath
    public List<WebElement> bringMeAllElementsByXpath(String locator){ // podemos pasar el xpath de un dropbox
        return driver.findElements(By.xpath(locator));
    }

    // Obtener una lista String de una lista de web elements 
    public List<String> convertWebElementListToStringList(List<WebElement> webElements){ // le pasamos la lista obtenida previamente
        return webElements.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList()); // podemos aplicar una funcion al elemento identificado por texto
    }

    // Seleccionar un criterio en una lista
    public void selectCriteriaFromList(String locator, String criteria){
        List<WebElement> list = bringMeAllElementsByXpath(locator);
        for (WebElement element : list){
            if (element.getText().equals(criteria)){ // hacer click a un elemento por criterio texto
                element.click();
                break;
            }
        }
    }

    // Obtener el valor de un elemento
    public String getElementValue(String locator){
        WebElement e = driver.findElement(By.xpath(locator));
        String value = e.getAttribute("value");
        return value; // retorna valores en string
    }

    // Obtener cualquier atributo de un elemento
    public String getElementAttribute(String locator, String attribute){
        WebElement e = driver.findElement(By.xpath(locator));
        String value = e.getAttribute(attribute);
        return value; // retorna valores en string
    }

    public void scrollToElement(String locator){
        WebElement scrollToElement = driver.findElement(By.xpath(locator));
        JavascriptExecutor jse = (JavascriptExecutor) driver; 
        jse.executeScript("arguments[0].scrollIntoView(true);",scrollToElement);    
    }

    public void scrollToElement2(String locator){
        Actions action = new Actions(driver);
        WebElement button = driver.findElement(By.xpath(locator));
        action.moveToElement(button);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement scrollElement = driver.findElement(By.xpath(locator));                 
        jse.executeScript("arguments[0].click();",scrollElement);
    }

    public void scrollDown(int Pixeles) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, " + Pixeles + ")");
    }

    public void scrollUp(int Pixeles) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -" + Pixeles + ")");
    }

    public void pressKeyDownIterar(int Iteracion) throws IOException {
        Actions action = new Actions(driver);
        for (int i = 0; i < Iteracion; i++) {
            action.keyDown(Keys.ARROW_DOWN).perform();
            action.keyUp(Keys.ARROW_DOWN).perform();
        }
    }

    public void pressKeyUpIterar(int Iteracion) throws IOException {
        Actions action = new Actions(driver);
        for (int i = 0; i < Iteracion; i++) {
            action.keyDown(Keys.ARROW_UP).perform();
            action.keyUp(Keys.ARROW_UP).perform();
        }
    }

    public void dragElement(String locator,int x,int y){
        WebElement e = driver.findElement(By.xpath(locator));
        Actions move = new Actions(driver);
        move.moveToElement(e).clickAndHold().moveByOffset(x,y).release().perform();
    }

    public void loginWithCookies(String url, String value) {
        // Abre la página de inicio de sesión
        driver.get(url);
        // Crea y agrega la cookie de sesión
        Cookie sessionCookie = new Cookie("Nombre de la cookie: ", value);
        driver.manage().addCookie(sessionCookie);
        // Actualiza la página para aplicar la cookie
        driver.navigate().refresh();
    }

    // Paginacion
    public void elegirFilas(String opcionesFilas, String elegirFilas, String cantidad){
        clickElement(opcionesFilas);
        String variableFilas = String.format(elegirFilas, cantidad);
        clickElement(variableFilas);
    }

    public void avanzarPagina(String locator){
        try {
            Thread.sleep(1000);
            clickElement(locator);
        } catch (Exception e) {
            System.out.println(e);
        }  
    }

    public void avanzarPaginas(String locator, int cantidad){
        int pagina = 0;
        do {
            avanzarPagina(locator);
            pagina ++;
        } while (pagina < cantidad);
    }

    public void retrocederPagina(String locator){
        try {
            Thread.sleep(1000);
            clickElement(locator);
        } catch (Exception e) {
            System.out.println(e);
        } 
    }

    public void retrocederPaginas(String locator, int cantidad){
        int pagina = 0;
        do {
            retrocederPagina(locator);
            pagina ++;
        } while (pagina < cantidad);
    }

    public void irAUltimaPagina(String locator){
        do {
            avanzarPagina(locator);
        } while (elementIsEnabled(locator));
        esperar(2000);
    }

    public void irAPrimeraPagina(String locator){
        do {
            retrocederPagina(locator);
        } while (elementIsEnabled(locator));
        esperar(2000);
    }

    public boolean extensionArchivoDescargado(String downloadPath, String extension) {
        File descargas = new File(downloadPath);
        File[] listaDescargas = descargas.listFiles();
        for (int i = 0; i < listaDescargas.length; i++) {
            if (listaDescargas[i].getName().contains(extension)) {
                return true;
            }
        }
        return false;
    }

    public boolean nombreArchivoDescargado(String downloadPath, String fileName) {
        File descargas = new File(downloadPath);
        File[] listaDescargas = descargas.listFiles();
        for (int i = 0; i < listaDescargas.length; i++) {
            if (listaDescargas[i].getName().contains(fileName)) {
                // File has been found, it can now be deleted:
                listaDescargas[i].delete();
                return true;
            }
        }
        return false;
    }

    // public String getContentType(String locator) throws MalformedURLException, IOException{
    //     WebElement downloadLink = find(locator);
    //     String link = downloadLink.getAttribute("href");
    //     HttpURLConnection httpConnection = (HttpURLConnection)(new URL(link).openConnection());
    //     httpConnection.setRequestMethod("HEAD");
    //     httpConnection.connect();
    //     String contentType = httpConnection.getContentType();
    //     return contentType;
    // }

    public String fechaActual(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
        String fechaComoCadena = sdf.format(new Date());
        return fechaComoCadena;
    }

}