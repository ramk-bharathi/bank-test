package com.bank.test.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bank.test.beans.BankDetailsBean;
import com.bank.test.beans.ErrorBean;
import com.bank.test.managers.BankDetailsManager;

@RestController
@RequestMapping("/banks")
public class BankDetailsController {
	@RequestMapping(value = "/getVersion", method = RequestMethod.GET)
	public Map<String, String> getVersion() {
		Map<String, String> versionMap = new HashMap<String, String>();
		versionMap.put("version", "1.0");
		return versionMap;
	}

	@RequestMapping(value = "/search/ifsc/{ifscValue}", method = RequestMethod.GET)
	public ResponseEntity<?> searchBankByIfsc(@PathVariable("ifscValue") String ifsc) {
		BankDetailsManager manager = new BankDetailsManager();
		BankDetailsBean detailsBean = manager.getBankDetailsByIfsc(ifsc);
		if (detailsBean != null) {
			return new ResponseEntity<BankDetailsBean>(detailsBean, HttpStatus.OK);
		}
		ErrorBean errorBean = new ErrorBean();
		errorBean.setErrorCode(Integer.parseInt(HttpStatus.NOT_FOUND.toString()));
		errorBean.setMessage("Not Found");
		return new ResponseEntity<ErrorBean>(errorBean, HttpStatus.NOT_ACCEPTABLE);
	}

	@RequestMapping(value = "/search/bnc/{bankName}/{bankCity}", method = RequestMethod.GET)
	public ResponseEntity<?> searchBankByNameCity(@PathVariable("bankName") String bankName,
			@PathVariable("bankCity") String bankCity) {
		BankDetailsManager manager = new BankDetailsManager();
		List<BankDetailsBean> detailsBeanList = manager.getBankDetailsByNameCity(bankName, bankCity);
		if (detailsBeanList.size() > 0 || detailsBeanList != null) {
			return new ResponseEntity<List<BankDetailsBean>>(detailsBeanList, HttpStatus.OK);
		}
		ErrorBean errorBean = new ErrorBean();
		errorBean.setErrorCode(Integer.parseInt(HttpStatus.NOT_FOUND.toString()));
		errorBean.setMessage("Not Found");
		return new ResponseEntity<ErrorBean>(errorBean, HttpStatus.NOT_ACCEPTABLE);
	}
}
