package app.UserUseCaseFactory;

import data_access.factories.interfaces.Product.DataBaseProductReadAllDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.Product.DatabaseProductReadByNameDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.ShoppingCart.DatabaseShoppingCartReadDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.User.DatabaseUserReadDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.User.DatabaseUserUpdateNameDataAccessObjectFactoryInterface;
import data_access.factories.interfaces.User.DatabaseUserUpdatePasswordDataAccessObjectFactoryInterface;
import data_access.factories.objects.Product.DatabaseProductReadAllDataAccessObjectFactory;
import data_access.factories.objects.Product.DatabaseProductReadByNameDataAccessObjectFactory;
import data_access.factories.objects.ShoppingCart.DatabaseShoppingCartReadDataAccessObjectFactory;
import data_access.factories.objects.User.DatabaseUserReadDataAccessObjectFactory;
import data_access.factories.objects.User.DatabaseUserUpdateNameDataAccessObjectFactory;
import data_access.factories.objects.User.DatabaseUserUpdatePasswordDataAccessObjectFactory;
import data_access.interfaces.Prouct.ProductReadAllDataAccessInterface;
import data_access.interfaces.Prouct.ProductReadByNameDataAccessInterface;
import data_access.interfaces.ShoppingCart.ShoppingCartReadDataAccessInterface;
import data_access.interfaces.User.UserReadDataAccessInterface;
import data_access.interfaces.User.UserUpdateNameDataAccessInterface;
import data_access.interfaces.User.UserUpdatePasswordDataAccessInterface;
import entity.product.CommonProductFactory;
import entity.product.ProductFactory;
import entity.schedule.CommonScheduleFactory;
import entity.schedule.ScheduleFactory;
import entity.shopping_cart.CommonShoppingCartFactory;
import entity.shopping_cart.ShoppingCartFactory;
import entity.user.CommonUserFactory;
import entity.user.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.logout.LogOutController;
import interface_adapter.logout.LogOutPresenter;
import interface_adapter.main_page.MainPageController;
import interface_adapter.main_page.MainPagePresenter;
import interface_adapter.main_page.MainPageViewModel;
import interface_adapter.profile.ModifyProfile.ModifyProfileController;
import interface_adapter.profile.ModifyProfile.ModifyProfilePresenter;
import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfilePresenter;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.search_product.SearchProductByNameController;
import interface_adapter.search_product.SearchProductByNamePresenter;
import interface_adapter.search_product.SearchProductByNameViewModel;
import interface_adapter.shopping_cart.ShoppingCartController;
import interface_adapter.shopping_cart.ShoppingCartPresenter;
import interface_adapter.shopping_cart.ShoppingCartViewModel;
import use_case.logout.LogOutInputBoundary;
import use_case.logout.LogOutInteractor;
import use_case.logout.LogOutOutputBoundary;
import use_case.main_page.ShowMainPageInputBoundary;
import use_case.main_page.ShowMainPageInteractor;
import use_case.main_page.ShowMainPageOutputBoundary;
import use_case.product_search.SearchProductByNameInputBoundary;
import use_case.product_search.SearchProductByNameInteractor;
import use_case.product_search.SearchProductByNameOutputBoundary;
import use_case.profile.ModifyProfile.ModifyProfileInputBoundary;
import use_case.profile.ModifyProfile.ModifyProfileInteractor;
import use_case.profile.ModifyProfile.ModifyProfileOutputBoundary;
import use_case.profile.ViewProfileInputBoundary;
import use_case.profile.ViewProfileInteractor;
import use_case.profile.ViewProfileOutputBoundary;
import use_case.shopping_cart.ShowShoppingCartInputBoundary;
import use_case.shopping_cart.ShowShoppingCartInteractor;
import view.profile.ModifyProfileView;

import java.sql.SQLException;

public class ModifyProfileUseCaseFactory {

    public static ModifyProfileView create(ViewManagerModel viewManagerModel,
                                           MainPageViewModel mainPageViewModel,
                                           ShoppingCartViewModel shoppingCartViewModel,
                                           SearchProductByNameViewModel searchProductByNameViewModel) throws SQLException {
        MainPageController mainPageController =
                ModifyProfileUseCaseFactory.createMainPageController(mainPageViewModel,
                        viewManagerModel);
        ModifyProfileController modifyProfileController =
                ModifyProfileUseCaseFactory.createModifyProfileController();
        ShoppingCartController shoppingCartController =
                ModifyProfileUseCaseFactory.createShoppingCartController(shoppingCartViewModel);
        SearchProductByNameController searchProductByNameVController =
                ModifyProfileUseCaseFactory.createSearchProductByNameController(viewManagerModel,
                        searchProductByNameViewModel);
        return new ModifyProfileView(modifyProfileController, mainPageController,
                        shoppingCartController, searchProductByNameVController);
    }

