package data_access.interfaces;

import entity.comment.Question;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuestionReadDataAccessInterface {
    ArrayList<Question> getQuestion(String productID) throws SQLException;
}
