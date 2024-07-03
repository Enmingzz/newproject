package use_case.shopping_cart;

import entity.product.Product;
import entity.shopping_cart.ShoppingCart;
import entity.user.User;
import data_access.interfaces.shopping_cart.ShoppingCartUpdateAddDataAccessInterface;
import data_access.interfaces.shopping_cart.ShoppingCartReadDataAccessInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddShoppingCartProductInteractor implements AddShoppingCartProductInputBoundary{

    final ShoppingCartUpdateAddDataAccessInterface shoppingCartUpdateAddDataAccessObject;
    final AddShoppingCartProductOutputBoundary addShoppingCartProductPresenter;
    final ShoppingCartReadDataAccessInterface shoppingCartReadDataAccessObject;

    public AddShoppingCartProductInteractor(ShoppingCartUpdateAddDataAccessInterface shoppingCartUpdateAddDataAccessObject,
                                            AddShoppingCartProductOutputBoundary addShoppingCartProductPresenter,
                                            ShoppingCartReadDataAccessInterface shoppingCartReadDataAccessObject) {

        this.shoppingCartUpdateAddDataAccessObject = shoppingCartUpdateAddDataAccessObject;
        this.addShoppingCartProductPresenter = addShoppingCartProductPresenter;
        this.shoppingCartReadDataAccessObject = shoppingCartReadDataAccessObject;
    }

    @Override
    public void addProductToShoppingCart(AddShoppingCartProductInputData addShoppingCartProductInputData) throws SQLException, IOException {
        User user = addShoppingCartProductInputData.getUser();
        Product addProduct = addShoppingCartProductInputData.getProduct();
        ShoppingCart shoppingCart = shoppingCartReadDataAccessObject.getShoppingCart(user.getStudentNumber());
        List<Product> listProducts = shoppingCart.getListProducts();

        shoppingCartUpdateAddDataAccessObject.updateShoppingCart(user, addProduct);
        float totalPrice = shoppingCart.getTotalPrice() + addProduct.getPrice();
        AddShoppingCartProductOutputData addShoppingCartProductOutputData = new AddShoppingCartProductOutputData(user, listProducts, totalPrice);
        addShoppingCartProductPresenter.prepareSuccessView(addShoppingCartProductOutputData);

    }


}
