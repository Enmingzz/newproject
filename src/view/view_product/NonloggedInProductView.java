package view.view_product;

import app.Main;
import entity.comment.Question;
import entity.product.Product;
import entity.user.CommonUserFactory;
import entity.user.User;
import entity.user.UserFactory;
import interface_adapter.login.ViewLoginPageController;
import interface_adapter.logout.LogOutController;
import interface_adapter.main_page.MainPageController;
import interface_adapter.profile.view_profile.ViewProfileController;
import interface_adapter.search_product.GetSearchPageController;
import interface_adapter.shopping_cart.ShoppingCartController;
import interface_adapter.signup.ViewSignupPageController;
import interface_adapter.view_product.UnloggedInState;
import interface_adapter.view_product.UnloggedInViewModel;
import view.TopBarSampleView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * This class provides a view for non-logged-in users to explore product details. It includes functionality
 * to display product information, questions and answers related to the product, and options to navigate
 * to other parts of the application like login or signup pages.
 *
 * The class uses several controllers to manage user interaction with the application and to update the view
 * according to user actions and application state changes.
 */


public class NonloggedInProductView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "non login view product view";
    private final UnloggedInViewModel nonLoggedInViewModel;

    //Top Bar stuff
    private final GetSearchPageController getSearchPageController;
    private final ViewSignupPageController viewSignupPageController;
    private final ViewLoginPageController viewLoginPageController;
    private final ShoppingCartController shoppingCartController;
    private final LogOutController logOutController;
    private final ViewProfileController viewProfileController;
    private final MainPageController mainPageController;


    private final JButton cancel;
    private final JButton addToCart;

    private ProductInfoLabelTextPanel productInfo;
    private JPanel qAInfo;

    /**
     * Constructs a NonloggedInProductView with specific controllers and view model to manage the view's state and interactions.
     *
     * @param nonLoggedInViewModel the view model containing state and operations specific to non-logged-in users.
     * @param viewLoginPageController controller to manage login operations.
     * @param mainPageController controller to navigate to the main page.
     * @param getSearchPageController controller to access the search page functionality.
     * @param viewSignupPageController controller to manage signup operations.
     * @param shoppingCartController controller to handle shopping cart operations.
     * @param logOutController controller to manage logout operations.
     * @param viewProfileController controller to view user profiles.
     */

    public NonloggedInProductView(UnloggedInViewModel nonLoggedInViewModel,
                                  ViewLoginPageController viewLoginPageController,
                                  MainPageController mainPageController,
                                  GetSearchPageController getSearchPageController,
                                  ViewSignupPageController viewSignupPageController,
                                  ShoppingCartController shoppingCartController,
                                  LogOutController logOutController,
                                  ViewProfileController viewProfileController){
        this.nonLoggedInViewModel = nonLoggedInViewModel;

        this.getSearchPageController = getSearchPageController;
        this.viewSignupPageController  = viewSignupPageController;
        this.viewLoginPageController = viewLoginPageController;
        this.shoppingCartController = shoppingCartController;
        this.logOutController = logOutController;
        this.viewProfileController = viewProfileController;
        this.mainPageController = mainPageController;

        this.nonLoggedInViewModel.addPropertyChangeListener(this);



        JLabel title = new JLabel(nonLoggedInViewModel.TITLE_LABEL+", but you are not logged in yet :(");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //(1)product_info
        Product wtv_product = nonLoggedInViewModel.getState().getProduct();
        final JLabel image = new JLabel(String.valueOf(wtv_product.getImage()));//image???
        final JLabel description = new JLabel(wtv_product.getDescription());
        final JLabel price = new JLabel(String.valueOf(wtv_product.getPrice()));
        final JLabel _title = new JLabel(wtv_product.getTitle());
        final JLabel rating = new JLabel(String.valueOf(wtv_product.getRating()));
        final JLabel state = new JLabel(String.valueOf(wtv_product.getState()));
        final JLabel address = new JLabel(wtv_product.getAddress());
        final JLabel lstTags = new JLabel(String.valueOf(wtv_product.getListTags()));//what will valueOf list look like???
        final JLabel productID = new JLabel(wtv_product.getProductID());

        productInfo = new ProductInfoLabelTextPanel(_title, image, description, price, rating, state, address,
                lstTags, productID);


        //(2)show q_and_a
        final JPanel qAInfo = new JPanel();

        final JLabel qA_title = new JLabel("Q&A:");

        ArrayList<Question> lst_question = nonLoggedInViewModel.getState().getQuestion();
        final JPanel qA_TextPanel = new JPanel();
        for (Question question : lst_question) {

            String answer_content = question.getAnswer().getDescription();
            String question_content = question.getDescription();

            JLabel q = new JLabel(question_content);
            JLabel a = new JLabel(answer_content);

            BuyerQAInfoLabelTextPanel panel = new BuyerQAInfoLabelTextPanel(q, a);
            qA_TextPanel.add(panel);
        }

        qAInfo.add(qA_title);
        qAInfo.add(qA_TextPanel);


        //(3)buttons
        JPanel buttons = new JPanel();
        cancel = new JButton(nonLoggedInViewModel.CANCEL_BUTTON_LABEL);
        addToCart = new JButton(nonLoggedInViewModel.ADD_TO_CART);


        buttons.add(cancel);
        buttons.add(addToCart);


        class AddTtoCartButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(addToCart)) {
                    try {
                        viewLoginPageController.execute();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


        class CancelButtonListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(evt.getSource().equals(cancel)){
                    try{
                        UserFactory userFactory = new CommonUserFactory();
                        User emptyuser = userFactory.createUser("","","",0, "");
                        mainPageController.execute(emptyuser);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        addToCart.addActionListener(new AddTtoCartButtonListener());
        cancel.addActionListener(new CancelButtonListener());

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(productInfo);
        this.add(qAInfo);
        this.add(buttons);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Responds to property changes and updates the UI accordingly. This method ensures that the view reflects
     * the current state of the model after changes occur.
     *
     * @param evt the property change event, indicating changes in the model state that may affect the view.
     */

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        UnloggedInState newState = (UnloggedInState) evt.getNewValue();
        if(newState.getIsChanged()){
            JLabel title= new JLabel(String.valueOf(newState.getProduct().getTitle()));
            JLabel image = new JLabel(String.valueOf(newState.getProduct().getImage()));
            JLabel des = new JLabel(String.valueOf(newState.getProduct().getDescription()));
            JLabel price = new JLabel(String.valueOf(newState.getProduct().getPrice()));
            JLabel rating = new JLabel(String.valueOf(newState.getProduct().getRating()));
            JLabel pro_state = new JLabel(String.valueOf(newState.getProduct().getState()));
            JLabel address = new JLabel(String.valueOf(newState.getProduct().getAddress()));
            JLabel lstTags = new JLabel(String.valueOf(newState.getProduct().getListTags()));
            JLabel proId = new JLabel(String.valueOf(newState.getProduct().getProductID()));
            this.productInfo = new ProductInfoLabelTextPanel(title, image, des, price, rating, pro_state, address,lstTags, proId);

            //(2)show q_and_a
            qAInfo = new JPanel();

            final JLabel qA_title = new JLabel("Q&A:");

            ArrayList<Question> lst_question = newState.getQuestion();

            final JPanel qA_TextPanel = new JPanel();
            for (Question question : lst_question) {

                String answer_content = question.getAnswer().getDescription();
                String question_content = question.getDescription();

                JLabel q = new JLabel(question_content);
                JLabel a = new JLabel(answer_content);

                BuyerQAInfoLabelTextPanel panel = new BuyerQAInfoLabelTextPanel(q, a);
                qA_TextPanel.add(panel);
            }

            qAInfo.add(qA_title);
            qAInfo.add(qA_TextPanel);

            JPanel topBar = new TopBarSampleView(newState.getUser(),
                    getSearchPageController, viewSignupPageController, viewLoginPageController, shoppingCartController, logOutController, viewProfileController, mainPageController);
            this.add(topBar);

            newState.setIsChanged(false);
        }
    }
}