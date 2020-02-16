package edu.knoldus.etl

object AppDriver extends App {
  object ExampleTextFile extends SourceIsTheFile("example1.txt")
  ExampleTextFile.processOfManipulation()
  ExampleTextFile.occurrenceOfEveryWordInTheData()
}
