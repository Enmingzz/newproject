package entity;

import java.awt.*;
import java.util.ArrayList;

public class CommonProductFactory implements ProductFactory{
    public Product createProduct(Image image, String description, String title, float price, Integer rating, boolean isPending, String eTransferEmail, String buyerStudentNumber, String sellerStudentNumber, String address, CommonSchedule schedule, ArrayList<String> listTags) {
        return new CommonProduct(image,description, title, price, rating, isPending, eTransferEmail, buyerStudentNumber, sellerStudentNumber, address, schedule, listTags);
    }
}
