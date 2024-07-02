package use_case.product_search;

import data_access.interfaces.product.ProductReadByNameDataAccessInterface;

public class SearchProductByNameInteractor implements SearchProductByNameInputBoundary{

    final private ProductReadByNameDataAccessInterface databaseProductReadByNameDataAccessObject;
    final private SearchProductByNameOutputBoundary searchProductByNamePresenter;

    public SearchProductByNameInteractor(ProductReadByNameDataAccessInterface databaseProductReadByNameDataAccessObject, SearchProductByNameOutputBoundary searchProductByNamePresenter) {
        this.databaseProductReadByNameDataAccessObject = databaseProductReadByNameDataAccessObject;
        this.searchProductByNamePresenter = searchProductByNamePresenter;
    }

    @Override
    public void execute(SearchProductByNameInputData inputData) {
        //TODO need to implement this method
    }
}
