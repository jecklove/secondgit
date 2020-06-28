package entity;

import java.util.Date;

import lombok.*;

/**
 * 
 * 	评论实体类
 * 
 * @author junki
 * @date 2020年6月5日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
	
	private Long id;
	
	private String content;
	
	private Date create_time;
	
	private Long user_id;
	
	private Long post_id;
	
	
	private String nickname;

}
