package com.example.NewMock.Controller;

import com.example.NewMock.Model.RequestDTO;
import com.example.NewMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
public class MainController
{
    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper oMapper = new ObjectMapper();

    @PostMapping
    (
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO)
    {
        try
        {
            String rqUID = requestDTO.getRqUID();
            String clientId = requestDTO.getClientId();
            String account = requestDTO.getAccount();
            String currency;
            BigDecimal maxLimit;
            BigDecimal balance;

            char firstDigit = clientId.charAt(0);

            if (firstDigit == '8')
            {
                maxLimit = new BigDecimal(2000);
                currency = "US";
            }
            else if (firstDigit == '9')
            {
                maxLimit = new BigDecimal(1000);
                currency = "EU";
            }
            else
            {
                maxLimit = new BigDecimal(10000);
                currency = "RUB";
            }

            balance = BigDecimal.valueOf(Math.random()).multiply(maxLimit).setScale(2, RoundingMode.HALF_UP);

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(account);
            responseDTO.setMaxLimit(maxLimit);
            responseDTO.setBalance(balance);
            responseDTO.setCurrency(currency);

            log.info("\n********** RequestDTO **********" + oMapper.writerWithDefaultPrettyPrinter(). writeValueAsString(requestDTO));
            log.info("\n********** ResponseDTO **********" + oMapper.writerWithDefaultPrettyPrinter(). writeValueAsString(responseDTO));

            return responseDTO;
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
