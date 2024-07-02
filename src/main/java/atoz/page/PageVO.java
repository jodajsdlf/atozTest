package atoz.page;

import lombok.Data;

@Data
public class PageVO {
	private int startPage;
	private int endPage;
	private boolean prev, next;
	private int total;
	private atoz.page.Criteria cri;

	public PageVO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;

		// 현재 페이지 번호에서 10으로 나눈 후 올림 처리하여 endPage 계산
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		this.startPage = this.endPage - 9;

		// 전체 페이지 수 계산
		int realEnd = (int) (Math.ceil((double) total / cri.getAmount()));
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}

		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
