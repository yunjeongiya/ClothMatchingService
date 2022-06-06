# ClothMatchingService

LinkPage 변경사항 _ 0607,01:24
--> 기존에 만들어둔 link와 main branch를 병합 (Merge가 아니라 수동으로 넣었기에 merge request 등은 없었을 겁니다!)

1) layout 폴더
	0. link쪽을 위한 fragment fragment_link변경, (linkrow, linkrowrow) 추가
	1. fragment_link : '이번달' -> '이번주' 로 변경
	2. fragment_link : NestedScrollView를 이용, 전체가 다 scroll하도록 변경

-----------

2) java 폴더
	0. link를 위한 clothadapter, clothshopadaper 추가, linkfragment 변경
	1. ShopInfo - 데이터 클래스를 위한 kotlin class - 추가

----------

3) drawable 폴더
	1. R.drawable에 각 회사 icon 추가
