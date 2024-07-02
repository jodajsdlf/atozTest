package atoz.page;

import lombok.Data;

@Data
public class Criteria {
	private int pageNum;
	private int amount;

	private String searchType = "com_name";
	private String searchName = "";

	private long com_number;
	private int dep_number;
	private int num;

	public Criteria() {
		this(1, 5);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public int getOffset() {
		return (pageNum - 1) * amount;
	}
}
