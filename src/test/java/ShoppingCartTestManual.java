import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.dao.InMemorySalesSystemDAO;
import ee.ut.math.tvt.salessystem.dataobjects.SoldItem;
import ee.ut.math.tvt.salessystem.dataobjects.StockItem;
import ee.ut.math.tvt.salessystem.logic.ShoppingCart;

public class ShoppingCartTestManual {

    private ShoppingCart cart;
    private InMemorySalesSystemDAO dao;

    @Before
    public void setUp() {
        dao = new InMemorySalesSystemDAO();
        cart = new ShoppingCart(dao);
    }

    @Test
    public void testAddItem() {
        StockItem stockItem = new StockItem(5L, "Cake", "Blueberry cake", 2.5, 5);
        SoldItem soldItem = new SoldItem(stockItem, 3);

        cart.addItem(soldItem);

        assertEquals(1, cart.getAll().size());
        assertEquals(3, cart.getAll().get(0).getQuantity().intValue());
    }

    @Test
    public void testCancelPurchase() {
        StockItem stockItem1 = new StockItem(6L, "Coffee", "Mocha", 5.0, 20);
        StockItem stockItem2 = new StockItem(7L, "Tea", "Green tea", 3.0, 15);
        SoldItem soldItem1 = new SoldItem(stockItem1, 2);
        SoldItem soldItem2 = new SoldItem(stockItem2, 1);

        cart.addItem(soldItem1);
        cart.addItem(soldItem2);

        assertEquals(2, cart.getAll().size());

        cart.cancelCurrentPurchase();

        assertTrue(cart.getAll().isEmpty());
    }

    @Test
    public void testSubmitPurchase() {
        StockItem stockItem = new StockItem(8L, "Milk", "Almond milk", 2.49, 50);
        SoldItem soldItem = new SoldItem(stockItem, 5);

        cart.addItem(soldItem);
        cart.submitCurrentPurchase();

        assertTrue(cart.getAll().isEmpty());
        assertEquals(1, dao.findStockItem(8L).getQuantity());
    }
}
