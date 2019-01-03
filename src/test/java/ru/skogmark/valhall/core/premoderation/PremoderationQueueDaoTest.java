package ru.skogmark.valhall.core.premoderation;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.skogmark.valhall.core.premoderation.PremoderationQueueDao;
import ru.skogmark.valhall.core.premoderation.UnmoderatedPost;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class PremoderationQueueDaoTest {
    @Test
    public void shouldMapParamsCorrectlyWhenInserting() {
        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        when(jdbcTemplate.queryForObject(any(), (MapSqlParameterSource) any(), (Class<Long>) any())).thenReturn(100L);
        PremoderationQueueDao dao = new PremoderationQueueDao(jdbcTemplate);
        UnmoderatedPost post = UnmoderatedPost.builder()
                .text("test text of post with some info")
                .imageId(12345L)
                .build();

        dao.insertPost(post);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate, times(1))
                .queryForObject(any(), captor.capture(), (Class<Long>) any());
        assertEquals("test text of post with some info", captor.getValue().getValue("text"));
        assertEquals(12345L, captor.getValue().getValue("imageId"));
    }
}