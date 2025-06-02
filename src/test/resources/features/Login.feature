@login 
Feature: Login

    Login to Swag Labs

    Scenario: 1 (Happy path) - Sign in with valid credentials
        Given the user navigates to https://www.saucedemo.com/
        When he enters username standard_user and password secret_sauce
        And he succesfully signs in
        And he signs out
        Then the user should be redirected to the Login Page

    Scenario Outline: 2 (Negative) - Do not sign in with invalid credentials
        Given the user navigates to https://www.saucedemo.com/
        When he enters username <username> and password <password>
        Then an error message should be displayed
        And the user should not access to the website
    
        Examples:
        | username        | password      |
        | standard_user   | secretsauce   |
        | standarduser    | secret_sauce  |
        | problem_user    | secretsauce   |

    Scenario: 3 (Negative) - Do not sign in with locked out credentials
        Given the user navigates to https://www.saucedemo.com/
        When he enters username locked_out_user and password secret_sauce
        Then an informative message should be displayed
        And the user should not access to the website
