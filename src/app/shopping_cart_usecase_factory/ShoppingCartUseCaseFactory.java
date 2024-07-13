package app.shopping_cart_usecase_factory;

import data_access.factories.interfaces.product.DataBaseProductReadAllDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.product.DataBaseProductReadByIdDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.product.DatabaseProductReadByNameDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.product.DatabaseProductUpdateStateDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.question.DatabaseQuestionReadDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.shopping_cart.DatabaseShoppingCartReadDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.shopping_cart.DatabaseShoppingCartUpdateDeleteDataAccessObjectFactoryInterface;
import data_access.factories.objects.product.DataBaseProductReadByIdDataAccessObjectFactory;
import data_access.factories.objects.product.DatabaseProductReadAllDataAccessObjectFactory;
import data_access.factories.objects.product.DatabaseProductReadByNameDataAccessObjectFactory;
import data_access.factories.objects.product.DatabaseProductUpdateStateDataAccessObjectFactory;
import data_access.factories.objects.question.DatabaseQuestionReadDataAccessObjectFactory;
import data_access.factories.objects.shopping_cart.DatabaseShoppingCartReadDataAccessObjectFactory;
import data_access.factories.objects.shopping_cart.DatabaseShoppingCartUpdateDeleteDataAccessObjectFactory;
import data_access.interfaces.product.ProductReadAllDataAccessInterface;
import data_access.interfaces.product.ProductReadByIdDataAccessInterface;
import data_access.interfaces.product.ProductReadByNameDataAccessInterface;
import data_access.interfaces.product.ProductUpdateStateDataAccessInterface;
import data_access.interfaces.question.QuestionReadDataAccessInterface;
import data_access.interfaces.shopping_cart.ShoppingCartReadDataAccessInterface;
import data_access.interfaces.shopping_cart.ShoppingCartUpdateDeleteDataAccessInterface;
import entity.comment.AnswerFactory;
import entity.comment.CommonAnswerFactory;
import entity.comment.CommonQuestionFactory;
import entity.comment.QuestionFactory;
import entity.product.CommonProductFactory;
import entity.product.ProductFactory;
import entity.schedule.CommonScheduleFactory;
import entity.schedule.ScheduleFactory;
import entity.shopping_cart.CommonShoppingCartFactory;
import entity.shopping_cart.ShoppingCartFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login.ViewLoginPageController;
import interface_adapter.login.ViewLoginPagePresenter;
import interface_adapter.logout.LogOutController;
import interface_adapter.logout.LogOutPresenter;
import interface_adapter.main_page.MainPageController;
import interface_adapter.main_page.MainPagePresenter;
import interface_adapter.main_page.MainPageViewModel;
import interface_adapter.profile.view_profile.ViewProfileController;
import interface_adapter.profile.view_profile.ViewProfilePresenter;
import interface_adapter.profile.view_profile.ViewProfileViewModel;
import interface_adapter.rating.GetRatePageController;
import interface_adapter.rating.RateProductController;
import interface_adapter.rating.RateProductViewModel;
import interface_adapter.schedule.BuyerSelectScheduleController;
import interface_adapter.schedule.BuyerSelectScheduleViewModel;
import interface_adapter.schedule.GetBuyerSchedulePageController;
import interface_adapter.search_product.*;
import interface_adapter.shopping_cart.*;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.signup.ViewSignupPageController;
import interface_adapter.signup.ViewSignupPagePresenter;
import interface_adapter.view_product.*;
import use_case.search_product.*;
import use_case.signup.ViewSignupPageInputBoundary;
import use_case.signup.ViewSignupPageInteractor;
import use_case.signup.ViewSignupPageOutputBoundary;
import use_case.login.ViewLoginPageInputBoundary;
import use_case.login.ViewLoginPageInteractor;
import use_case.login.ViewLoginPageOutputBoundary;
import use_case.logout.LogOutInputBoundary;
import use_case.logout.LogOutInteractor;
import use_case.logout.LogOutOutputBoundary;
import use_case.main_page.ShowMainPageInputBoundary;
import use_case.main_page.ShowMainPageInteractor;
import use_case.main_page.ShowMainPageOutputBoundary;
import use_case.profile.view_profile.ViewProfileInputBoundary;
import use_case.profile.view_profile.ViewProfileInteractor;
import use_case.profile.view_profile.ViewProfileOutputBoundary;
import use_case.shopping_cart.*;
import use_case.view_product.ViewProductInputBoundary;
import use_case.view_product.ViewProductInteractor;
import use_case.view_product.ViewProductOutputBoundary;
import view.shopping_cart.ShoppingCartView;
import view.view_product.NonloggedInProductView;

