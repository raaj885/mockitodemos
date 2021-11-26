/**
 * 
 */
package com.mobileapp.testcases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.mobileapp.exceptions.MobileNotFoundException;
import com.mobileapp.model.Mobile;
import com.mobileapp.service.IMobileService;
import com.mobileapp.service.OrderDetails;

/**
 * @author RajasekharMandireddy
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class OrderDetailsTest {
	@Mock
	IMobileService mobileService;
	
	@InjectMocks
	OrderDetails orderdetails;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	Mobile mobile1,mobile2,mobile3,mobile4,mobile5,mobile6;

	private Mobile expectedMobile;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mobile1= new Mobile ("A123", "samsung", 1, 20000);
		 mobile2= new Mobile ("Z12", "vivo", 2, 60000);
		 mobile3= new Mobile ("13ProMAx", "IPhone", 3, 80000);
		 mobile4= new Mobile("Xperia13", "sony", 4, 75000);
		 mobile5= new Mobile ("X123", "vivo", 5, 50000);
		 mobile6= new Mobile ("78g", "moto", 6, 19000);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("show mobile positive")
	void testshowMobiles() throws MobileNotFoundException {
		String brand = "vivo";
		List<Mobile>expectedMobileList= Arrays.asList(mobile5,mobile2);
		when(mobileService.findMobileByBrand("vivo")).thenReturn(Arrays.asList(mobile5,mobile2));
		List<Mobile>actualMobileList= orderdetails.showMobiles(brand);
		assertEquals(expectedMobileList,actualMobileList,"List is not equal");
	}
	@Test
	@DisplayName("show mobile negative")
	void testshowMobilesInvalid() throws MobileNotFoundException {
		when (mobileService.findMobileByBrand("moto")).thenThrow(MobileNotFoundException.class);
		assertThrows(MobileNotFoundException.class,()->orderdetails.showMobiles("moto"));
	}
	
	@Test
	@DisplayName("MobileList null")
	void testshowMobilesNull() throws MobileNotFoundException {
		String brand = "vivo";
		
		when(mobileService.findMobileByBrand(brand)).thenReturn(new ArrayList<>());
		
		List<Mobile>actualMobileList= orderdetails.showMobiles(brand);
		assertEquals(0,actualMobileList.size());
	}
	
	@Test
	@DisplayName("Sorting mobileList")
	void testshowMobileSort()throws MobileNotFoundException{
		String brand = "vivo";
		List<Mobile>expectedMobileList= Arrays.asList(mobile5,mobile2);
		when(mobileService.findMobileByBrand("vivo")).thenReturn(Arrays.asList(mobile2,mobile5));
		List<Mobile>actualMobileList= orderdetails.showMobiles(brand);
		assertEquals(expectedMobileList,actualMobileList,"List is not equal");
	}
	
	@Test
	@DisplayName("Order positive")
	void testOrderMobileOne() throws MobileNotFoundException {
		String expected= "mobile ordered";
		when(mobileService.findMobileById(1)).thenReturn(mobile1);
		String actual= orderdetails.orderMobile(1);
		assertEquals(expected,actual,"not same");
		
	}
	
	@Test
	@DisplayName("Order negative")
	void testOrderMobileInvalid() throws MobileNotFoundException{
		String expected= "mobile not ordered";
		when (mobileService.findMobileById(100)).thenReturn(null);
		String actual = orderdetails.orderMobile(100);
		assertEquals(expected, actual,"values are diff");
	}
	@Test
	@DisplayName("Order Exception")
	void testOrderMobileException()throws MobileNotFoundException{
		String expected = "mobile not ordered";
		when(mobileService.findMobileById(100)).thenThrow(MobileNotFoundException.class);
		String actual = orderdetails.orderMobile(100);
		assertEquals(expected,actual,"values are not same");
		
	}
	@Test
	@DisplayName("Checking with empty object")
	void testOrderEmpty()throws MobileNotFoundException{
		String expected = "mobile not ordered";
		when(mobileService.findMobileById(100)).thenReturn(new Mobile());
		String actual = orderdetails.orderMobile(100);
		assertEquals(expected,actual,"Object is not empty");
		
	}
	
	

}
