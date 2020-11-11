package org.jckj.modules.auth.provider;

import lombok.Data;
import org.jckj.core.tool.support.Kv;

/**
 * TokenParameter
 *
 * @author Peng
 */
@Data
public class TokenParameter {

	private Kv args = Kv.create();

}
