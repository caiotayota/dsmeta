package com.caiotayota.dsmeta.controllers;

import com.caiotayota.dsmeta.entities.Sale;
import com.caiotayota.dsmeta.services.SaleService;
import com.caiotayota.dsmeta.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/sales", produces = MediaType.APPLICATION_JSON_VALUE)
public class SaleController {

    private final SaleService service;

    private final SmsService smsService;

    @Autowired
    public SaleController(SaleService service, SmsService smsService) {
        this.service = service;
        this.smsService = smsService;
    }

    @GetMapping
    public Page<Sale> findSales(
            @RequestParam(value = "minDate", defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", defaultValue = "") String maxDate,
            Pageable pageable) {
        return service.findSales(minDate, maxDate, pageable);
    }

    @GetMapping("/{id}/notification")
    public void notifySms(@PathVariable Long id) {
        smsService.sendSms(id);
    }

    @PostMapping(path = "/new", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Sale newSale(@RequestBody @Valid Sale sale) {
        return service.newSale(sale);
    }
}
