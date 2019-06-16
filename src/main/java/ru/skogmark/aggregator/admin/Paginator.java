package ru.skogmark.aggregator.admin;

import javax.annotation.Nonnull;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Paginator {
    private final int pageNum;
    private final int onPageCount;
    private final long totalCount;

    public Paginator(int pageNum, int onPageCount, long totalCount) {
        this.pageNum = pageNum - 1;
        this.onPageCount = onPageCount;
        this.totalCount = totalCount;
    }

    public int getPagesCount() {
        return (int) Math.ceil(totalCount / onPageCount);
    }

    public List<Page> getPages() {
        return List.of(
                new Page(1, "1"),
                new Page(2, "2"),
                new Page(3, "3"),
                new Page(4, "4"),
                new Page(5, "5"));
    }

    public OffsetInfo getOffsetInfo() {
        return new OffsetInfo(onPageCount, pageNum * onPageCount);
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
        private final String text;

        public Page(int num, @Nonnull String text) {
            this.num = num;
            this.text = requireNonNull(text, "text");
        }

        public int getNum() {
            return num;
        }

        @Nonnull
        public String getText() {
            return text;
        }
    }
}
