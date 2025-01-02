package com.ll.mutiChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화 생성일, 수정일 자동 주입
@SpringBootApplication
public class MutiChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutiChatApplication.class, args);
	}

}
