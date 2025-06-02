@Clientes 
Feature: Clientes 

    Administrar clientes

    @QA @1 @2 @3 @4 @5
    Scenario: Background - El usuario Admin accede a Clientes
        Given el usuario se dirige a Gonnectia QA
        And inicia sesion con credenciales de Admin
        And el usuario se dirige a la seccion Clientes

    @QA @1
    Scenario Outline: 1 (Happy path) - El usuario busca un cliente de Cuentas Digitales
        When ingresa el nombre de un cliente: <apellido>
        Then el buscador deberia retornar al cliente <apellido> con sus datos completos

        Examples:
            | apellido |
            | Mills    |
            | Sporer   |
            | Rath     |

    @2
    Scenario: 2 (Positive) - El usuario limpia los valores de los filtros de busqueda
        When ingresa el nombre de un cliente y selecciona un tipo de persona
        And hace click en Limpiar filtros
        Then el campo de busqueda deberia quedar en blanco
        And el tipo de persona deberia volver a Todas

    @3
    Scenario: 3 (Positive) - El usuario edita los datos de un cliente
        When hace click en Editar en las opciones de un cliente
        And el modal de edicion se despliega
        Then el usuario podra modificar los datos, a excepcion de DNI, CUIT, email y Pais de Residencia
        And al hacer click en Guardar los cambios quedaran registrados en el backend

    @4
    Scenario Outline: 4 (Positive) -  El usuario modifica y se desplaza en la paginacion
        When selecciona la cantidad <cantidad> clientes por pagina
        Then podra explorar las paginas del listado utilizando los botones de navegacion

        Examples:
            | cantidad |
            | 15  |
            | 30  |
            | 60  |
    
    @QA @5
    Scenario Outline: 5 (Negative) - El usuario NO puede dar de alta un cliente con un DNI, CUIT o email ya registrado
        When hace click en Agregar cliente
        And selecciona pais y tipo de persona
        And completa los datos personales ingresando DNI <DNI> y CUIT <CUIT>
        And completa los datos de contacto ingresando un email <email>
        And envia el formulario de alta de cliente
        Then deberia dispararse un mensaje indicando que el DNI, CUIT o Email ya esta registrado
        And el cliente no deberia darse de alta

        Examples:
            | DNI       | CUIT         | email |
            | 21000004  | 20210000047  | lifah10417@acentni.com |
            | 18000001  | 20180000012  | tancherry@puabook.com  |