import java.io.IOException;
import java.sql.SQLException;

public class ShoppingCartUseCaseFactory {

    public static ShoppingCartView create(ShoppingCartViewModel shoppingCartViewModel,
                                          BuyerViewProductViewModel buyerViewProductViewModel,
                                          SellerViewProductViewModel sellerViewProductViewModel,
                                          UnloggedInViewModel unloggedInViewModel,
                                          BuyerSelectScheduleViewModel buyerSelectScheduleViewModel,
                                          RateProductViewModel rateProductViewModel,
                                          ViewProfileViewModel viewProfileViewModel,
                                          MainPageViewModel mainPageViewModel,
                                          ViewManagerModel viewManagerModel) throws SQLException {
        //TODO need to implement this method
        ViewProductController viewProductController =
                ShoppingCartUseCaseFactory.createViewProductController(buyerViewProductViewModel,
                        sellerViewProductViewModel, viewManagerModel, unloggedInViewModel);
        PurchaseController purchaseController =
                ShoppingCartUseCaseFactory.createPurchaseController(shoppingCartViewModel,
                        viewManagerModel);
        DeleteShoppingCartProductController deleteShoppingCartProductController =
                ShoppingCartUseCaseFactory.createDeleteShoppingCartProductController(
                        shoppingCartViewModel, viewManagerModel);
        GetBuyerSchedulePageController getBuyerSelectScheduleController =
                ShoppingCartUseCaseFactory.createBuyerSelectScheduleController();
        //TODO: ADD CONTROLLER INPUT
        ConfirmController confirmController = ShoppingCartUseCaseFactory.createConfirmController();
        GetRatePageController getRatePageController =
                ShoppingCartUseCaseFactory.createRateProductController();
        ShoppingCartController shoppingCartController = ShoppingCartUseCaseFactory.createShoppingCartController();



        return new ShoppingCartView(shoppingCartViewModel,
                viewProductController,
                purchaseController,
                deleteShoppingCartProductController,
                getBuyerSelectScheduleController,
                confirmController,
                getRatePageController,);
    }

    private static ShoppingCartController createShoppingCartController(ViewManagerModel viewManagerModel, ShoppingCartViewModel shoppingCartViewModel) throws SQLException {
        ShoppingCartFactory shoppingCartFactory = new CommonShoppingCartFactory();
        ProductFactory productFactory = new CommonProductFactory();
        ShoppingCartPresenter presenter = new ShoppingCartPresenter(viewManagerModel,
                shoppingCartViewModel);
        DatabaseShoppingCartReadDataAccessObjectFactoryInterface databaseShoppingCartReadDataAccessObjectFactory
                = new DatabaseShoppingCartReadDataAccessObjectFactory();
        ScheduleFactory scheduleFactory = new CommonScheduleFactory();
        ShoppingCartReadDataAccessInterface shoppingCartReadDataAccess =
                databaseShoppingCartReadDataAccessObjectFactory.create(shoppingCartFactory,
                        productFactory, scheduleFactory);
        ShowShoppingCartInputBoundary showShoppingCartInteractor =
                new ShowShoppingCartInteractor(presenter, shoppingCartReadDataAccess);
        return new ShoppingCartController(showShoppingCartInteractor);
    }

