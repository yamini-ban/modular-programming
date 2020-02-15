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
      linesInFile.foreach(line => {
        line.split(" ").foreach(word => printWriter.write(s"$word -> ${line.split(word).length - 1}\n"))
      })

      /*val _ = linesInFile.foldLeft(Map[String, Int])((_, line) => {
        line.split(" ").foldLeft(Map[String, Int])((result, word) => {
          result + (word -> getOccurrence(line, word))
        })
      })*/

      val map = Map.empty[String, Int]
      for {
        line <- linesInFile
        word <- line
        map :+ (word -> getOccurrence(line, word))
      } yield map

      printWriter.close()
      contentOfFetchedFile.close
    }
//    else throw new CustomException("File does not exist.")

  }

  private def getOccurrence(line: String, occurrenceOfWord: String): Int = {
    if(line.contains(occurrenceOfWord)) {
      line.count(o)
    }
  }

}
