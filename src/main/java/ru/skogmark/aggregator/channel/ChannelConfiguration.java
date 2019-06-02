package ru.skogmark.aggregator.channel;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skogmark.aggregator.core.ChannelContext;
import ru.skogmark.aggregator.core.SourceContext;
import ru.skogmark.aggregator.core.Timetable;
import ru.skogmark.aggregator.parser.vk.VkApiParser;

import java.time.LocalTime;
import java.util.Collections;

import static ru.skogmark.aggregator.channel.Channel.MEMES;
import static ru.skogmark.aggregator.channel.Source.VK_LEPRA;

@Configuration
public class ChannelConfiguration {
    @Bean
    ChannelContext memesChannel(VkApiParser vkApiParser) {
        return ChannelContext.builder()
                .withChannelId(MEMES.getId())
                .withSources(Collections.singleton(SourceContext.builder()
                        .withSourceId(VK_LEPRA.getId())
                        .withParser(vkApiParser)
                        .withParserLimit(10)
                        .withTimetable(Timetable.of(Sets.newHashSet(LocalTime.of(1, 0),
                                LocalTime.of(13, 0))))
                        .build()))
                .build();
    }
}
