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
                .setChannelId(MEMES.getId())
                .setSources(Collections.singleton(SourceContext.builder()
                        .setSourceId(VK_LEPRA.getId())
                        .setParser(vkApiParser)
                        .setParserLimit(10)
                        .setTimetable(Timetable.of(Sets.newHashSet(LocalTime.of(1, 0),
                                LocalTime.of(13, 0))))
                        .build()))
                .build();
    }
}
