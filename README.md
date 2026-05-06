# Java OOP Unit Testing

Java OOP practice project focused on basic object-oriented programming concepts, simple domain modelling, validation logic, and automated unit testing.

The project contains two main parts: introductory geometry/math classes and a money/banking domain model. It demonstrates how classes interact with each other, how objects can be validated, and how functionality can be checked with tests.

## Main Features

- Java object-oriented programming
- Package-based project structure
- Geometry and vector-related classes
- Money, currency, account, and bank classes
- Input validation and exception handling
- Unit tests for checking class behavior
- Practice with constructors, methods, and object interaction

## Technologies Used

- Java
- JUnit
- IntelliJ IDEA / Java IDE
- Git / GitHub

## Project Structure

```text
java-oop-unit-testing/
├── src/
│   ├── a_Introductory/
│   │   ├── Fibonacci.java
│   │   ├── Line.java
│   │   ├── Point.java
│   │   ├── Quadrilateral.java
│   │   └── Vector2D.java
│   └── b_Money/
│       ├── Account.java
│       ├── Bank.java
│       ├── Currency.java
│       ├── CurrencyConverter.java
│       ├── CurrencyDoesNotExistException.java
│       ├── Money.java
│       └── NotEnoughMoneyException.java
└── test/
    ├── a_Introductory/
    │   ├── FibonacciTest.java
    │   ├── LineTest.java
    │   ├── PointTest.java
    │   ├── QuadrilateralTest.java
    │   └── Vector2DTest.java
    └── b_Money/
        └── ...
```

## Introductory Package

The `a_Introductory` package contains basic classes used to practice core Java and OOP concepts.

| Class | Description |
|---|---|
| `Point` | Represents a point in a coordinate system |
| `Line` | Represents a line based on points |
| `Vector2D` | Represents a two-dimensional vector |
| `Quadrilateral` | Represents a four-sided geometric figure |
| `Fibonacci` | Contains logic related to the Fibonacci sequence |

## Money Package

The `b_Money` package contains a simple money and banking model.

| Class | Description |
|---|---|
| `Money` | Represents a monetary value |
| `Currency` | Represents a currency |
| `CurrencyConverter` | Handles currency conversion logic |
| `Account` | Represents a bank account |
| `Bank` | Manages accounts and money-related operations |
| `CurrencyDoesNotExistException` | Custom exception for unsupported currencies |
| `NotEnoughMoneyException` | Custom exception for insufficient balance |

## Testing

The project includes unit tests for checking the behavior of the implemented classes.

The tests cover examples such as:

- point and line operations
- vector calculations
- quadrilateral behavior
- Fibonacci logic
- money and account operations
- validation and exception cases

## How to Run

1. Clone the repository.
2. Open the project in IntelliJ IDEA or another Java IDE.
3. Make sure JUnit is configured.
4. Run the tests from the `test` directory.
5. Review the results in the test runner.

## Notes

This project was created as a Java OOP and unit testing practice task.

The main goal is to demonstrate class design, object interaction, basic domain modelling, and test-based verification of program behavior.
