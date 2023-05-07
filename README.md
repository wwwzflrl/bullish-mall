# bullish mall

### Pre-requests

* jdk version >= 17
* Maven

Run following command to install java package

```
.\mvnw clean install

OR

mvn clean install

```

#### Test Case

If you want to use spring built in mvn, please run

```
.\mvnw test
```

You can also use your global mvn run test case

```agsl
mvn test

```

#### Run Application

Same as before, you can run

```agsl
./mvnw spring-boot:run
mvn spring-boot:run
```

The application will run on 8080 port.
And try to visit http://localhost:8080/user/welcome

### Promotion

#### Promotion Type

* Product Promotion (Each Product)
    * X % off
    * X dollar
    * Buy X Product, get Y Product free

* Total Promotion (Whole order)
    * Over X dollar, minus Y dollar
    * Over X dollar, Y % off

#### Promotion Limitation

* Every product, at most one Product Promotion
* Whole order have at most one promotion

```
Example 1
Product A, price 8,  buy 3 get 1 free, 
Product B, price 10, 20% off
Total Promotion: Over 50, minus 10

Client buy 5 A and 20 B

Actual Amount = (5 - 1) * 8 + 20 * 10 * 0.8 = 192
Original Amount = 5 * 8 + 20 * 10 =240
Reduce Amount = 48
```