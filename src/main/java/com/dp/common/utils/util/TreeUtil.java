package com.dp.common.utils.util;

import java.util.ArrayList;
import java.util.List;



public class TreeUtil {

	/**
	 * 将所有没有层级关系的TreeNode 组织成有层级关系的TreeNode
	 * @param beans
	 * @param topPid
	 * @return
	 */
	public static List<? extends TreeNode> recurse2tree(List<? extends TreeNode> beans, String topPid) {
		ArrayList<TreeNode> list = new ArrayList<TreeNode>();
		if (beans == null || beans.size() == 0)
			return list;
		for (TreeNode bean : beans) {
			if (topPid == null) {
				if (bean.getParentId() == null){
					list.add(bean);
				}
			} else {
				if (topPid.equals(bean.getParentId())) {
					list.add(bean);
				}
			}
		}
		beans.removeAll(list);
		for (TreeNode topBean : list) {
			setChildren2ParentBean(beans, topBean);
		}
		return list;
	}
	private static void setChildren2ParentBean(List<? extends TreeNode> beans, TreeNode parent) {
		if (beans == null || beans.size() == 0 || parent == null)
			return;
		List<TreeNode> tmp = new ArrayList<TreeNode>();
		for (TreeNode bean : beans) {
			if ((parent.getId()).equals(bean.getParentId())) {
				tmp.add(bean);
			}
		}
		beans.removeAll(tmp);
		parent.setChildren(tmp);
		if (tmp.size()>0) {
			for (TreeNode p : tmp) {
				setChildren2ParentBean(beans, p);
			}
		}
	}

	public static class TreeNode {
		private String id;
		private String parentId;
		private List<TreeNode> children;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public List<TreeNode> getChildren() {
			return children;
		}

		public void setChildren(List<TreeNode> children) {
			this.children = children;
		}
	}

}
