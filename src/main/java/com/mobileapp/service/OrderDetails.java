package com.mobileapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.mobileapp.exceptions.MobileNotFoundException;
import com.mobileapp.model.Mobile;

public class OrderDetails {

	public IMobileService getMobileService() {
		return mobileService;
	}

	public void setMobileService(IMobileService mobileService) {
		this.mobileService = mobileService;
	}

	public Mobile getMobile() {
		return mobile;
	}

	public void setMobile(Mobile mobile) {
		this.mobile = mobile;
	}

	IMobileService mobileService;
	Mobile mobile = null;
	List<Mobile> mobileList;

	public String orderMobile(int mobileId) {
		try {
			mobile = mobileService.findMobileById(mobileId);
		} catch (MobileNotFoundException e) {

			System.out.println(e.getMessage());
		}
		
		  if (mobile== null || mobile.getBrand() == null && mobile.getMobileId()==null){
			  
			  return "mobile not ordered"; 
			  }
		  else
		 
			return "mobile ordered";
	}

	public List<Mobile> showMobiles(String brand) throws MobileNotFoundException {
		try {
			mobileList = mobileService.findMobileByBrand(brand);// null
		} catch (MobileNotFoundException e) {

			System.out.println(e.getMessage());
			throw e;
		}
		if (mobileList != null) {
			return mobileList.stream().sorted(Comparator.comparing(Mobile::getModel)).collect(Collectors.toList());
		} else
			return mobileList;
	}
}