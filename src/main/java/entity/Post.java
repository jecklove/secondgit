package entity;

import java.util.Date;
import java.util.List;

import lombok.*;

/**
 * 
 * 	帖子实体类
 * 
 * @author junki
 * @date 2020年6月5日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
	
	private Long id;
	
	private String title;
	
	private Byte type;
	
	private String summary;
	
	private String content;
	
	private Date create_time;
	
	private Long user_id;

	// 以下属性用于包装数据，并非数据表字段
	
	private String nickname;
	
	private Long comment_count;
	
	private List<Comment> comments;
	
	private Date create_time_begin;
	
	private Date create_time_end;
	
}
