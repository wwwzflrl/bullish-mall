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

#### Price Calculation

1. Apply Product Promotion to get total amount
2. Apply Total Promotion to total amount to get final price

```
Example 1
Product A, price 8,  buy 3 get 1 free, 
Product B, price 10, 20% off
Total Promotion: Over 50, minus 10

Client buy 5 A and 20 B

Total Amount = (5 - 1) * 8 + 20 * 10 * 0.8 = 192
Final Amount = 192 - 10 = 182
```