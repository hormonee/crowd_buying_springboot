package com.hormonic.crowd_buying.util.paging;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PageInfo {
    private int startPage;  // 화면에 보여지는 시작 페이지 번호
    private	int endPage;  // 화면에 보여지는 끝 페이지 번호
    private	boolean prev, next;  //다음 , 이전 버튼 활성화 여부

    private int pageNum;  //사용자가 조회하는 페이지 번호
    private int amount;  //한 페이지에 출력되는 데이터 개수
    private	int total;  //총 게시물 수

    private int realEnd;  //페이지네이션 실제 끝 번호
    //타임리프에서는 향상된 for문을 사용하기 위해 list로 페이지 번호를 생성
    private ArrayList<Integer> pageList; //페이지 번호리스트

    private	PageCriteria cri;  //페이지 기준

    private int pageCount = 5;  //페이지당 출력할 페이지 수

    //생성자 - pageVO가 만들어질 때, cri, total을 받는다.
    public PageInfo(PageCriteria cri, int total) {
        //계산에 필요한 값(페이지 번호, 데이터 개수, 전체 게시글 수, cri)을 초기화
        this.cri = cri;
        this.pageNum = cri.getPageNum();
        this.amount = cri.getAmount();
        this.total = total;  //총 게시물 수

        this.endPage = (int)Math.ceil(this.pageNum / (double)pageCount) * pageCount;
        this.startPage = endPage - pageCount + 1;

        this.realEnd = (int)Math.ceil(total / (double)this.amount);

        if(this.endPage > realEnd) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = realEnd > this.endPage;

        this.pageList = new ArrayList<>();
        for(int i=this.startPage; i<=this.endPage; i++) {
            pageList.add(i);
        }
    }
}
