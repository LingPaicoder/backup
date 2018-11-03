package ren.lingpai.lppoint.util;

/**
 * 分页参数类
 * Created by lrp on 17-5-21.
 */
public class PageParam {

    public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize;
    private int currentPage;
    private int prePage;
    private int nextPage;
    private int totalPage;
    private int totalCount;

    public static void setPageValues(int totalCount,PageParam pageParam){
        pageParam.setCurrentPage(pageParam.getCurrentPage()>0?pageParam.getCurrentPage():1);
        pageParam.setTotalCount(totalCount>0?totalCount:0);
        pageParam.setTotalPage((pageParam.getTotalCount() + pageParam.getPageSize() -1)/pageParam.getPageSize());
        pageParam.setNextPage(pageParam.getTotalPage()-pageParam.getCurrentPage()>0?
            pageParam.getCurrentPage()+1 : pageParam.getTotalPage());
        pageParam.setPrePage(pageParam.getCurrentPage()>1?
            pageParam.getCurrentPage()-1 : 1);
    }

    public static PageParam NewInstance(int CurrentPage){
        PageParam pageParam = new PageParam();
        pageParam.setCurrentPage(CurrentPage);
        return pageParam;
    }

    public PageParam() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     *
     * @param currentPage
     * @param pageSize
     */
    public PageParam(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
