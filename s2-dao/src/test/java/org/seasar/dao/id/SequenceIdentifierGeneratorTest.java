/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.dao.id;

import org.seasar.dao.dbms.HSQL;
import org.seasar.extension.unit.S2TestCase;

/**
 * @author higa
 * 
 */
public class SequenceIdentifierGeneratorTest extends S2TestCase {

    /**
     * Constructor for InvocationImplTest.
     * 
     * @param arg0
     */
    public SequenceIdentifierGeneratorTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SequenceIdentifierGeneratorTest.class);
    }

    protected void setUp() throws Exception {
        include("j2ee-test.dicon");
    }

    protected void tearDown() throws Exception {
    }

    public void testGenerateTx() throws Exception {
        SequenceIdentifierGenerator generator = new SequenceIdentifierGenerator(
                "id", new HSQL());
        generator.setSequenceName("myseq");
        Hoge hoge = new Hoge();
        generator.setIdentifier(hoge, getDataSource());
        System.out.println(hoge.getId());
        assertTrue("1", hoge.getId() > 0);
    }
}