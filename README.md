# Snipp Link

## H2 Console 접속 방법 (Local)

로컬 환경에서 H2 Database Console에 접속하는 방법입니다.

- **접속 주소**: [http://localhost:8083/h2-console](http://localhost:8083/h2-console)
- **JDBC URL**: `jdbc:h2:mem:snipp_link`
- **User Name**: `sa`
- **Password**: (빈 값)


## Redis 설정
1. redis.conf 파일 내용 copy&paste
2. D드라이브 경로에 redisData 폴더 생성
3. powershell 켜서 해당 레디스 경로로 들어가서
   ex) cd D:\redis-7.0.11-windows\
   .\redis-server.exe redis.conf 입력