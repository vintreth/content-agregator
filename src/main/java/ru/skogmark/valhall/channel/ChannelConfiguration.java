package ru.skogmark.valhall.channel;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skogmark.valhall.core.ChannelContext;
import ru.skogmark.valhall.core.Timetable;
import ru.skogmark.valhall.core.content.AuthorizationMeta;
import ru.skogmark.valhall.core.content.Content;
import ru.skogmark.valhall.core.content.Parser;
import ru.skogmark.valhall.core.content.ParserAuthorizationListener;
import ru.skogmark.valhall.source.SourceContextImpl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static ru.skogmark.valhall.channel.Channel.MEMES;

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
                            public int getSourceId() {
                                return 0;
                            }

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
