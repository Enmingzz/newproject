package interface_adapter.rating;

import interface_adapter.ViewManagerModel;
import use_case.rate_product.GetRatePageOutputBoundary;
import use_case.rate_product.GetRatePageOutputData;

public class GetRatePagePresenter implements GetRatePageOutputBoundary {
    private final RateProductViewModel rateProductViewModel;
    private final ViewManagerModel viewManagerModel;

    public GetRatePagePresenter(RateProductViewModel rateProductViewModel, ViewManagerModel viewManagerModel) {
        this.rateProductViewModel = rateProductViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessfulView(GetRatePageOutputData getRatePageOutputData){
        RateProductState state = rateProductViewModel.getState();
        rateProductViewModel.setState(state);
        rateProductViewModel.firePropertyChanged();
        viewManagerModel.setActiveView(rateProductViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
