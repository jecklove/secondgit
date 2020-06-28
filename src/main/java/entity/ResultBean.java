package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装ajax请求的结果
 * @author junki
 * @date 2020年6月16日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultBean {
	
	private Integer code;
	
	private String message;
	
	private Object data;
	
}
