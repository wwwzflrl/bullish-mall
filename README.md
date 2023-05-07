# bullish mall

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