    private static ShoppingCartController createShoppingCartController(ShoppingCartViewModel shoppingCartViewModel) throws SQLException {
        ShoppingCartFactory shoppingCartFactory = new CommonShoppingCartFactory();
        ProductFactory productFactory = new CommonProductFactory();
        ShoppingCartPresenter presenter = new ShoppingCartPresenter(shoppingCartViewModel);
        DatabaseShoppingCartReadDataAccessObjectFactoryInterface databaseShoppingCartReadDataAccessObjectFactory
                = new DatabaseShoppingCartReadDataAccessObjectFactory();
        ShoppingCartReadDataAccessInterface shoppingCartReadDataAccess =
                databaseShoppingCartReadDataAccessObjectFactory.create(shoppingCartFactory,
                        productFactory);
        ShowShoppingCartInputBoundary showShoppingCartInteractor =
                new ShowShoppingCartInteractor(presenter, shoppingCartReadDataAccess);
        return new ShoppingCartController(showShoppingCartInteractor);
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

    private static LogOutController createLogOutController(ViewManagerModel viewManagerModel,
                                                           MainPageViewModel mainPageViewModel) throws SQLException {
        LogOutOutputBoundary LogOutPresenter = new LogOutPresenter(mainPageViewModel,
                viewManagerModel);
        LogOutInputBoundary logOutInteractor = new LogOutInteractor(LogOutPresenter);
        return new LogOutController(logOutInteractor);
    }

    private static ProfileController createProfileController(ViewManagerModel viewManagerModel,
                                                             ProfileViewModel profileViewModel) {
        ViewProfileOutputBoundary viewProfilePresenter = new ProfilePresenter(profileViewModel,
                viewManagerModel);
        ViewProfileInputBoundary viewProfileInteractor = new ViewProfileInteractor(viewProfilePresenter);
        return new ProfileController(viewProfileInteractor);
    }

    private static SearchProductByNameController createSearchProductByNameController(ViewManagerModel viewManagerModel, SearchProductByNameViewModel searchProductByNameViewModel) throws SQLException {
        SearchProductByNameOutputBoundary searchProductByNamePresenter =
                new SearchProductByNamePresenter(viewManagerModel, searchProductByNameViewModel);
        DatabaseProductReadByNameDataAccessObjectFactoryInterface databaseProductReadByNameDataAccessObjectFactory
                = new DatabaseProductReadByNameDataAccessObjectFactory();
        ProductFactory productFactory = new CommonProductFactory();
        ScheduleFactory scheduleFactory = new CommonScheduleFactory();
        ProductReadByNameDataAccessInterface productReadByNameDataAccessObject =
                databaseProductReadByNameDataAccessObjectFactory.create(productFactory, scheduleFactory);
        SearchProductByNameInputBoundary searchProductByNameInteractor =
                new SearchProductByNameInteractor(productReadByNameDataAccessObject,
                        searchProductByNamePresenter);
        return new SearchProductByNameController(searchProductByNameInteractor);
    }

    private static ModifyProfileController createModifyProfileController() throws SQLException {
        DatabaseUserUpdateNameDataAccessObjectFactoryInterface databaseUserUpdateNameDataAccessObjectFactoryInterface
                = new DatabaseUserUpdateNameDataAccessObjectFactory();
        UserUpdateNameDataAccessInterface userUpdateNameDataAccessObject =
                databaseUserUpdateNameDataAccessObjectFactoryInterface.create();
        DatabaseUserUpdatePasswordDataAccessObjectFactoryInterface databaseUserUpdatePasswordDataAccessObjectFactoryInterface = new
                DatabaseUserUpdatePasswordDataAccessObjectFactory();
        UserFactory userFactory = new CommonUserFactory();
        DatabaseUserReadDataAccessObjectFactoryInterface userReadDataAccessObjectFactoryInterface = new DatabaseUserReadDataAccessObjectFactory();
        UserReadDataAccessInterface userReadDataAccessObject =
                userReadDataAccessObjectFactoryInterface.create(userFactory);
        UserUpdatePasswordDataAccessInterface userUpdatePasswordDataAccessObject =
                databaseUserUpdatePasswordDataAccessObjectFactoryInterface.create();
        ModifyProfileOutputBoundary modifyProfilePresenter = new ModifyProfilePresenter();
        ModifyProfileInputBoundary modifyProfileInteractor =
                new ModifyProfileInteractor(userUpdateNameDataAccessObject,
                                        userUpdatePasswordDataAccessObject, userReadDataAccessObject,
                                        modifyProfilePresenter);

        return new ModifyProfileController(modifyProfileInteractor);
    }

}
