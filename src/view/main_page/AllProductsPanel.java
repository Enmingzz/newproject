package view.main_page;

import entity.product.Product;
import entity.user.User;
import interface_adapter.main_page.MainPageViewModel;
import interface_adapter.view_product.ViewProductController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllProductsPanel extends JPanel {
    AllProductsPanel(ArrayList<Product> allProducts,
                     MainPageViewModel mainPageViewModel,
                     ViewProductController viewProductController) {

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setAlignmentY(Component.TOP_ALIGNMENT);

        int _i = 0;
        List<JPanel> listProductPanels = new ArrayList<>();

        for (Product product : allProducts) {
            if (product != null) {
                JLabel paneledImage = new JLabel();
                paneledImage.setIcon(new ImageIcon(product.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                JLabel productTitle = new JLabel(product.getTitle());

                JLabel productPrice = new JLabel(String.valueOf(product.getPrice()));

                JButton viewButton = new JButton(product.getTitle());
                // dimension set as this for now but will likely get changed later
                viewButton.setPreferredSize(new Dimension(100, 50));
                viewButton.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent event) {
                                if (event.getSource().equals(viewButton)) {
                                    User user = mainPageViewModel.getState().getUser();
                                    try {
                                        viewProductController.execute(product, user);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e); //Revisit this in case of bug in viewing a product
                                    }

                                }
                            }
                        }
                );

                ProductPanel productPanel = new ProductPanel(
                        paneledImage, productTitle, productPrice, viewButton
                );

                // Above created one panel for image

                if (_i % 3 == 0) {
                    listProductPanels = new ArrayList<>();

                }
                listProductPanels.add(productPanel);

                if (_i % 3 == 2) {
                    HorizontalLayoutPanel horizontalLayoutPanel = new HorizontalLayoutPanel(
                            listProductPanels
                    );
                    this.add(horizontalLayoutPanel);
                    horizontalLayoutPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                } else if (_i + 1 == allProducts.size()) {
                    HorizontalLayoutPanel horizontalLayoutPanel = new HorizontalLayoutPanel(
                            listProductPanels
                    );
                    this.add(horizontalLayoutPanel);
                    horizontalLayoutPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                }

                _i++;

            }

        }

    }

}
