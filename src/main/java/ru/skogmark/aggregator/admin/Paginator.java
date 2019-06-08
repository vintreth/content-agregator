package ru.skogmark.aggregator.admin;

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
}