    private static ViewProductController createViewProductController
            (BuyerViewProductViewModel buyerViewProductViewModel, SellerViewProductViewModel
                    sellerViewProductViewModel, ViewManagerModel viewManagerModel,
             UnloggedInViewModel non_loggedInProductView) throws SQLException {
        ViewProductOutputBoundary viewProductPresenter =
                new ViewProductPresenter(buyerViewProductViewModel, sellerViewProductViewModel,
                        non_loggedInProductView, viewManagerModel);
        DatabaseQuestionReadDataAccessObjectFactoryInterface databaseQuestionReadDataAccessObjectFactory = new DatabaseQuestionReadDataAccessObjectFactory();
        QuestionFactory commonQuestionFactory = new CommonQuestionFactory();
        AnswerFactory commonAnswerFactory = new CommonAnswerFactory();
        QuestionReadDataAccessInterface questionReadDataAccess =
                new DatabaseQuestionReadDataAccessObjectFactory().create(commonQuestionFactory,
                        commonAnswerFactory);
        ViewProductInputBoundary viewProductInteractor =
                new ViewProductInteractor(viewProductPresenter, questionReadDataAccess);
        return new ViewProductController(viewProductInteractor);
    }

    private static ViewSignupPageController creatViewSignupPageController(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel){
        ViewSignupPageOutputBoundary viewSignupPagePresenter =
                new ViewSignupPagePresenter(viewManagerModel, signupViewModel);
        ViewSignupPageInputBoundary viewSignupPageInteractor =
                new ViewSignupPageInteractor(viewSignupPagePresenter);
        return new ViewSignupPageController(viewSignupPageInteractor);
    }

    private static DeleteShoppingCartProductController createDeleteShoppingCartProductController(
            ShoppingCartViewModel shoppingCartViewModel, ViewManagerModel viewManagerModel) throws SQLException {
        ShoppingCartFactory commonShoppingCartFactory = new CommonShoppingCartFactory();
        DeleteShoppingCartProductOutputBoundary deleteShoppingCartProductPresenter =
                new DeleteShoppingCartProductPresenter(shoppingCartViewModel, viewManagerModel);
        ProductFactory commonProductFactory = new CommonProductFactory();
        DatabaseShoppingCartReadDataAccessObjectFactoryInterface databaseShoppingCartReadDataAccessObjectFactory
                = new DatabaseShoppingCartReadDataAccessObjectFactory();
        ScheduleFactory scheduleFactory = new CommonScheduleFactory();
        ShoppingCartReadDataAccessInterface shoppingCartReadDataAccessObject =
                databaseShoppingCartReadDataAccessObjectFactory.create(commonShoppingCartFactory,
                        commonProductFactory, scheduleFactory);
        DatabaseShoppingCartUpdateDeleteDataAccessObjectFactoryInterface databaseShoppingCartUpdateDeleteDataAccessObjectFactory
                = new DatabaseShoppingCartUpdateDeleteDataAccessObjectFactory();
        ShoppingCartUpdateDeleteDataAccessInterface shoppingCartUpdateDeleteDataAccessObject =
                databaseShoppingCartUpdateDeleteDataAccessObjectFactory.create();
        DeleteShoppingCartProductInputBoundary deleteShoppingCartProductInteractor =
                new DeleteShoppingCartProductInteractor(shoppingCartUpdateDeleteDataAccessObject,
                        shoppingCartReadDataAccessObject, deleteShoppingCartProductPresenter);
        return new DeleteShoppingCartProductController(deleteShoppingCartProductInteractor);
    }

    private static PurchaseController createPurchaseController(ShoppingCartViewModel shoppingCartViewModel,
                                                               ViewManagerModel viewManagerModel) throws SQLException {

        DatabaseProductUpdateStateDataAccessObjectFactoryInterface databaseProductUpdateStateDataAccessObjectFactory =
                new DatabaseProductUpdateStateDataAccessObjectFactory();

        ProductUpdateStateDataAccessInterface productUpdateStateDataAccessObject =
                databaseProductUpdateStateDataAccessObjectFactory.create();

        DataBaseProductReadByIdDataAccessObjectFactoryInterface databaseProductReadByIdDataAccessObjectFactory =
                new DataBaseProductReadByIdDataAccessObjectFactory();

        ProductFactory commonProductFactory = new CommonProductFactory();
        ScheduleFactory scheduleFactory = new CommonScheduleFactory();
        ProductReadByIdDataAccessInterface productReadByIdDataAccessObject =
                databaseProductReadByIdDataAccessObjectFactory.create(commonProductFactory, scheduleFactory);

        PurchaseOutputBoundary purchaseOutputBoundary = new PurchasePresenter(shoppingCartViewModel, viewManagerModel);

        PurchaseInputBoundary purchaseInteractor = new PurchaseInteractor(productUpdateStateDataAccessObject,
                productReadByIdDataAccessObject,
                purchaseOutputBoundary);
        return new PurchaseController(purchaseInteractor);
    }

