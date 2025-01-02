# 요구사항
## 기능 명세
### 채팅방
채팅방 정보 저장/조회

### 채팅 기능
채팅 메시지 저장(작성자명, 내용 필수)/조회

### 실시간 채팅
#### API 명세
GET /chat/room/list : 채팅방 목록 조회
GET /chat/room/make : 채팅방 생성 페이지
POST /chat/room/make : 채팅방 생성
GET /chat/room/{roomId} : 채팅방 입장
POST /chat/room/{roomId}/write : 메시지 작성
GET /chat/room/{roomId}/messagesAfter/{afterId} : 채팅방 메시지 조회

### 응답형식
```
{
"resultCode": "200",   // 성공: 2XX(HTTP 상태코드)
"msg": "메시지",
"data": {}            // 선택적
}
```

## Database Schema

### ChatRoom (채팅방 테이블)
| Column Name | Data Type    | Constraints       | Description    |
|-------------|--------------|-------------------|----------------|
| id          | BIGINT       | PK, AUTO_INCREMENT | 고유 식별자     |
| name        | VARCHAR(255) | NOT NULL          | 채팅방 이름     |
| createDate  | DATETIME     | NOT NULL          | 생성 일시       |
| modifyDate  | DATETIME     | NOT NULL          | 수정 일시       |

---
### ChatMessage (채팅 메시지 테이블)
| Column Name   | Data Type    | Constraints       | Description      |
|---------------|--------------|-------------------|------------------|
| id            | BIGINT       | PK, AUTO_INCREMENT | 고유 식별자       |
| chatRoom_id   | BIGINT       | FK                | 채팅방 ID         |
| writerName    | VARCHAR(255) | NOT NULL          | 작성자 이름       |
| content       | TEXT         | NOT NULL          | 메시지 내용       |
| createDate    | DATETIME     | NOT NULL          | 생성 일시         |
| modifyDate    | DATETIME     | NOT NULL          | 수정 일시         |
---

## Relationships
- **ChatMessage.chatRoom_id** → **ChatRoom.id**
    - `ChatMessage`는 `ChatRoom`과 1:N 관계.

## 폴링 방법으로 채팅 구현

## SSE 방법으로 채팅 구현

## WebSocket 방법으로 채팅 구현