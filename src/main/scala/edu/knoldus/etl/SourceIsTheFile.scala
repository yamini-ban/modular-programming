package edu.knoldus.etl

import java.io.{File, PrintWriter}

import edu.knoldus.etl.exception.CustomException

import scala.io.Source

abstract class ManipulateFile extends Manipulation {

  val nameOfTheFileToManipulate: String

}

class SourceIsTheFile(val nameOfTheFileToManipulate: String) extends ManipulateFile with CountOccurrenceOfWords {

  override def processOfManipulation() {
    val fetchFile = new File(nameOfTheFileToManipulate)
    if (fetchFile.exists && fetchFile.isFile) {
      val directoryName = "Manipulated-Result"
      val _ = new File(directoryName).mkdir
      val contentOfFetchedFile = Source.fromFile(fetchFile)
      val linesInFile = contentOfFetchedFile.getLines
      val printWriter = new PrintWriter(new File(s"$directoryName/O-${fetchFile.getName}"))
      linesInFile.foreach(line => printWriter.write(line.toUpperCase()+"\n"))
      printWriter.close()
      contentOfFetchedFile.close
    }
    else throw new CustomException("File does not exist.")
  }

  override def occurrenceOfEveryWordInTheData() {

    val fetchFile = new File(nameOfTheFileToManipulate)
    if (fetchFile.exists && fetchFile.isFile) {
      val directoryName = "Count-Result"
      val _ = new File(directoryName).mkdir
      val contentOfFetchedFile = Source.fromFile(fetchFile)
      val linesInFile = contentOfFetchedFile.getLines
      val printWriter = new PrintWriter(new File(s"${directoryName}/O-${fetchFile.getName}"))

      val count = linesInFile.toList.foldLeft(Map.empty[String,Int])((result, line) => {
        line.split(" ").foldLeft(Map.empty[String, Int])((_, word) => {
          result.get(word) match {
            case Some(value) => result + (word -> (getOccurrence(line, word) + value))
            case _ => result + (word -> getOccurrence(line, word))
          }
        })
      })

    count.foreach{ tuple => {
      tuple match {
        case (word: String, occurring: Int) => printWriter.write(s"$word -> ${occurring}\n")
      }
    }}

      printWriter.close()
      contentOfFetchedFile.close
    }

    else throw new CustomException("File does not exist.")

  }

  private def getOccurrence(line: String, occurrenceOfWord: String): Int = {
      line.split(" ").foldLeft(0)((occurrence, element) => {
      if (element == occurrenceOfWord)
        occurrence + 1
      else
        occurrence
    })
  }

}
