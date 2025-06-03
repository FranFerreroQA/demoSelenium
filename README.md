
# ✅ Selenium Test Automation Framework

This project is a web automation framework built using:

- **Java**
- **Selenium WebDriver**
- **Cucumber** (Gherkin syntax for BDD)
- **Gradle** (build tool)
- **Page Object Model (POM)** design pattern

---

## 📁 Project Structure

The framework is designed using the **Page Object Model** to promote reusability and maintainability of code.

src/
test/
java/ 
steps/ # Step definitions for Cucumber
pages/ # Page classes representing each page of the application
runners/ # Test runner (JUnit)
features/ # Gherkin feature files
build.gradle # Gradle build configuration

---

## ⚙️ Getting Started

### 🔧 Prerequisites

Make sure the following tools are installed on your system:

- [Java JDK 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Gradle](https://gradle.org/install/)
- An IDE like **IntelliJ IDEA** or **Visual Studio Code**

---

### 🧪 Running Tests with Cucumber Tags

You can use the `-Dcucumber.filter.tags` option to run specific scenarios based on their tags.
Avoid running Gradle tests from PowerShell or Git Bash — use **Command Prompt** for full compatibility.

- gradle test -Dcucumber.filter.tags="@login" 
  → Runs all scenarios that have the `@login` tag.

- gradle test -Dcucumber.filter.tags="@products"
  → Runs all scenarios that have the `@products` tag.

- gradle test -Dcucumber.filter.tags="not @login"
  → Runs all scenarios that **do not** have the `@login` tag.

- gradle test -Dcucumber.filter.tags="@login or @products"
  → Runs scenarios that have **either** the `@login` or `@products` tag.

- gradle test -Dcucumber.filter.tags="@login and @products"
  → Runs **only** scenarios that have **both** the `@login` and `@products` tags.

- gradle test -Dcucumber.filter.tags="@products and (@2 or @3)"
  → Runs scenarios that have the `@products` tag and **either** the `@2` or `@3` tag.

- gradle test -Dcucumber.filter.tags="@login and not @products"
  → Runs scenarios that have the `@login` tag but **do not** have the `@products` tag.

---

## 📦 Build and Run with Gradle

Use the following commands:

- gradle clean
  Cleans the previous build artifacts.

- gradle build
  Compiles the code and prepares the test artifacts.

- gradle test
  Executes all test cases defined in the runner classes.

---

## 📁 Reports

After running the tests, you can find the generated reports in /CucumberReports
You can configure the output route for reports in the TestRunner file.

---

## 📝 Notes for Users

- Make sure the `JAVA_HOME` environment variable is set correctly.
- Avoid running Gradle tests from PowerShell or Git Bash — use **Command Prompt** on Windows for full compatibility.
- Customize your tags in `.feature` files to organize and filter scenarios effectively.

---

## 📌 Tools & Recommendations

- **Java 11+**
- **Gradle**
- **IntelliJ IDEA** or **VS Code**
- **Cucumber for Java plugin** (IDE support)
- **Gradle plugin**

---

Happy Testing! 🚀
