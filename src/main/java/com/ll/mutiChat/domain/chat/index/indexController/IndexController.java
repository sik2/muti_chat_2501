package com.ll.mutiChat.domain.chat.index.indexController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.ll.mutiChat.domain.chat.indexController
 * fileName       : IndexController
 * author         : sungjun
 * date           : 2025-01-02
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-02        kyd54       최초 생성
 */
@Controller
@RequiredArgsConstructor
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "domain/chat/chatRoom/list";
    }
}
