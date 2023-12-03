package com.meche.service;

import com.meche.model.AnotherCharge;
import com.meche.repo.AnotherChargeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author sidof
 * @Since 28/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AnotherChargeService {
    private final AnotherChargeRepo anotherChargeRepo;

    public AnotherCharge save(AnotherCharge anotherCharge) {
        return anotherChargeRepo.save(anotherCharge);
    }

    public AnotherCharge anotherCharge(Long id){
        return anotherChargeRepo.findById(id).orElseThrow();
    }
}
