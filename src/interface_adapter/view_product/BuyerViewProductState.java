package interface_adapter.view_product;

import entity.comment.Question;
import entity.product.Product;

import java.util.ArrayList;

public class BuyerViewProductState {
    Product product;
    ArrayList<Question> lst_question;
    String prompt_words;

    public BuyerViewProductState(Product product, ArrayList<Question> lst_question, String prompt_words){
        this.product = product;
        this.lst_question = lst_question;
        this.prompt_words = prompt_words;
    }

    public Product getProduct(){
        return product;
    }

    public ArrayList<Question> getQuestion(){
        return lst_question;
    }

    public String getPrompt_words(){return prompt_words;}

    public void setProduct(Product product){
        this.product = product;
    }

    public void setLst_question(ArrayList<Question> lst_question){
        this.lst_question = lst_question;
    }

    public void setPrompt_words(String prompt_words){this.prompt_words = prompt_words;}

    public BuyerViewProductState(){}
}
