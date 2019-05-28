package ru.skogmark.aggregator.channel;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skogmark.aggregator.core.ChannelContext;
import ru.skogmark.aggregator.core.Timetable;
import ru.skogmark.aggregator.core.content.AuthorizationMeta;
import ru.skogmark.aggregator.core.content.Content;
import ru.skogmark.aggregator.core.content.Parser;
import ru.skogmark.aggregator.core.content.ParserAuthorizationListener;
import ru.skogmark.aggregator.source.SourceContextImpl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static ru.skogmark.aggregator.channel.Channel.MEMES;

@Configuration
public class ChannelConfiguration {
    @Bean
    ChannelContext memesChannel() {
        return ChannelContextImpl.builder()
                .channel(MEMES)
                .sources(Collections.singleton(SourceContextImpl.builder()
                        .parsingLimit(10) // todo hardcode
                        .parser(new Parser() {
                            @Override
                            public void auth(@Nullable AuthorizationMeta authorizationMeta, @Nonnull ParserAuthorizationListener listener) {

                            }

                            @Override
                            public Optional<Content> parse(long offset) {
                                return Optional.empty();
                            }
                        })
                        .timetable(Timetable.of(Sets.newHashSet(LocalTime.of(1, 0),
                                LocalTime.of(13, 0))))
                        .build()))
                .build();
    }
}
