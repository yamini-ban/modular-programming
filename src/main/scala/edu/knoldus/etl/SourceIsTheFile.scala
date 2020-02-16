package edu.knoldus.etl

import java.io.{File, PrintWriter}

import edu.knoldus.etl.exception.CustomException

import scala.io.Source

abstract class ManipulateFile extends Manipulation {

  val nameOfTheFileToManipulate: String

}

class SourceIsTheFile(val nameOfTheFileToManipulate: String) extends ManipulateFile with CountOccurrenceOfWords {

  override def processOfManipulation(): Unit = {
    val fetchFile = new File(nameOfTheFileToManipulate)
    if (fetchFile.exists && fetchFile.isFile) {
      val directoryName = "Manipulated-Result"
      val _ = new File(directoryName).mkdir
      val contentOfFetchedFile = Source.fromFile(fetchFile)
      val linesInFile = contentOfFetchedFile.getLines
      val printWriter = new PrintWriter(new File(s"$directoryName/O-${fetchFile.getName}"))
      linesInFile.foreach(line => printWriter.write(line.toUpperCase() + "\n"))
      printWriter.close()
      contentOfFetchedFile.close
    }
    else throw new CustomException("File does not exist.")
  }

  override def occurrenceOfEveryWordInTheData(): Unit = {

    val fetchFile = new File(nameOfTheFileToManipulate)
    if (fetchFile.exists && fetchFile.isFile) {
      val directoryName = "Count-Result"
      val _ = new File(directoryName).mkdir
      val contentOfFetchedFile = Source.fromFile(fetchFile)
      val linesInFile = contentOfFetchedFile.getLines

      val printWriter = new PrintWriter(new File(s"${directoryName}/O-${fetchFile.getName}"))

      val count = getMapOfWordsWithCount(linesInFile.toList)
      count.foreach { tuple => {
        tuple match {
          case (word: String, occurring: Int) => printWriter.write(s"$word -> ${occurring}\n")
        }
      }
      }
      printWriter.close()
      contentOfFetchedFile.close
    }
    else {
      throw new CustomException("File does not exist.")
    }
  }

  private def getMapOfWordsWithCount(listOfLinesFromFile: List[String]) = {
    listOfLinesFromFile.foldLeft(Map.empty[String, Int])((result, line) => {
      val record = line.split(" ").foldLeft(Map.empty[String, Int])((record, word) => {
        (result.get(word), record.get(word)) match {
          case (Some(valueResult), Some(valueRecord)) => record + (word -> (getOccurrenceInLine(line, word) + valueResult + valueRecord))
          case (Some(valueResult), _) => record + (word -> (getOccurrenceInLine(line, word) + valueResult))
          case (None, Some(valueRecord)) => record + (word -> valueRecord)
          case (None, None) => record + (word -> getOccurrenceInLine(line, word))
        }
      })
      result ++ record
    })
  }

  private def getOccurrenceInLine(line: String, occurrenceOfWord: String): Int = {
    line.split(" ").foldLeft(0)((occurrence, element) => {
      if (element == occurrenceOfWord) occurrence + 1 else occurrence
    })
  }

}
