# In this example we read the information about products from a JSON file, the file contains products and delivery types. The problem is to create a function that will split products given into diffrent deliveries, the goal is to put ads many products in one delivery as possible

## In the BasketSplitter class we have a constuctor that receives the path to the config file in which we have products and delivery types, we check if there are less than 1000 products and if the product has less than 10 delivery types
![image](https://github.com/jkucaba/BasketSplittingAlgorithm/assets/116729677/1f3015f9-dd37-44bc-85c7-12fcc1d0b5ba)

## In the split function we firstly cout numer of products for each delivery type and then based on that number add products to result Map
![image](https://github.com/jkucaba/BasketSplittingAlgorithm/assets/116729677/0eaaa1f5-032e-4097-b0bc-5cf32fba3b85)
![image](https://github.com/jkucaba/BasketSplittingAlgorithm/assets/116729677/84ee5e2f-82fc-4f4b-9ee6-9d597238e72b)

## If you want to test it you need to pass the absolute path to the config file and then if you want to test the split method you need to pass a list of products.

## Test Output

## Firstly we print the products and delivery types
![image](https://github.com/jkucaba/BasketSplittingAlgorithm/assets/116729677/21d728fb-dbb8-4987-9269-79898cd38389)

## Then we print the counts for delivery types
![image](https://github.com/jkucaba/BasketSplittingAlgorithm/assets/116729677/06f4e73c-4ffd-42b7-ae6b-004501e1cccc)

## Lastly we print the result 
![image](https://github.com/jkucaba/BasketSplittingAlgorithm/assets/116729677/089cfadc-ab9a-4616-9d99-88f4af0d6f06)

