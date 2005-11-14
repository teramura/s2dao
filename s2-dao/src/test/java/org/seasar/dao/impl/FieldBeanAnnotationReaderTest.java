package org.seasar.dao.impl;

import java.util.Date;

import org.seasar.dao.AnnotationReaderFactory;
import org.seasar.dao.BeanAnnotationReader;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class FieldBeanAnnotationReaderTest extends S2TestCase {
	protected AnnotationReaderFactory readerFactory;
	/**
	 * Constructor for InvocationImplTest.
	 * 
	 * @param arg0
	 */
	public FieldBeanAnnotationReaderTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(FieldBeanAnnotationReaderTest.class);
	}
	public void setUp() {
		include("FieldBeanAnnotationReaderTest.dicon");
	}

	public void testGetColumnAnnotation() {
		Class clazz = AnnotationTestBean1.class;
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
		BeanAnnotationReader reader = readerFactory.createBeanAnnotationReader(clazz);
		assertEquals("1","Cprop1",reader.getColumnAnnotation(beanDesc.getPropertyDesc("prop1")));
		assertEquals("2","prop2",reader.getColumnAnnotation(beanDesc.getPropertyDesc("prop2")));
	}

	public void testGetTableAnnotation() {
		Class clazz1 = AnnotationTestBean1.class;
		BeanAnnotationReader reader1 = readerFactory.createBeanAnnotationReader(clazz1);
		assertEquals("1","TABLE",reader1.getTableAnnotation());
		Class clazz2 = AnnotationTestBean2.class;
		BeanAnnotationReader reader2 = readerFactory.createBeanAnnotationReader(clazz2);
		assertNull("2",reader2.getTableAnnotation());
	}

	public void testGetVersionNoProteryNameAnnotation() {
		Class clazz1 = AnnotationTestBean1.class;
		BeanAnnotationReader reader1 = readerFactory.createBeanAnnotationReader(clazz1);
		String str1 = reader1.getVersionNoProteryNameAnnotation();
		assertEquals("1","myVersionNo",str1);
		Class clazz2 = AnnotationTestBean2.class;
		BeanAnnotationReader reader2 = readerFactory.createBeanAnnotationReader(clazz2);
		String str2 = reader2.getVersionNoProteryNameAnnotation();
		assertNull("1",str2);
	}

	public void testGetTimestampPropertyName() {
		Class clazz1 = AnnotationTestBean1.class;
		BeanAnnotationReader reader1 = readerFactory.createBeanAnnotationReader(clazz1);
		String str1 = reader1.getTimestampPropertyName();
		assertEquals("1","myTimestamp",str1);
		Class clazz2 = AnnotationTestBean2.class;
		BeanAnnotationReader reader2 = readerFactory.createBeanAnnotationReader(clazz2);
		String str2 = reader2.getTimestampPropertyName();
		assertNull("1",str2);
	}

	public void testGetId() {
		Class clazz1 = AnnotationTestBean1.class;
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz1);
		BeanAnnotationReader reader1 = readerFactory.createBeanAnnotationReader(clazz1);
		String str1 = reader1.getId(beanDesc.getPropertyDesc("prop1"));
		assertEquals("1","sequence, sequenceName=myseq",str1);
		String str2 = reader1.getId(beanDesc.getPropertyDesc("prop2"));
		assertNull("1",str2);
	}

	public void testGetNoPersisteneProps() {
		Class clazz1 = AnnotationTestBean1.class;
		BeanAnnotationReader reader1 = readerFactory.createBeanAnnotationReader(clazz1);
		String[] strings1 = reader1.getNoPersisteneProps();
		assertEquals("1","prop2",strings1[0]);
		Class clazz2 = AnnotationTestBean2.class;
		BeanAnnotationReader reader2 = readerFactory.createBeanAnnotationReader(clazz2);
		String[] strings2 = reader2.getNoPersisteneProps();
		assertNull("1",strings2);
	}

	public void testGetRelationKey() {
		Class clazz1 = AnnotationTestBean1.class;
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz1);
		BeanAnnotationReader reader1 = readerFactory.createBeanAnnotationReader(clazz1);
		PropertyDesc pd = beanDesc.getPropertyDesc("department");
		assertTrue("1",reader1.hasRelationNo(pd));
		assertEquals("1",0,reader1.getRelationNo(pd));
		assertEquals("1","DEPTNUM:DEPTNO",reader1.getRelationKey(pd));
		assertFalse("1",reader1.hasRelationNo(beanDesc.getPropertyDesc("prop2")));
	}

	public static class AnnotationTestBean1 {
		public static String TABLE = "TABLE";
		public static final String NO_PERSISTENT_PROPS = "prop2";
		public static final String TIMESTAMP_PROPERTY = "myTimestamp";
		public static final String VERSION_NO_PROPERTY = "myVersionNo";
		public static final String prop1_ID = "sequence, sequenceName=myseq";
		public static String prop1_COLUMN ="Cprop1";
		private Department department;
		private Date myTimestamp;
		public int getProp1(){
			return 0;
		}
		public void setProp1(int i){}

		public int getProp2(){
			return 0;
		}
		public void setProp2(int i){}
		public Date getMyTimestamp() {
			return myTimestamp;
		}
		public void setMyTimestamp(Date myTimestamp) {
			this.myTimestamp = myTimestamp;
		}
		public static final int department_RELNO = 0;
		public static final String department_RELKEYS = "DEPTNUM:DEPTNO";
		public Department getDepartment() {
			return department;
		}
		public void setDepartment(Department department) {
			this.department = department;
		}
		
	}
	public static class AnnotationTestBean2 {
		public static String prop1_COLUMN ="Cprop1";
		public int getProp1(){
			return 0;
		}
		public void setProp1(int i){}

		public int getProp2(){
			return 0;
		}
		public void setProp2(int i){}
	}
}
