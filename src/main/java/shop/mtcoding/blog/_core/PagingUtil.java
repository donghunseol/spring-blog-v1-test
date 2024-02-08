package shop.mtcoding.blog._core;

public class PagingUtil {

    public static boolean isFirst(int currentPage) {
        return currentPage == 0 ? true : false;
    }

    public static boolean isLast(int currentPage, int totalPage) {
        int totalPageCount = getTotlaPageCount(totalPage);
        return currentPage + 1 == totalPageCount ? true : false;
    }

    // 전체 페이지 파악
    public static int getTotlaPageCount(int totalCount) {
        int remainCount = totalCount % Constant.PAGING_COUNT;
        int totalPageCount = totalCount / Constant.PAGING_COUNT;

        if (remainCount > 0) {
            totalPageCount = totalPageCount + 1;
        }

        return totalPageCount;
    }
}
