package ru.skogmark.aggregator.core.moderation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class PremoderationQueueDaoTest {
    @Test
    public void shouldMapParamsCorrectlyWhenInserting() {
        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        when(jdbcTemplate.queryForObject(any(), (MapSqlParameterSource) any(), (Class<Long>) any())).thenReturn(100L);
        PremoderationQueueDao dao = new PremoderationQueueDao(jdbcTemplate, new ObjectMapper());
        UnmoderatedPost post = UnmoderatedPost.builder()
                .setChannelId(453453)
                .setText("test text of post with some info")
                .setImages(Lists.newArrayList("http://localhost/image0", "http://localhost/image1",
                        "http://localhost/image2"))
                .build();

        dao.insertPost(post);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate, times(1))
                .queryForObject(any(), captor.capture(), (Class<List>) any());
        assertEquals(453453, captor.getValue().getValue("channelId"));
        assertEquals("test text of post with some info", captor.getValue().getValue("text"));
        assertEquals("[\"http://localhost/image0\",\"http://localhost/image1\",\"http://localhost/image2\"]",
                captor.getValue().getValue("images"));
    }
}