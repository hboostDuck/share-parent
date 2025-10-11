package com.share.payment.service;

import com.share.payment.domain.CreateWxPaymentForm;
import com.share.payment.domain.WxPrepayVo;
import jakarta.servlet.http.HttpServletRequest;
import com.wechat.pay.java.service.payments.model.Transaction;

public interface IWxPayService {

	WxPrepayVo createWxPayment(CreateWxPaymentForm createWxPaymentForm);

	void wxnotify(HttpServletRequest request);

	Transaction queryPayStatus(String orderNo);
}