    private static MainPageController createMainPageController(MainPageViewModel mainPageViewModel, ViewManagerModel viewManagerModel) throws SQLException {
        ShowMainPageOutputBoundary showMainPagePresenter = new MainPagePresenter(mainPageViewModel, viewManagerModel);
        DataBaseProductReadAllDataAccessObjectFactoryInterface dataBaseProductReadAllDataAccessObjectFactoryInterface = new DatabaseProductReadAllDataAccessObjectFactory();
        ProductFactory productFactory = new CommonProductFactory();
        ScheduleFactory scheduleFactory = new CommonScheduleFactory();
        ProductReadAllDataAccessInterface productReadAllDataAccessObeject =
                dataBaseProductReadAllDataAccessObjectFactoryInterface.create(productFactory, scheduleFactory);
        ShowMainPageInputBoundary showMainPageInteractor =
                new ShowMainPageInteractor(showMainPagePresenter, productReadAllDataAccessObeject);
        return new MainPageController(showMainPageInteractor);
    }

    public static GetBuyerSchedulePageController createBuyerSelectScheduleController(){
        //TODO  need to implement this method
        return new BuyerSelectScheduleController();
    }

    public static ConfirmController createConfirmController(){
        //TODO need to implement this method
        return new ConfirmController();
    }

    public static GetRatePageController createRateProductController(){
        //TODO need to implement this method
        return new GetRatePageController();
    }

    private static LogOutController createLogOutController(ViewManagerModel viewManagerModel,
                                                           MainPageViewModel mainPageViewModel) throws SQLException {
        LogOutOutputBoundary LogOutPresenter = new LogOutPresenter(mainPageViewModel,
                viewManagerModel);
        LogOutInputBoundary logOutInteractor = new LogOutInteractor(LogOutPresenter);
        return new LogOutController(logOutInteractor);
    }

    private static ViewProfileController createProfileController(ViewManagerModel viewManagerModel,
                                                                 ViewProfileViewModel profileViewModel) throws IOException {
        ViewProfileOutputBoundary viewProfilePresenter = new ViewProfilePresenter(profileViewModel,
                viewManagerModel);
        ViewProfileInputBoundary viewProfileInteractor = new ViewProfileInteractor(viewProfilePresenter);
        return new ViewProfileController(viewProfileInteractor);
    }

    private static GetSearchPageController createGetSearchPageController(ViewManagerModel viewManagerModel, SearchProductViewModel searchProductViewModel) throws SQLException {
        GetSearchViewOutputBoundary getSearchViewPresenter =
                new GetSearchPagePresenter(searchProductViewModel, viewManagerModel);
        DataBaseProductReadAllDataAccessObjectFactoryInterface dataBaseProductReadAllDataAccessObjectFactoryInterface = new DatabaseProductReadAllDataAccessObjectFactory();
        ProductFactory productFactory = new CommonProductFactory();
        ScheduleFactory scheduleFactory = new CommonScheduleFactory();
        ProductReadAllDataAccessInterface productReadAllDataAccessObeject =
                dataBaseProductReadAllDataAccessObjectFactoryInterface.create(productFactory, scheduleFactory);
        GetSearchViewInputBoundary getSearchViewInteractor =
                new GetSearchViewInteractor(getSearchViewPresenter, productReadAllDataAccessObeject);
        return new GetSearchPageController(getSearchViewInteractor);
    }

    private static ViewLoginPageController createViewLoginPageController(LoginViewModel loginViewModel,
                                                                         ViewManagerModel viewManagerModel){

        ViewLoginPageOutputBoundary viewLoginPagePresenter =
                new ViewLoginPagePresenter(loginViewModel, viewManagerModel);
        ViewLoginPageInputBoundary viewLoginPageInteractor =
                new ViewLoginPageInteractor(viewLoginPagePresenter);
        return new ViewLoginPageController(viewLoginPageInteractor);
    }


}
