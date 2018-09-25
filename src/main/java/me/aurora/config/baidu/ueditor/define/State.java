package me.aurora.config.baidu.ueditor.define;

/**
 * 处理状态接口
 * @author baidu.com
 *
 */
public interface State {
	
	public boolean isSuccess();
	
	public void putInfo(String name, String val);
	
	public void putInfo(String name, long val);
	
	public String toJSONString();

}
