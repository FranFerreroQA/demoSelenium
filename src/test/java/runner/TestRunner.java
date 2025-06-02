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


