package ru.skogmark.aggregator.core.moderation;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.skogmark.aggregator.ApplicationContextAwareTest;
import ru.skogmark.aggregator.core.PostImage;
import ru.skogmark.aggregator.core.PostImageSize;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class PremoderationQueueDaoTest extends ApplicationContextAwareTest {
    @Autowired
    private UnmoderatedPostMapper mapper;

    @Test
    public void shouldMapParamsCorrectlyWhenInserting() {
        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        when(jdbcTemplate.queryForObject(any(), (MapSqlParameterSource) any(), (Class<Long>) any())).thenReturn(100L);
        PremoderationQueueDao dao = new PremoderationQueueDao(jdbcTemplate, mapper);
        UnmoderatedPost post = UnmoderatedPost.builder()
                .setChannelId(453453)
                .setTitle("title")
                .setText("test text of post with some info")
                .setImages(List.of(
                        new PostImage(List.of(PostImageSize.builder()
                                .setUuid("2dfc221f-4694-4535-9052-e7584348fa29")
                                .setSrc("img4")
                                .setWidth(640)
                                .setHeight(480)
                                .build())),
                        new PostImage(List.of(PostImageSize.builder()
                                .setUuid("1366db77-5972-454d-a853-ba21033da20d")
                                .setSrc("img5")
                                .setWidth(1024)
                                .setHeight(768)
                                .build()))))
                .build();

        dao.insertPost(post);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate, times(1))
                .queryForObject(any(), captor.capture(), (Class<List>) any());
        assertEquals(453453, captor.getValue().getValue("channelId"));
        assertEquals("title", captor.getValue().getValue("title"));
        assertEquals("test text of post with some info", captor.getValue().getValue("text"));
        assertEquals("{\"images\":[{\"sizes\":[{\"uuid\":\"2dfc221f-4694-4535-9052-e7584348fa29\",\"src\":\"img4\",\"width\":640,\"height\":480}]},{\"sizes\":[{\"uuid\":\"1366db77-5972-454d-a853-ba21033da20d\",\"src\":\"img5\",\"width\":1024,\"height\":768}]}]}",
                captor.getValue().getValue("images"));
    }
}