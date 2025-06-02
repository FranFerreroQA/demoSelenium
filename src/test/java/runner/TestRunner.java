package runner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import pages.BasePage;

// Declaramos que el Runner trabajara con Cucumber
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", // Directorio de nuestros features
        glue = "steps", // Paquete donde tenemos nuestras clases definiendo los steps
        plugin = {"pretty","html:CucumberReports/Cucumber-report.html"}, // Ruta de destino de los reportes Cucumber
        tags = "@Login") // Cambiar el tag para correr otras Features y Escenarios

public class TestRunner {
        // AfterClass seran funciones a ejecutar luego del Run. Cerrar Browser y Driver
        @AfterClass
        public static void cleanDriver() {
            BasePage.closeBrowser();
        } 
}

        // Comandos terminal para ejecutar tags: 

        // gradle test -Dcucumber.filter.tags="@login" (todos los que tengan el tag)
        // gradle test -Dcucumber.filter.tags="@products" (todos los que tengan el tag)
        // gradle test -Dcucumber.filter.tags="not @login" (todos los que no tengan el tag)
        // gradle test -Dcucumber.filter.tags="@login or @products" (cualquiera de los dos tags)
        // gradle test -Dcucumber.filter.tags="@login and @products" (solo los que tengan los dos tags)
        // gradle test -Dcucumber.filter.tags="@products and (@2 or @3)" (los que tengan el primer tag y uno de los otros)
        // gradle test -Dcucumber.filter.tags="@login and not @products" (solo los que tengan el primer tag)

