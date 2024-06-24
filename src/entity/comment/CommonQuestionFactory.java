package entity.comment;

public class CommonQuestionFactory implements QuestionFactory{
    public Question createQuestion(String description, String studentNumber, Answer answer){
        return new CommonQuestion(description, studentNumber, answer);
    }
}
