package com.example.shopproject.shop.time;

import com.example.shopproject.shop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@RequiredArgsConstructor
public class ScheduleCart {

    private final CartRepository cartRepository;

    @Scheduled(fixedDelay = 600000)
    public void ScheduleCartTask() {
        cartRepository.deleteAll();
    }

}
