package org.apache.diana.hazelcast.key;

import org.apache.diana.api.key.KeyValueEntityManagerFactory;
import org.apache.diana.hazelcast.key.model.ProductCart;
import org.apache.diana.hazelcast.key.util.KeyValueEntityManagerFactoryUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class ListTest {


    private static final String FRUITS = "fruits";
    private ProductCart banana = new ProductCart("banana", BigDecimal.ONE);
    private ProductCart orange = new ProductCart("orange", BigDecimal.ONE);
    private ProductCart waterMelon = new ProductCart("waterMelon", BigDecimal.TEN);
    private ProductCart melon = new ProductCart("melon", BigDecimal.ONE);

    private KeyValueEntityManagerFactory keyValueEntityManagerFactory;

    private List<ProductCart> fruits;

    @Before
    public void init() {
        keyValueEntityManagerFactory =  KeyValueEntityManagerFactoryUtils.get();
        fruits = keyValueEntityManagerFactory.getList(FRUITS, ProductCart.class);
    }

    @Test
    public void shouldReturnsList() {
        assertNotNull(fruits);
    }

    @Test
    public void shouldAddList() {
        assertTrue(fruits.isEmpty());
        fruits.add(banana);
        assertFalse(fruits.isEmpty());
        ProductCart banana = fruits.get(0);
        assertNotNull(banana);
        assertEquals(banana.getName(), "banana");
    }

    @Test
    public void shouldSetList() {

        fruits.add(banana);
        fruits.add(0, orange);
        assertTrue(fruits.size() == 2);

        assertEquals(fruits.get(0).getName(), "orange");
        assertEquals(fruits.get(1).getName(), "banana");

        fruits.set(0, waterMelon);
        assertEquals(fruits.get(0).getName(), "waterMelon");
        assertEquals(fruits.get(1).getName(), "banana");

    }

    @Test
    public void shouldRemoveList() {
        fruits.add(banana);
    }

    @Test
    public void shouldReturnIndexOf() {

        fruits.add(new ProductCart("orange", BigDecimal.ONE));
        fruits.add(banana);
        fruits.add(new ProductCart("watermellon", BigDecimal.ONE));
        fruits.add(banana);
        assertTrue(fruits.indexOf(banana) == 1);
        assertTrue(fruits.lastIndexOf(banana) == 3);

        assertTrue(fruits.contains(banana));
        assertTrue(fruits.indexOf(melon) == -1);
        assertTrue(fruits.lastIndexOf(melon) == -1);
    }

    @Test
    public void shouldReturnContains() {

        fruits.add(orange);
        fruits.add(banana);
        fruits.add(waterMelon);
        assertTrue(fruits.contains(banana));
        assertFalse(fruits.contains(melon));
        assertTrue(fruits.containsAll(Arrays.asList(banana, orange)));
        assertFalse(fruits.containsAll(Arrays.asList(banana, melon)));

    }

    @SuppressWarnings("unused")
    @Test
    public void shouldIterate() {
        fruits.add(melon);
        fruits.add(banana);
        int count = 0;
        for (ProductCart fruiCart: fruits) {
            count++;
        }
        assertTrue(count == 2);
        fruits.remove(0);
        fruits.remove(0);
        count = 0;
        for (ProductCart fruiCart: fruits) {
            count++;
        }
        assertTrue(count == 0);
    }
    @After
    public  void end() {
        fruits.clear();
    }
}