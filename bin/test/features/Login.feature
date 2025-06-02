@Login 
Feature: Login

    Ingresar al Backoffice como Admin

    @QA @Demo
    Scenario Outline: 1 (Happy path) - Loguearse con credenciales de Admin
        Given el usuario se dirige al backoffice de Gonnectia <url>
        When ingresa el usuario <username> y la contrasena <password>
        And el login se realiza exitosamente
        And cierra sesion
        Then el usuario es redirigido al Login Page

        Examples:
            | url                               | username                 | password         |
            | https://gonnectia.qa.goiar.com/   | gonnectia-qa@goiar.com   | Gonnectia14624#  |
            | https://gonnectia.demo.goiar.com/ | gonnectia-demo@goiar.com | Gonnectia14624#  |

    @QA @Demo
    Scenario Outline: 2 (Negative) - No loguearse con credenciales invalidas
        Given el usuario se dirige al backoffice de Gonnectia <url>
        When ingresa el usuario <username> y la contrasena <password>
        Then deberia dispararse un mensaje de error
        And el usuario no deberia ingresar al Backoffice

        Examples:
            | url                               | username                 | password        |
            | https://gonnectia.qa.goiar.com/   | gonnectia-qa@goiar.com   | Gonnectia14624  |
            | https://gonnectia.demo.goiar.com/ | gonnectia-demo@goiar.com | Gonnectia14624  |
