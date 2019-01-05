package ru.skogmark.valhall.core.content;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SourceDaoTest {
    @Test
    public void shouldMapParamsCorrectlyWhenInsertingAuthMeta() {
        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        SourceDao sourceDao = new SourceDao(jdbcTemplate);
        AuthorizationMeta authorizationMeta = AuthorizationMeta.of(8, "authorizationToken===");

        sourceDao.insertAuthorizationMeta(authorizationMeta);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate, times(1)).update(any(), captor.capture());
        assertEquals(8, captor.getValue().getValue("sourceId"));
        assertEquals("authorizationToken===", captor.getValue().getValue("authorizationToken"));
    }

    @Test
    public void shouldMapParamsCorrectlyWhenGettingAuthMeta() {
        AuthorizationMeta authorizationMeta = AuthorizationMeta.of(10, "authorizationToken===");
        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        when(jdbcTemplate.queryForObject(any(), (MapSqlParameterSource) any(), (RowMapper<AuthorizationMeta>) any()))
                .thenReturn(authorizationMeta);
        SourceDao sourceDao = new SourceDao(jdbcTemplate);

        sourceDao.getAuthorizationMeta(10);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate, times(1))
                .queryForObject(any(), captor.capture(), (RowMapper<AuthorizationMeta>) any());
        assertEquals(10, captor.getValue().getValue("sourceId"));
    }

    @Test
    public void shouldMapParamsCorrectlyWhenUpdatingAuthMeta() {
        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        when(jdbcTemplate.update(any(), (MapSqlParameterSource) any())).thenReturn(1);
        SourceDao sourceDao = new SourceDao(jdbcTemplate);
        AuthorizationMeta authorizationMeta = AuthorizationMeta.of(5, "authorizationToken===");

        sourceDao.updateAuthorizationMeta(authorizationMeta);

        ArgumentCaptor<MapSqlParameterSource> captor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(jdbcTemplate, times(1)).update(any(), captor.capture());
        assertEquals("authorizationToken===", captor.getValue().getValue("authorizationToken"));
        assertEquals(5, captor.getValue().getValue("sourceId"));
    }

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