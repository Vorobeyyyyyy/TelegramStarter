package com.github.vorobeyyyyyy.telegramstarter.service.resultresolver;

import java.util.List;
import java.util.Optional;

import com.github.vorobeyyyyyy.telegramstarter.service.ResultResolver;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResultResolverManager {
    private final List<ResultResolver> resultResolvers;

    public void resolveResponse(Update update, Object response) {
        if (response == null) {
            return;
        }
        var responseResolvers = resultResolvers.stream()
                .map(resolver -> resolver.resolve(update, response))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        if (responseResolvers.size() > 1) {
            throw new RuntimeException("More than 1 responseResolver found");
        }
        if (responseResolvers.isEmpty()) {
            log.warn("Not fond suitable resolver for response {}", response);
        } else {
            responseResolvers.get(0).run();
        }
    }
}
