package ru.skogmark.aggregator.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Paginator {
    private final int currentPage;
    private final int onPageCount;
    private final long totalCount;

    public Paginator(int currentPage, int itemsOnPageCount, long totalItemsCount) {
        this.currentPage = currentPage - 1;
        this.onPageCount = itemsOnPageCount;
        this.totalCount = totalItemsCount;
    }

    public int getPagesCount() {
        return (int) Math.ceil(totalCount / onPageCount);
    }

    public OffsetInfo getOffsetInfo() {
        return new OffsetInfo(onPageCount, currentPage * onPageCount);
    }

    public List<Page> getPages() {
        List<Page> pages = new ArrayList<>();
        if (currentPage > 0) {
            pages.add(new Page(1, true, false, false));
        }
        int pagesCount = getPagesCount();
        pages.addAll(IntStream.range(0, pagesCount)
                .filter(index -> Math.abs(index - currentPage) <= 2)
                .mapToObj(index -> new Page(index + 1, false, false, index == currentPage))
                .collect(Collectors.toList()));
        if (currentPage < pagesCount - 1) {
            pages.add(new Page(pagesCount, false, true, false));
        }
        return pages;
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

        public Page(int num, boolean first, boolean last, boolean current) {
            this.num = num;
            this.first = first;
            this.last = last;
            this.current = current;
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
