package com.bie24.xct.util.page;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 
 * 注意所有序号从1开始.
 * 
 * @param <T> Page中记录的类型.
 */
public class Page<T> {
	// -- 公共变量 --//
		public static final String ASC = "asc";
		public static final String DESC = "desc";
		public static final int DEFAULT_PAGESIZE = 10; // 默认分页单位

		// -- 分页参数 --//
		protected int pageNo = 1;
		protected int pageSize = -1;
		protected String orderBy = null;
		protected String order = null;
		protected boolean autoCount = true;

		// -- 返回结果 --//
		protected List<T> result = new ArrayList<T>();
		protected long totalCount = -1;
		protected int tagItemNo = 0;

		// -- 构造函数 --//
		public Page() {
		}

		public Page(int pageSize) {
			this.pageSize = pageSize;
		}

		// -- 分页参数访问函数 --//
		/**
		 * 获得当前页的页号,序号从1开始,默认为1.
		 */
		public int getPageNo() {
			return pageNo;
		}

		/**
		 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
		 */
		public void setPageNo(final int pageNo) {
			this.pageNo = pageNo;

			if (pageNo < 1) {
				this.pageNo = 1;
			}
		}

		/**
		 * 返回Page对象自身的setPageNo函数,可用于连续设置。
		 */
		public Page<T> pageNo(final int thePageNo) {
			setPageNo(thePageNo);
			return this;
		}

		/**
		 * 获得每页的记录数量, 默认为-1.
		 */
		public int getPageSize() {
			return pageSize;
		}

		/**
		 * 设置每页的记录数量.
		 */
		public void setPageSize(final int pageSize) {
			this.pageSize = pageSize;
		}

		/**
		 * 返回Page对象自身的setPageSize函数,可用于连续设置。
		 */
		public Page<T> pageSize(final int thePageSize) {
			setPageSize(thePageSize);
			return this;
		}

		/**
		 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
		 */
		public int getFirst() {
			return ((pageNo - 1) * pageSize) + 1;
		}

		/**
		 * 获得排序字段,无默认值. 多个排序字段时用','分隔.
		 */
		public String getOrderBy() {
			return orderBy;
		}

		/**
		 * 设置排序字段,多个排序字段时用','分隔.
		 */
		public void setOrderBy(final String orderBy) {
			this.orderBy = orderBy;
		}

		/**
		 * 返回Page对象自身的setOrderBy函数,可用于连续设置。
		 */
		public Page<T> orderBy(final String theOrderBy) {
			setOrderBy(theOrderBy);
			return this;
		}

		/**
		 * 获得排序方向, 无默认值.
		 */
		public String getOrder() {
			return order;
		}

		/**
		 * 设置排序方式向.
		 * 
		 * @param order
		 *            可选值为desc或asc,多个排序字段时用','分隔.
		 */
		public void setOrder(final String order) {
			String lowcaseOrder = StringUtils.lowerCase(order);

			// 检查order字符串的合法值
			String[] orders = StringUtils.split(lowcaseOrder, ',');
			for (String orderStr : orders) {
				if (!StringUtils.equals(DESC, orderStr)
						&& !StringUtils.equals(ASC, orderStr)) {
					throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
				}
			}

			this.order = lowcaseOrder;
		}

		/**
		 * 返回Page对象自身的setOrder函数,可用于连续设置。
		 */
		public Page<T> order(final String theOrder) {
			setOrder(theOrder);
			return this;
		}

		/**
		 * 是否已设置排序字段,无默认值.
		 */
		public boolean isOrderBySetted() {
			return (StringUtils.isNotBlank(orderBy) && StringUtils
					.isNotBlank(order));
		}

		/**
		 * 获得查询对象时是否先自动执行count查询获取总记录数, 默认为false.
		 */
		public boolean isAutoCount() {
			return autoCount;
		}

		/**
		 * 设置查询对象时是否自动先执行count查询获取总记录数.
		 */
		public void setAutoCount(final boolean autoCount) {
			this.autoCount = autoCount;
		}

		/**
		 * 返回Page对象自身的setAutoCount函数,可用于连续设置。
		 */
		public Page<T> autoCount(final boolean theAutoCount) {
			setAutoCount(theAutoCount);
			return this;
		}

		// -- 访问查询结果函数 --//

		/**
		 * 获得页内的记录列表.
		 */
		public List<T> getResult() {
			return result;
		}

		/**
		 * 设置页内的记录列表.
		 */
		public void setResult(final List<T> result) {
			this.result = result;
		}

		/**
		 * 获得总记录数, 默认值为-1.
		 */
		public long getTotalCount() {
			return totalCount;
		}

		/**
		 * 设置总记录数.
		 */
		public void setTotalCount(final long totalCount) {
			this.totalCount = totalCount;
		}

		/**
		 * 根据pageSize与totalCount计算总页数, 默认值为-1.
		 */
		public long getTotalPages() {
			if (totalCount < 0) {
				return -1;
			}

			long count = totalCount / pageSize;
			if (totalCount % pageSize > 0) {
				count++;
			}
			return count;
		}

		/**
		 * 是否还有下一页.
		 */
		public boolean isHasNext() {
			return (pageNo + 1 <= getTotalPages());
		}

		/**
		 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
		 */
		public int getNextPage() {
			if (isHasNext()) {
				return pageNo + 1;
			} else {
				return pageNo;
			}
		}

		/**
		 * 是否还有上一页.
		 */
		public boolean isHasPre() {
			return (pageNo - 1 >= 1);
		}

