package com.shopeasy.estore.api;

import com.shopeasy.estore.dto.StripeChargeDto;
import com.shopeasy.estore.dto.StripeTokenDto;
import com.shopeasy.estore.service.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stripe")
@AllArgsConstructor
public class StripeApi {

    private final StripeService stripeService;

   //card info is added, if card info incorrect no token is generated and thus no charge
    @PostMapping("/card/token")
    @ResponseBody
    public StripeTokenDto createCardToken(@RequestBody StripeTokenDto model){
        return stripeService.createCardToken(model);

    }

    @PostMapping("/charge")
    @ResponseBody
    public StripeChargeDto charge(@RequestBody StripeChargeDto model){

        return stripeService.charge(model);
    }

}
