package com.vorobey.quickeye.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.vorobey.quickeye.annotation.BotController;
import com.vorobey.quickeye.annotation.mappings.CommandMapping;
import com.vorobey.quickeye.config.property.BotProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
@Slf4j
public class BotConfig {

    private BotProperty botProperty;
    private ApplicationContext applicationContext;

    @Bean
    public TelegramBot bot() {
        TelegramBot telegramBot = new TelegramBot(botProperty.getToken());
        telegramBot.setUpdatesListener(this::handleUpdates, new GetUpdates().limit(1));
        return telegramBot;
    }

    public int handleUpdates(List<Update> updates) {
        updates.forEach(update -> {
            try {
                handleUpdate(update);
            } catch (RuntimeException exception) {
                log.error("Error during handling update", exception);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void handleUpdate(Update update) {
        Collection<Object> controllers = applicationContext.getBeansWithAnnotation(BotController.class).values();

        Message message = update.message();
        if (Objects.nonNull(message)) {
            String text = message.text();
            // handle command
            if (Objects.nonNull(text) && text.startsWith("/")) {
                String[] split = text.split(" ");
                String command = split[0];
                List<String> args = new ArrayList<>();
                for (int i = 1; i < split.length; i++) {
                    args.add(split[i]);
                }

                List<Pair<Object, Method>> methods = controllers.stream()
                        .flatMap(o -> Arrays.stream(o.getClass().getMethods()).map(method -> Pair.of(o, method)))
                        .filter(pair -> {
                            CommandMapping annotation = pair.getRight().getAnnotation(CommandMapping.class);
                            return Objects.nonNull(annotation)
                                    && annotation.value().equals(command);
                        }).toList();
                if (methods.size() >= 2) {
                    throw new RuntimeException("There " + methods.size() + " methods, that can handle this request "
                            + methods.stream().map(m -> m.getLeft().getClass().getName() + "."
                            + m.getRight().getName()).toList());
                }
                if (methods.isEmpty()) {
                    throw new RuntimeException("Can't find method to handle update");
                }

                Object controller = methods.get(0).getLeft();
                Method method = methods.get(0).getRight();
                try {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    method.invoke(controller);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
