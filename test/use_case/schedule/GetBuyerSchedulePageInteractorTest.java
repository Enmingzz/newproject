package use_case.schedule;

import entity.product.CommonProduct;
import entity.product.CommonProductFactory;
import entity.product.Product;
import entity.product.ProductFactory;
import entity.schedule.CommonScheduleFactory;
import entity.schedule.Schedule;
import entity.schedule.ScheduleFactory;
import entity.user.CommonUser;
import entity.user.CommonUserFactory;
import entity.user.User;
import entity.user.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GetBuyerSchedulePageInteractorTest {
    private User buyer;
    private Product product;

    @BeforeEach
    void setUp() throws IOException {
        ProductFactory productFactory = new CommonProductFactory();
        Image image = ImageIO.read(new File("src/pic/testpic1.png"));
        String des = "This is a description";
        float price = 1;
        String title = "This is a title";
        int state = 2;
        int rating = 0;
        String eTransferEmail = "example@email.com";
        String sellerStudentNumber = "1111111111";
        String address = "BA 3175";
        LocalDateTime buyerTime = null;
        ArrayList<LocalDateTime> sellerTime = new ArrayList<>();
        sellerTime.add(LocalDateTime.parse("2024-07-13T12:00:00"));
        sellerTime.add(LocalDateTime.parse("2024-07-13T13:00:00"));
        ScheduleFactory scheduleFactory = new CommonScheduleFactory();
        Schedule schedule = scheduleFactory.createSchedule(buyerTime, sellerTime);
        ArrayList<String> listTags = new ArrayList<>();
        listTags.add("Tag 1");
        String productID = "id_1";

        product =productFactory.createProduct(image, des, title, price, state, rating, eTransferEmail,
                sellerStudentNumber, address, listTags, productID, schedule);


        String name = "tabby cat";
        String password = "password";
        String email = "tabby@mail.utoronto.ca";
        float userRating = 5;
        String studentNumber = "1234567890";

        UserFactory userFactory = new CommonUserFactory();
        buyer = userFactory.createUser(name, password, email, userRating, studentNumber);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void execute() throws SQLException, IOException {
        GetBuyerSchedulePageOutputBoundary getBuyerSchedulePagePresenter = new GetBuyerSchedulePageOutputBoundary() {
            @Override
            public void prepareSuccessfulView(GetBuyerSchedulePageOutputData getBuyerSchedulePageOutputData) {
                assertEquals(product, getBuyerSchedulePageOutputData.getProduct());
                assertEquals(buyer, getBuyerSchedulePageOutputData.getBuyer());
            }
        };

        GetBuyerSchedulePageInputData inputData =
                new GetBuyerSchedulePageInputData(buyer, product);
        GetBuyerSchedulePageInteractor interactor =
                new GetBuyerSchedulePageInteractor(getBuyerSchedulePagePresenter);
        interactor.execute(inputData);
    }
}