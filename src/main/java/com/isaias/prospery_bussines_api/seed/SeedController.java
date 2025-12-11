package com.isaias.prospery_bussines_api.seed;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isaias.prospery_bussines_api.common.UtilsService;

@RestController
@RequestMapping("/data")
public class SeedController {
    private SeedService seedService;
    private UtilsService utilsService;

    public SeedController(SeedService seedService, UtilsService utilsService) {
        this.seedService = seedService;
        this.utilsService = utilsService;
    }

    @GetMapping("/seed")
    public ResponseEntity<?> executeSeed() {
        return utilsService.handleResponse(
                () -> seedService.creatingData()
            );
    }
    
}