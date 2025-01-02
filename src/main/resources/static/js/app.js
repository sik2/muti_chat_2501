document.addEventListener("DOMContentLoaded", function() {
    loadRooms();
});

// 채팅방 목록 불러오기
function loadRooms() {
    fetch('/rooms')
        .then(response => response.json())
        .then(data => {
            const roomList = document.getElementById('room-list');
            roomList.innerHTML = '';
            data.forEach(room => {
                const li = document.createElement('li');
                li.innerHTML = `
                    ${room.name}
                    <button onclick="enterRoom(${room.id}, '${room.name}')">입장</button>
                `;
                roomList.appendChild(li);
            });
        })
        .catch(error => console.error('Error:', error));
}

// 채팅방 입장
function enterRoom(roomId, roomName) {
    document.querySelector('.container').classList.add('hidden');
    const chatRoom = document.getElementById('chat-room');
    chatRoom.classList.remove('hidden');
    document.getElementById('room-title').innerText = roomName;

    loadChats(roomId);
}

// 채팅 메시지 불러오기
function loadChats(roomId) {
    fetch(`/rooms/${roomId}/chats`)
        .then(response => response.json())
        .then(data => {
            const chatList = document.getElementById('chat-list');
            chatList.innerHTML = '';
            data.forEach(chat => {
                const li = document.createElement('li');
                li.innerHTML = `<strong>${chat.name}:</strong> ${chat.message}`;
                chatList.appendChild(li);
            });
        })
        .catch(error => console.error('Error:', error));
}

// 채팅 메시지 전송
function sendMessage() {
    const roomId = document.getElementById('room-title').dataset.roomId;
    const name = document.getElementById('chat-name').value;
    const message = document.getElementById('chat-message').value;

    if (!name || !message) {
        alert('이름과 메시지를 모두 입력해주세요.');
        return;
    }

    fetch(`/rooms/${roomId}/chats`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: name,
            message: message
        }),
    })
        .then(response => response.json())
        .then(() => {
            loadChats(roomId);  // 전송 후 채팅목록 다시 불러오기
            document.getElementById('chat-name').value = '';
            document.getElementById('chat-message').value = '';
        })
        .catch(error => console.error('Error:', error));
}

// 뒤로가기
function goBack() {
    document.querySelector('.container').classList.remove('hidden');
    document.getElementById('chat-room').classList.add('hidden');
}
