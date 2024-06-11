package com.techartistry.paymentsservice.payments.application.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {
    @Value("${stripe-apikey}")
    private String apiKey;

    /**
     * Inicializa la API de Stripe
     */
    private void init() {
        Stripe.apiKey = apiKey;
    }

    public void processPayment(Long bookingId, double amount) throws StripeException {
        //se crea un cargo con los detalles de la compra
        ChargeCreateParams params3 = ChargeCreateParams.builder()
            .setAmount((long) (amount * 100))
            .setCurrency("usd")
            .setSource("tok_visa")
            .setDescription("Payment for booking ID: " + bookingId)
            .build();

        Charge charge = Charge.create(params3);

        var session = Session.create(params3.toMap());
        var url = session.getUrl();



//        //se crea una sesión de pago con Stripe Checkout agregando los detalles de la compra
//        SessionCreateParams params = SessionCreateParams.builder()
//            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
//            .setMode(SessionCreateParams.Mode.PAYMENT)
//            .setSuccessUrl("http://localhost:8080/success")
//            .setCancelUrl("http://localhost:8080/cancel")
//            .addLineItem(
//                SessionCreateParams.LineItem.builder()
//                    .setQuantity(1L)
//                    .setPriceData(
//                        SessionCreateParams.LineItem.PriceData.builder()
//                            .setCurrency("usd")
//                            .setUnitAmount((long) (amount * 100))
//                            .setProductData(
//                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                    .setName("Booking ID: " + bookingId)
//                                    .build()
//                            )
//                            .build()
//                    )
//                    .build()
//            )
//            .build();
    }
}