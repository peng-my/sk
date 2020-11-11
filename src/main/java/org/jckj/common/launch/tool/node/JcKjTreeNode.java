package org.jckj.common.launch.tool.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jckj.core.tool.node.TreeNode;

@Data
@EqualsAndHashCode(callSuper = false)
public class JcKjTreeNode extends TreeNode {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	private String strKey;

}
