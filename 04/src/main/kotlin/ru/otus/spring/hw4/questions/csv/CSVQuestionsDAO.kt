package ru.otus.spring.hw4.questions.csv

import com.opencsv.CSVReader
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import ru.otus.spring.hw4.Question
import ru.otus.spring.hw4.questions.QuestionsDAO
import java.io.FileReader

@Service
class CSVQuestionsDAO(
        gameFile: Resource
) : QuestionsDAO {
    private lateinit var questions: List<Question>

    init {
        val lines = FileReader(gameFile.file).use { fileReader ->
            CSVReader(fileReader).use {
                it.readAll()
            }
        }
        questions = readQuestions(lines)
    }

    private fun readQuestions(questionLines: List<Array<String>>): List<Question> {
        return questionLines.mapNotNull(this::convertLineToQuestion)
    }

    private fun convertLineToQuestion(line: Array<String>): Question? {
        val question = line[0]
        val answer = line[1]
        return Question(question, answer)
    }

    override fun getQuestion(questionIndex: Int): Question {
        return questions[questionIndex]
    }

    override fun getQuestionCount(): Int {
        return questions.size
    }

}

