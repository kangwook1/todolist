# 구현
***
간단한 crud를 jpa를 이용해 구현했고,jwt와 security를 이용해 회원 인증/인가를 구현했습니다.
refreshToken을 도입해 보안을 강화했고 redis 인메모리 db를 이용해 블랙리스트를 만들어 로그아웃을 구현했습니다.
또한 controlleradvice를 이용해 예외를 일괄적으로 처리하고 fiilter단에서 일어나는 예외또한 같은 형식으로 처리했습니다.

# 브랜치
***
처음에는 restful 브랜치에 rest api를 만들고 master에 스프링 mvc 방식을 구현하려 했으나
restapi만 만들게 되어 master에 restful 브랜치 내용을 그대로 붙여넣었습니다. master,restful 모두 같은 내용입니다.
