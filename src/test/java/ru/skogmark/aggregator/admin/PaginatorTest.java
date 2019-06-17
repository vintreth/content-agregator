package ru.skogmark.aggregator.admin;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PaginatorTest {
    @Test
    public void should_return_pages_when_first_page_is_current() {
        Paginator paginator = new Paginator(1, 20, 200);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(4, pages.size());
        assertEquals(1, pages.get(0).getNum());
        assertTrue(pages.get(0).isCurrent());
        assertFalse(pages.get(0).isLast());
        assertFalse(pages.get(0).isLast());
    }
}