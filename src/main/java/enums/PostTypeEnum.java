package enums;

/**
 * 	帖子类型枚举
 * @author junki
 * @date 2020年6月5日
 */
public enum PostTypeEnum {
	
	CODE("编程"),
	GAME("游戏"),
	GIRL("美女"),
	LIFE("生活");
	
	private String name;
	
	PostTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
