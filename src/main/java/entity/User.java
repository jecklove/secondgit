package entity;

import lombok.*;

/**
 * 
 * 	用户实体类
 * 
 * @author junki
 * @date 2020年6月5日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	private Long id;
	
	private String nickname;
	
	private String username;
	
	private String password;
	
}
