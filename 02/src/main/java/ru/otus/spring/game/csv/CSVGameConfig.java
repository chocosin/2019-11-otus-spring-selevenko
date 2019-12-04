package ru.otus.spring.game.csv;

import com.opencsv.CSVReader;
import org.springframework.core.io.Resource;
import ru.otus.spring.game.GameConfig;
import ru.otus.spring.game.Question;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CSVGameConfig implements GameConfig {
    private List<Question> questions;

    public CSVGameConfig(Resource gameFile) throws IOException {
        try (
                FileReader fileReader = new FileReader(gameFile.getFile());
                CSVReader csvReader = new CSVReader(fileReader)
        ) {
            questions = readQuestions(csvReader);
        }
    }

    private List<Question> readQuestions(Iterable<String[]> questions) {
        return StreamSupport.stream(questions.spliterator(), false)
                .map(CSVGameConfig::convertLineToQuestion)
                .collect(Collectors.toList());
    }

    private static Question convertLineToQuestion(String[] line) {
        String question = line[0];
        String answer = line[1];
        return new Question(question, answer);
    }

    @Override
    public Question getQuestion(int questionIndex) {
        return questions.get(questionIndex);
    }

    @Override
    public int getQuestionCount() {
        return questions.size();
    }
}
