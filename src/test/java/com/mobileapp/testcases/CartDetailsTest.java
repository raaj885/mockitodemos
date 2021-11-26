package com.mobileapp.testcases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mobileapp.exceptions.EmptyCartException;
import com.mobileapp.exceptions.MobileNotFoundException;
import com.mobileapp.model.Mobile;
import com.mobileapp.service.CartDetails;
import com.mobileapp.service.ICartService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CartDetailsTest {

	@Mock
	ICartService CartService;

	@InjectMocks
	CartDetails cartdetails;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	Mobile mobile1, mobile2, mobile3, mobile4, mobile5, mobile6;

	@BeforeEach
	void setUp() throws Exception {
		mobile1 = new Mobile("A123", "samsung", 1, 20000);
		mobile2 = new Mobile("Z12", "vivo", 2, 60000);
		mobile3 = new Mobile("ProMAx", "iPhone", 3, 80000);
		mobile4 = new Mobile("Xperia13", "sony", 4, 75000);
		mobile5 = new Mobile("X123", "vivo", 5, 50000);
		mobile6 = new Mobile("G78", "moto", 6, 19000);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("AddCart positive")
	void testAddCart() throws MobileNotFoundException {
		doNothing().when(CartService).addtoCart(mobile1);
		String actual = cartdetails.addtoCart(mobile1);
		String expected = "added successfully";
		assertEquals(expected, actual, "invalid");
	}

	@Test
	@DisplayName("AddCart Negative")
	void testAddCartException() throws MobileNotFoundException {
		doThrow(new MobileNotFoundException("invalid")).when(CartService).addtoCart(mobile1);
		assertThrows(MobileNotFoundException.class, () -> cartdetails.addtoCart(mobile1));
	}

	@Test
	@DisplayName("AddCart positive")
	void testShowCart() throws MobileNotFoundException, EmptyCartException {
		List<Mobile> expected = Arrays.asList(mobile1, mobile3, mobile2);
		doReturn(Arrays.asList(mobile3, mobile2, mobile1)).when(CartService).showCart();
		List<Mobile> actual = cartdetails.showCart();
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("AddCart Exception")
	void testshowCartEmpty() throws EmptyCartException {
		doThrow(new EmptyCartException()).when(CartService).showCart();
		assertThrows(EmptyCartException.class, () -> cartdetails.showCart());
	}

	@Test
	@DisplayName("AddCart null")
	void testShowCartNull() throws MobileNotFoundException, EmptyCartException {
		doReturn(null).when(CartService).showCart();
		assertNull(cartdetails.showCart());

	}

	@Test
	@DisplayName("remove cart positive")
	void testRemoveCart() throws EmptyCartException, MobileNotFoundException {
		doReturn(true).when(CartService).removeFromCart(mobile1);
		assertEquals(true, cartdetails.removeFromCart(mobile1));
	}

	@Test
	@DisplayName("remove cart empty")
	void testRemoveCartEmpty() throws MobileNotFoundException {
		doThrow(new MobileNotFoundException()).when(CartService).removeFromCart(mobile3);
		assertThrows(MobileNotFoundException.class, () -> cartdetails.removeFromCart(mobile3));
	}

}
