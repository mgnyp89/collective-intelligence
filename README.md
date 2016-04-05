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

## Expected output will change from week to week:

  WEEK 1: data written to standard output

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

  WEEK 2: data written to standard output + a .cvs file with all predictions (in the ./resources)

  Loading the data
  Loaded dataset movies
  *******************************
  Starting evaluation
  Running test No. 1
  Leave One Out Test completed in 2.214 seconds
  The average RMSE is: 0.1649
  1586126.0
  The coverage value is: 99.9912 %
  *******************************
  Starting run time evaluation
  Running test No. 2
  Leave One Out Test completed in 1.721 seconds
  Running test No. 3
  Leave One Out Test completed in 1.558 seconds
  Running test No. 4
  Leave One Out Test completed in 1.534 seconds
  Running test No. 5
  Leave One Out Test completed in 1.544 seconds
  Running test No. 6
  Leave One Out Test completed in 1.554 seconds
  Running test No. 7
  Leave One Out Test completed in 1.547 seconds
  Running test No. 8
  Leave One Out Test completed in 1.568 seconds
  Running test No. 9
  Leave One Out Test completed in 1.586 seconds
  Running test No. 10
  Leave One Out Test completed in 1.631 seconds
  The average run time is: 1.6457 seconds

  Week 3: data written to standard output + a .cvs file with all predictions (in the ./resources) per each neighbourhood
  size that was tested (5, 10, 30, 50, 100, 150, 200, 400, 1000)

  Loading the data
  Loaded dataset movies
  *******************************
  Starting evaluation
  Running test No. 1
  Leave One Out Test completed in 9.935 seconds
  The average RMSE is: 0.2832
  The coverage value is: 99.9895 %
  *******************************
  Starting run time evaluation
  Leave One Out Test completed in 8.846 seconds
  Leave One Out Test completed in 9.137 seconds
  Leave One Out Test completed in 8.873 seconds
  Leave One Out Test completed in 9.286 seconds
  Leave One Out Test completed in 8.701 seconds
  Leave One Out Test completed in 8.595 seconds
  Leave One Out Test completed in 8.838 seconds
  Leave One Out Test completed in 8.477 seconds
  Leave One Out Test completed in 8.601 seconds
  The average run time is: 8.9289 seconds

  Week 4: data written to standard output + a .cvs file with all predictions (in the ./resources) per each neighbourhood
  size that was tested (5, 10, 30, 50, 100, 150, 200, 400, 1000)

  Loading the data
  Loaded dataset movies
  *******************************
  Starting evaluation
  Neighbourhood size:  5
  Leave One Out Test completed in 4.915 seconds
  The average RMSE is: 1.1443
  The coverage value is: 95.0062 %
  *******************************
  Neighbourhood size:  10
  Leave One Out Test completed in 4.718 seconds
  The average RMSE is: 1.0485
  The coverage value is: 96.3262 %
  *******************************
  Neighbourhood size:  30
  Leave One Out Test completed in 4.88 seconds
  The average RMSE is: 0.8772
  The coverage value is: 98.8211 %
  *******************************
  Neighbourhood size:  50
  Leave One Out Test completed in 5.048 seconds
  The average RMSE is: 0.7768
  The coverage value is: 99.505 %
  *******************************
  Neighbourhood size:  100
  Leave One Out Test completed in 5.494 seconds
  The average RMSE is: 0.686
  The coverage value is: 99.8582 %
  *******************************
  Neighbourhood size:  150
  Leave One Out Test completed in 5.81 seconds
  The average RMSE is: 0.6197
  The coverage value is: 99.9364 %
  *******************************
  Neighbourhood size:  200
  Leave One Out Test completed in 6.206 seconds
  The average RMSE is: 0.5707
  The coverage value is: 99.9604 %
  *******************************
  Neighbourhood size:  300
  Leave One Out Test completed in 6.974 seconds
  The average RMSE is: 0.5305
  The coverage value is: 99.978 %
  *******************************
  Neighbourhood size:  400
  Leave One Out Test completed in 8.658 seconds
  The average RMSE is: 0.4982
  The coverage value is: 99.9844 %
  *******************************
  Neighbourhood size:  1000
  Leave One Out Test completed in 8.328 seconds
  The average RMSE is: 0.4724
  The coverage value is: 99.9895 %
  *******************************
  cosineDistanceBasedPredictionEvaluator:
  The average run time is: 6.1031 seconds

   Week 5: data written to standard output + a .cvs file with all predictions (in the ./resources) per each neighbourhood
   size that was tested (5, 10, 30, 50, 100, 150, 200, 400, 1000)

   *******************************
   Neighbourhood size:  5
   Leave One Out Test completed in 10.145 seconds
   The average RMSE is: 0.1607
   The coverage value is: 100 %
   *******************************
   Neighbourhood size:  10
   Leave One Out Test completed in 15.062 seconds
   The average RMSE is: 0.1589
   The coverage value is: 100 %
   *******************************
   Neighbourhood size:  30
   Leave One Out Test completed in 40.338 seconds
   The average RMSE is: 0.1566
   The coverage value is: 100 %
   *******************************
   Neighbourhood size:  50
   Leave One Out Test completed in 82.73 seconds
   The average RMSE is: 0.1552
   The coverage value is: 100 %
   *******************************
   Neighbourhood size:  100
   Leave One Out Test completed in 141.278 seconds
   The average RMSE is: 0.154
   The coverage value is: 100 %
   *******************************
   Neighbourhood size:  150
   Leave One Out Test completed in 235.027 seconds
   The average RMSE is: 0.1529
   The coverage value is: 100 %
   *******************************
   Neighbourhood size:  200
   Leave One Out Test completed in 330.726 seconds
   The average RMSE is: 0.1516
   The coverage value is: 100 %
   *******************************
   Neighbourhood size:  300
   Leave One Out Test completed in 524.436 seconds
   The average RMSE is: 0.1495
   The coverage value is: 100 %
   *******************************
   Neighbourhood size:  400
   Leave One Out Test completed in 687.589 seconds
   The average RMSE is: 0.147
   The coverage value is: 100 %
   *******************************
   Neighbourhood size:  1000
   Leave One Out Test completed in 1030.985 seconds
   The average RMSE is: 0.1413
   The coverage value is: 100 %