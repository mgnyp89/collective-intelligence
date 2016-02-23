# collective-intelligence
repo for projects related to comp 41444 2016
This project contains all files associated with the collective intelligence module
The aim of the project is to develop a recommender system.

## Resources
Resources are contained in ./resources (mainly datasets)

## Testing
test.csv contains the first 15 (for simplicity) entries from the 100k.csv dataset.
test.csv is used to test the correctness of mathematical functions ad the behaviour of custom data structures
multiple.csv contains multiple entries for the same item / user. It's used to test statistical methods
All tests are in ./tst, they depend on junit

## How to Run

1) Use a runnable jar
 - original directory of the jar: /RecommenderSystem/out/artifacts/RecommenderSystem_jar. The runner uses a relative filepath
   for the dataset hence it should be rum from the root directory (I cpied a copy of the jar to the root)
    cd ./RecommenderSystem
 - run
   java -jar RecommenderSystem.jar


2) Load the project in your ide
   - The main is in ./src/core/Runner.java

3) You can also compile from commandline (javac *.java from the top directory recursively) and run main.

## Expected output:

  Loading the data
  Loaded dataset movies
  *******************************
  Starting basic evaluation of the data
  Number of users: 943
  Total number of movies: 1682
  Total number of movie ratings: 100000
  Number of ratings for rating class 1 : 6110
  Number of ratings for rating class 2 : 11370
  Number of ratings for rating class 3 : 27145
  Number of ratings for rating class 4 : 34174
  Number of ratings for rating class 5 : 21201
  Evaluator completed in 22 miliseconds
  *******************************
  Starting statistical evaluation
  Minimum ratings count for users: Optional[20]
  Maximum ratings count for users: Optional[737]
  Mean of ratings count for users: 106.04453870625663
  Median of ratings count for users: 65.0
  Standard deviation of ratings count for users: 100.87576748950151
  Evaluator completed in 41 miliseconds
  *******************************
  Starting evaluation of the data for items
  Minimum ratings count for items: Optional[1]
  Maximum ratings count for items: Optional[1]
  Mean of ratings count for items: 59.45303210463734
  Median of ratings count for items: 27.0
  Standard deviation of ratings count for items: 80.35675507144603
  Evaluator completed in 6 miliseconds
  *******************************
  Starting evaluation of the data
  strema total count  1682
  The density value is 0.06304669364224531
  Evaluator completed in 1 miliseconds
  *******************************