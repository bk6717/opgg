# OPGG Clone Spring Boot Server

## 1. 프로젝트 개요

>-OP.GG는 월간 4500만명의 이용자가 사용하는 게임 전적 검색 사이트입니다. `https://www.op.gg`<br>
>-본 프로젝트는 OP.GG 의 어플리케이션과 웹을 클론 코딩하였습니다.<br>
>-리그오브레전드 게임 유저의 데이터를 API를 통해 조회하고 실제 경기 승, 패 상세내용을 제공하고 있습니다.<br>
>` Riot API: https://developer.riotgames.com` <br>
>-커뮤니티 서비스를 제공하고 있습니다.<br>

##  기능

1. 라이엇 API 호출
   - (라이엇 api ) https://ondolroom.tistory.com/650
   - (라이엇 cdn ) https://ondolroom.tistory.com/708
2. JPA
   - https://ondolroom.tistory.com/639
3. AOP - validation (bindinResult) 
   - https://ondolroom.tistory.com/727
4. security
   - https://blog.naver.com/bk6717/222047466102
5. jwt 
   - https://ondolroom.tistory.com/705
6. oauth2.0
   - https://ondolroom.tistory.com/734



## 프레젠테이션
https://docs.google.com/presentation/d/1CDGR5XWxe7j4afxRBsul-vG5oblBLByOJI-SKFED9YE/edit?usp=sharing

## 관리자 페이지 영상
https://youtu.be/idEkVdJDVrU


## DB 생성

```sql
CREATE DATABASE opgg CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
use opgg;
```



## DB 커뮤니티 구조도 



![community](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fdyc1sX%2FbtqH7ceqFiD%2FpK5KSmFJDR2PH71Ai0Dpkk%2Fimg.png)

