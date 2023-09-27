package com.shopeasy.estore.service;

import com.shopeasy.estore.dto.StripeChargeDto;
import com.shopeasy.estore.dto.StripeTokenDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j //log our error msg when generating token from stripe
public class StripeService {

    private String stripeApiPublishedKey = "pk_test_51NsHEPFOBWvODlc06C2xu7BTUl5iwBmhrRDlOeGX9O8OTJs9PUNSgke7IWjaxNYrcShhsOrLAQy5QLSRnAMeBC7c00wqLdAJg0";
    private String stripeApiSecretKey = "sk_test_51NsHEPFOBWvODlc0gGxcQqoOtMvKN1jTP3Tvom31caDQTsTk2CadyUER3FrTaa2XQ6HaLGdDMy5jyLiO90whRSI400U2kaYUTM";
    //initializing api key
    @PostConstruct //for init method to be initialized when StripeService
                    // object is created by Spring we need to use postConstruct
    public void init(){
        //Stripe.apiKey = stripeApiPublishedKey;
        Stripe.apiKey = stripeApiSecretKey;
    }

    public StripeTokenDto createCardToken(StripeTokenDto model){
        try{
            Map<String, Object> card = new HashMap<>();
            card.put("number", model.getCardNumber());
            card.put("exp_month", Integer.parseInt(model.getExpMonth()));
            card.put("exp_year", Integer.parseInt(model.getExpYear()));
            card.put("cvc", model.getCvc());
            Map<String, Object> params = new HashMap<>();
            params.put("card", card);
            Token token = Token.create(params);
            if (token != null && token.getId() != null){
                model.setSuccess(true);
                model.setToken(token.getId());
            }return model;
        } catch (StripeException e) {

            log.error("StripeService (createCardToken)", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    //logic to charge card.
    public StripeChargeDto charge(StripeChargeDto chargeRequest){
        try {
            chargeRequest.setSuccess(false);
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", (int) (chargeRequest.getAmount() * 100));
            chargeParams.put("currency", "CAD");
            chargeParams.put("description", "Payment for id" + chargeRequest.getAdditionalInfo().getOrDefault("ID_TAG", ""));
            chargeParams.put("source", chargeRequest.getStripeToken());
            Map<String, Object> metaData = new HashMap<>();
            metaData.put("id", chargeRequest.getChargeId());
            metaData.putAll(chargeRequest.getAdditionalInfo());
            chargeParams.put("metadata", metaData);
            Charge charge = Charge.create(chargeParams);
            chargeRequest.setMessage(charge.getOutcome().getSellerMessage());

            if (charge.getPaid()) {
                chargeRequest.setChargeId(charge.getId());
                chargeRequest.setSuccess(true);
            }
            return chargeRequest;
        }catch (StripeException e){
            log.error("StripeService (charge)" , e);
            throw new RuntimeException(e.getMessage());
        }
    }


}
