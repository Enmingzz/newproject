package interface_adapter.view_product;

import entity.comment.*;
import entity.product.Product;
import entity.user.User;

public class ReplyQuestionState {
    private QuestionFactory questionFactory = new CommonQuestionFactory();
    private AnswerFactory answerFactory = new CommonAnswerFactory();
    private Question question = questionFactory.createQuestion("", "", answerFactory.createAnswer("", ""),"" );
    private Product product = null;
    private User user = null;

    public ReplyQuestionState(Question question, Product product, User user) {
        this.question = question;
        this.product = product;
        this.user = user;
    }

    public ReplyQuestionState(){};

    public Question getQuestion() {return question;}

    public Product getProduct() {return product;}

    public User getUser() {return user;}


    public void setQuestion(Question question){
        this.question = question;
    }

    public void setProduct(Product product){this.product = product;}

    public void setUser(User user){this.user = user;}

}
