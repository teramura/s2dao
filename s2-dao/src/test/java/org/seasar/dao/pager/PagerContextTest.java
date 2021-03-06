/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.dao.pager;

import junit.framework.TestCase;

/**
 * PagerContextTest
 * 
 * @author agata
 * @author azusa
 */
public class PagerContextTest extends TestCase {

    public void testIsPagerCondition() {
        PagerCondition pagerConderion = new DefaultPagerCondition();
        pagerConderion.setLimit(10);
        assertEquals(true, PagerContext
                .isPagerCondition(new Object[] { pagerConderion }));
        assertEquals(true, PagerContext.isPagerCondition(new Object[] {
                pagerConderion, "dummy" }));
        assertEquals(false, PagerContext
                .isPagerCondition(new Object[] { "dummy" }));
        pagerConderion.setLimit(PagerCondition.NONE_LIMIT);
        pagerConderion.setOffset(0);
        assertEquals(false, PagerContext
                .isPagerCondition(new Object[] { pagerConderion }));
        pagerConderion.setLimit(PagerCondition.NONE_LIMIT);
        pagerConderion.setOffset(10);
        assertEquals(true, PagerContext
                .isPagerCondition(new Object[] { pagerConderion }));

    }

    public void testGetPagerCondition() {
        PagerCondition condition = new DefaultPagerCondition();
        PagerCondition condition2 = PagerContext
                .getPagerCondition(new Object[] { "dummy", condition, "dummy" });
        assertEquals(true, condition == condition2);
    }

}
