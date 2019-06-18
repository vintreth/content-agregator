package ru.skogmark.aggregator.admin;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Paginator {
    private static final int ITEMS_COUNT_ON_SIDE = 2;

    private final int currentPage;
    private final int onPageCount;
    private final long totalCount;

    public Paginator(int currentPage, int itemsOnPageCount, long totalItemsCount) {
        this.currentPage = currentPage;
        this.onPageCount = itemsOnPageCount;
        this.totalCount = totalItemsCount;
    }

    public int getPagesCount() {
        return (int) Math.ceil(totalCount / onPageCount);
    }

    public OffsetInfo getOffsetInfo() {
        return new OffsetInfo(onPageCount, (currentPage - 1) * onPageCount);
    }

    public List<Page> getPages() {
        Map<Integer, Page> pages = new HashMap<>();
        int lastPage = getPagesCount();
        for (int i = currentPage - ITEMS_COUNT_ON_SIDE; i <= currentPage + ITEMS_COUNT_ON_SIDE; i++) {
            if (i < 1 || i > lastPage) {
                continue;
            }
            pages.put(i, Page.newPage(i , i == currentPage));
        }
        pages.computeIfAbsent(1, k -> Page.newFirstPage());
        pages.computeIfAbsent(lastPage, Page::newLastPage);
        return pages.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public static class OffsetInfo {
        private final int limit;
        private final long offset;

        private OffsetInfo(int limit, long offset) {
            this.limit = limit;
            this.offset = offset;
        }

        public int getLimit() {
            return limit;
        }

        public long getOffset() {
            return offset;
        }
    }

    public static class Page {
        private final int num;
        private final boolean first;
        private final boolean last;
        private final boolean current;

        private Page(int num, boolean first, boolean last, boolean current) {
            this.num = num;
            this.first = first;
            this.last = last;
            this.current = current;
        }

        public static Page newPage(int num, boolean current) {
            return new Page(num, false, false, current);
        }

        public static Page newFirstPage() {
            return new Page(1, true, false, false);
        }

        public static Page newLastPage(int num) {
            return new Page(num, false, true, false);
        }

        public int getNum() {
            return num;
        }

        public boolean isFirst() {
            return first;
        }

        public boolean isLast() {
            return last;
        }

        public boolean isCurrent() {
            return current;
        }
    }
}