		/**
		 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
		 */
		public int getPrePage() {
			if (isHasPre()) {
				return pageNo - 1;
			} else {
				return pageNo;
			}
		}

		/**
		 * @param result
		 * @return
		 */
		public String getJson(StringBuffer result) {
			return "{\"pageNo\":\"" + pageNo + "\",\"pageSize\":\"" + pageSize
					+ "\",\"totalCount\":\"" + totalCount + "\",\"totalPage\":\""
					+ this.getTotalPages() + "\",\"result\":" + result + "}";
		}

		/**
		 * @param pageNo
		 * @param pageSize
		 * @return
		 */
		public static Page<?> getInstance(final Integer pageNo, final Integer pageSize) {
			Page page = new Page();
			page.setPageNo(pageNo);
			if (pageSize != null && pageSize > 0) {
				page.setPageSize(pageSize);
			} else {
				page.setPageSize(DEFAULT_PAGESIZE);
			}
			return page;
		}
		
		
		public String getPageInfo(){
			StringBuffer buffer = new StringBuffer("<nav><ul class='pagination'>");
			printFirst(buffer);
			printPrev(buffer);
			printPage(buffer);
			printNext(buffer);
			printLast(buffer);
			buildPageRemark(buffer);
			buffer.append("</ul></nav>");
			return  buffer.toString();
		}
		
		/**
		 * 方法描述：想buffer中append"第一页"
		 * @param buffer
		 */
		private void printFirst(StringBuffer buffer){
			
			if (pageNo == 1) {
				buffer.append("<li class='disabled'><a href='#'>«</a></li>");
			} else {
				buffer.append("<li ><a href='javaScript:turning(1)' rel='first'>«</a></li>");
			}
		}
		
		private void printLast(StringBuffer buffer){
			if (pageNo == getTotalPages()) {
				buffer.append("<li class='disabled'><a href='#'>»</a></li>");
			} else {
				buffer.append("<li ><a href='javaScript:turning(");
				buffer.append(getTotalPages());
				buffer.append(")' rel='last'>»</a></li>");
			}
		}
		
		/**
		  * 方法描述：向buffer中append“上一页”
		  * @param buffer
		  */
		private void printPrev(StringBuffer buffer) {
			if (pageNo == 1) {
				buffer.append("<li class='disabled'><a href='#'>‹</a></li>");
			} else {
				buffer.append("<li class='prev'><a href='javaScript:turning(");
				buffer.append(pageNo-1);
				buffer.append(")' rel='prev'>‹</a></li>");
			}
		}
		/**
		  * 方法描述：向buffer中append“下一页”
		  * @version: 2013-4-26 下午04:00:13
		  */
		private void printNext(StringBuffer buffer) {
			if (pageNo == getTotalPages()) {
				buffer.append("<li class='disabled'><a href='#'>›</a></li>");
			} else {
				buffer.append("<li class='next'><a href='javaScript:turning(");
				buffer.append(pageNo+1);
				buffer.append(")' rel='next'>›</a></li>");
			}
		}
		
		private void printPage(StringBuffer buffer){
			if(this.getTotalPages()<tagItemNo){
				this.buildPageTags(buffer);
			} else {
				int middle = tagItemNo / 2;
				int frontCount = middle;
				int backCount = middle;
				if (tagItemNo % 2 == 0) {
					frontCount = frontCount - 1;
				}
				if (pageNo - frontCount <= 0) {
					frontCount = pageNo-1;
				} else {
					if (pageNo + backCount + 1 > getTotalPages()) {
						frontCount = tagItemNo - ((int)getTotalPages() - pageNo - 1) - 1;
					}
				}
				if (pageNo + backCount >= getTotalPages()) {
					backCount = (int)getTotalPages() - pageNo;
				} else {
					if (pageNo - frontCount <= 0) {
						backCount = tagItemNo - (pageNo);
					}
				}
				if (pageNo - frontCount > 1) {
					buffer.append("<li class='disabled'><a  href='#'>...</a></li>");
				}
				for (int i = pageNo - frontCount; i <= pageNo + backCount; i++) {
					if (i == pageNo) {
						buffer.append("<li class='active'><span>");
						buffer.append(pageNo);
						buffer.append("</span></li>");
					} else {
						buffer.append("<li><a href='javascript:turning(");
						buffer.append(i);
						buffer.append(")'>");
						buffer.append(i);
						buffer.append("</a></li>");
					}
				}
				if (pageNo + middle < getTotalPages()) {
					buffer.append("<li class='disabled'><a href='#'>...</a></li>");
				}
			}
		}
		/**
		  * 方法描述：向buffer中append所有页码
		  * @param buffer
		  */
		private void buildPageTags(StringBuffer buffer){
			for (int i = 1; i <= getTotalPages(); i++) {
				if (i == pageNo) {
					buffer.append("<li class='active'><span>");
					buffer.append(pageNo);
					buffer.append("</span></li>");
				} else {
					buffer.append("<li><a href='javascript:turning(");
					buffer.append(i);
					buffer.append(")'>");
					buffer.append(i);
					buffer.append("</a></li>");
				}
			}
		} 

		private void buildPageRemark(StringBuffer buffer){
			buffer.append("<li class='remark'><span>共").append(getTotalPages());
			buffer.append("页，每页").append(pageSize).append("条，共计");
			buffer.append(totalCount).append("条。</span></li>");
		}
}
