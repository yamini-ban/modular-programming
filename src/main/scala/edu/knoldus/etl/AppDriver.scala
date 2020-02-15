package edu.knoldus.etl

object AppDriver extends App {
  object ExampleTextFile extends SourceIsTheFile("example.txt")
  ExampleTextFile.processOfManipulation()
}
