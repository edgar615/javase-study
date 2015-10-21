package com.edgar.core.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 命令的接口，所有的业务逻辑命令必须实现它.
 *
 * @author Edgar
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Command {

}
