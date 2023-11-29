package com.meche.api;

import com.meche.model.Charge;
import com.meche.service.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@RequestMapping("/api/v1/hair/charge")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
public class ChargeApi {

    private final ChargeService chargeService;

    @PostMapping
    public ResponseEntity<Charge> saveCharge(@RequestBody Charge charge) {
        Charge savedCharge = chargeService.save(charge);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharge);
    }

    @PutMapping
    public ResponseEntity<Charge> updateCharge(@RequestBody Charge charge) {
        Charge updatedCharge = chargeService.update(charge);
        return ResponseEntity.ok(updatedCharge);
    }

    @GetMapping
    public List<Charge> getAllCharges() {
        return chargeService.getCharges();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharge(@PathVariable("id") Long id) {
        chargeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
