package ru.skogmark.aggregator.admin;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PaginatorTest {
    @Test
    public void should_return_pages_when_1_from_10() {
        Paginator paginator = new Paginator(1, 20, 200);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(4, pages.size());
        assertCurrentPage(1, pages.get(0));
        assertPage(2, pages.get(1));
        assertPage(3, pages.get(2));
        assertLastPage(10, pages.get(3));
    }

    @Test
    public void should_return_pages_when_2_from_10() {
        Paginator paginator = new Paginator(2, 20, 200);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(5, pages.size());
        assertPage(1, pages.get(0));
        assertCurrentPage(2, pages.get(1));
        assertPage(3, pages.get(2));
        assertPage(4, pages.get(3));
        assertLastPage(10, pages.get(4));
    }

    @Test
    public void should_return_pages_when_3_from_10() {
        Paginator paginator = new Paginator(3, 20, 200);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(6, pages.size());
        assertPage(1, pages.get(0));
        assertPage(2, pages.get(1));
        assertCurrentPage(3, pages.get(2));
        assertPage(4, pages.get(3));
        assertPage(5, pages.get(4));
        assertLastPage(10, pages.get(5));
    }

    @Test
    public void should_return_pages_when_4_from_10() {
        Paginator paginator = new Paginator(4, 20, 200);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(7, pages.size());
        assertFirstPage(pages.get(0));
        assertPage(2, pages.get(1));
        assertPage(3, pages.get(2));
        assertCurrentPage(4, pages.get(3));
        assertPage(5, pages.get(4));
        assertPage(6, pages.get(5));
        assertLastPage(10, pages.get(6));
    }

    @Test
    public void should_return_pages_when_8_from_10() {
        Paginator paginator = new Paginator(8, 20, 200);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(6, pages.size());
        assertFirstPage(pages.get(0));
        assertPage(6, pages.get(1));
        assertPage(7, pages.get(2));
        assertCurrentPage(8, pages.get(3));
        assertPage(9, pages.get(4));
        assertPage(10, pages.get(5));
    }

    @Test
    public void should_return_pages_when_9_from_10() {
        Paginator paginator = new Paginator(9, 20, 200);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(5, pages.size());
        assertFirstPage(pages.get(0));
        assertPage(7, pages.get(1));
        assertPage(8, pages.get(2));
        assertCurrentPage(9, pages.get(3));
        assertPage(10, pages.get(4));
    }

    @Test
    public void should_return_pages_when_10_from_10() {
        Paginator paginator = new Paginator(10, 20, 200);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(4, pages.size());
        assertFirstPage(pages.get(0));
        assertPage(8, pages.get(1));
        assertPage(9, pages.get(2));
        assertCurrentPage(10, pages.get(3));
    }

    @Test
    public void should_return_pages_when_1_from_1() {
        Paginator paginator = new Paginator(1, 20, 5);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(1, pages.size());
        assertCurrentPage(1, pages.get(0));
    }

    @Test
    public void should_return_pages_when_1_from_2() {
        Paginator paginator = new Paginator(1, 20, 30);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(2, pages.size());
        assertCurrentPage(1, pages.get(0));
        assertPage(2, pages.get(1));
    }

    @Test
    public void should_return_pages_when_1_from_3() {
        Paginator paginator = new Paginator(1, 20, 50);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(3, pages.size());
        assertCurrentPage(1, pages.get(0));
        assertPage(2, pages.get(1));
        assertPage(3, pages.get(2));
    }

    @Test
    public void should_return_pages_when_2_from_2() {
        Paginator paginator = new Paginator(2, 20, 30);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(2, pages.size());
        assertPage(1, pages.get(0));
        assertCurrentPage(2, pages.get(1));
    }

    @Test
    public void should_return_pages_when_2_from_3() {
        Paginator paginator = new Paginator(2, 20, 50);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(3, pages.size());
        assertPage(1, pages.get(0));
        assertCurrentPage(2, pages.get(1));
        assertPage(3, pages.get(2));
    }

    @Test
    public void should_return_pages_when_3_from_3() {
        Paginator paginator = new Paginator(3, 20, 50);
        List<Paginator.Page> pages = paginator.getPages();
        assertEquals(3, pages.size());
        assertPage(1, pages.get(0));
        assertPage(2, pages.get(1));
        assertCurrentPage(3, pages.get(2));
    }

    private static void assertCurrentPage(int expectedNum, Paginator.Page actualPage) {
        assertEquals(expectedNum, actualPage.getNum());
        assertTrue(actualPage.isCurrent());
        assertFalse(actualPage.isFirst());
        assertFalse(actualPage.isLast());
    }

    private static void assertPage(int expectedNum, Paginator.Page actualPage) {
        assertEquals(expectedNum, actualPage.getNum());
        assertFalse(actualPage.isCurrent());
        assertFalse(actualPage.isFirst());
        assertFalse(actualPage.isLast());
    }

    private static void assertFirstPage(Paginator.Page actualPage) {
        assertEquals(1, actualPage.getNum());
        assertFalse(actualPage.isCurrent());
        assertTrue(actualPage.isFirst());
        assertFalse(actualPage.isLast());
    }

    private static void assertLastPage(int expectedNum, Paginator.Page actualPage) {
        assertEquals(expectedNum, actualPage.getNum());
        assertFalse(actualPage.isCurrent());
        assertFalse(actualPage.isFirst());
        assertTrue(actualPage.isLast());
    }
}