package ru.skogmark.aggregator.core.content;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SourceDaoTest {
    @Test
    public void shouldMapParamsCorrectlyWhenGettingOffset() {
        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        SourceDao sourceDao = new SourceDao(jdbcTemplate);

        sourceDao.getOffset(3);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate, times(1))
                .queryForObject(any(), captor.capture(), (Class<Long>) any());
        assertEquals(3, captor.getValue().getValue("sourceId"));
    }
}