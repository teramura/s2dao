package org.seasar.dao.pager;

import junit.framework.TestCase;

/**
 * @author Toshitaka Agata
 */
public class PagerConditionBaseTest extends TestCase {
    
    DefaultPagerCondition condition;
    protected void setUp() throws Exception {
        condition = new DefaultPagerCondition();
    }
    
    public void testFirstPage() {
        setCondtion(0, 10, 95);
        assertCondtion(false, true, 0, 1, 0, 10, 10, 9);
    }

    public void testSecondPage() {
        setCondtion(10, 10, 95);
        assertCondtion(true, true, 1, 2, 0, 20, 20, 9);
    }

    public void testLastPage() {
        setCondtion(90, 10, 95);
        assertCondtion(true, false, 9, 10, 80, 100, 95, 9);
    }

    public void testEmptyResult() {
        setCondtion(0, 10, 0);
        assertCondtion(false, false, 0, 1, 0, 10, 0, 0);
    }

    public void test9Result() {
        setCondtion(0, 10, 9);
        assertCondtion(false, false, 0, 1, 0, 10, 9, 0);
    }

    public void test10Result() {
        setCondtion(0, 10, 10);
        assertCondtion(false, false, 0, 1, 0, 10, 10, 0);
    }

    private void assertCondtion(
            boolean isPrev, boolean isNext, 
            int pageIndex, int pageCount, 
            int prevOffset, int nextOffset, 
            int currentLastOffset, int lastPageIndex)
    {
        PagerViewHelper helper = new PagerViewHelper(condition);
        assertEquals(isPrev, helper.isPrev());
        assertEquals(isNext, helper.isNext());
        assertEquals(pageIndex, helper.getPageIndex());
        assertEquals(pageCount, helper.getPageCount());
        assertEquals(prevOffset, helper.getPrevOffset());
        assertEquals(nextOffset, helper.getNextOffset());
        assertEquals(currentLastOffset, helper.getCurrentLastOffset());
        assertEquals(lastPageIndex, helper.getLastPageIndex());
    }

    private void setCondtion(int offset, int limit, int count) {
        condition.setOffset(offset);
        condition.setLimit(limit);
        condition.setCount(count);
    }
}
