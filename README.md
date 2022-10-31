# What is this repository ?

This repository contains exercises given in the algorithmic course to students in Computer Science at Université Catholique de Louvain.
The content of the exercises are based on the book [Algorithm 4th Edition, by Robert Sedgewick and Kevin Wayne](https://algs4.cs.princeton.edu/home/).
The exercises are meant to be **easily** exportable so you can reuse them in other courses.

# Structure of the repository

The repository is built as a java project (we use [Maven](https://maven.apache.org/index.html) to build the project). This means that
  - For all the exercises, the solution is given in this repository.
  - The solution can be removed by stripping the files using the in-file tags
  - Running `mvn test` run all the tests for the exercises and ensure the exercise is doable by the students
  
The mains directories of the project:
- `data/` contains data file for the parameterized tests
- `libs/` contains some test libraries
- `src/{main,test}/java` contains, respectively, the exercises and the associated tests

The rest of the files are specific to us and how student submissions are handled (using [INGInious](https://inginious.org/))

# How to create an exercise

An exercise is composed exclusively of the following files:
1. `src/main/java/Exercise.java` contains the statement with the functions, classes, etc. to fill by the students
2. `src/test/java/ExerciseTest.java` contains the unit test for the students to check their solution
3. An optional `src/main/java/ExerciseGenerator.java` data generator file for parameterized tests

**Every additional class needed for the exercise must be an internal class in `Exercise.java`**. This is an intended design, the cost of the overhead in
the code is compensated by:
- It is easier for students to navigate in the exercise. Everything they need is in one file and they can concentrate on the algorithmic part.
- We give to students a Maven project in a zip file for each exercise. Identifying all file by the exercise name makes it easier to create such archive.
- It is easy to extract some exercise from the repository, only the three mentioned file needs to be copied in order to have the full exercise.

## Example

Here is an example of exercise that would be located at `src/main/java/Mean.java`

```
/**
 * In this exercise we ask you to compute the mean of two numbers, `a` and `b`. You should return an
 * integer. This means that the mean of 2 and 5 is not 3.5 but 3
 */
public class Mean {
  
  /**
   * Returns the integer means of two numbers
   * @params a The first number in the mean
   * @params b The second number in the mean
   * @returns the mean of a and b
   */
  public static int mean(int a, int b) {
    // BEGIN STRIP
    return (a+b)/2;
    // END STRIP
    // TODO
    // STUDENT return 0;
  }
}
```
After processing it, the file given to the students (alongside a test file) is
```
/**
 * In this exercise we ask you to compute the mean of two numbers, `a` and `b`. You should return an
 * integer. This means that the mean of 2 and 5 is not 3.5 but 3
 */
public class Mean {
  
  /**
   * Returns the integer means of two numbers
   * @params a The first number in the mean
   * @params b The second number in the mean
   * @returns the mean of a and b
   */
  public static int mean(int a, int b) {
    // TODO
    return 0;
  }
}
```

- The statement is given as comments at the top of the class.
- The method(s) that the students should implement can be anything and they are free to add additional methods (in our case they submit the whole file for verification)
- The solution of the exercise is given but between tags to ensure that it is removed before giving the exercise to the students
- A tag STUDENT is used if a return line must be specified (the files given to the students **MUST** compile even without the solution)
- The associated tests are located at `src/test/java/MeanTest.java`. This is **essential** that only `Test` is added for the filename.

The goal is that an exercise is composed of
1. An exercise file in `src/main/java`
2. A test file in `src/test/java`
3. An optional instance generator named `ExerciseGenerator.java`

# Contributing

1. If you spot a mistake in the tests, or the solutions of the exercises, do not hesitate to open a PR with the fix, or report it in an issue
2. f you have an idea for an exercise that is not yet implemented, feel free to open an issue to describe the exercise, or a PR with the code formatted as explained above

