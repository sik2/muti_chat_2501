package com.ll.mutiChat.domain.chat;

public class JPASpecificationForChat {

    //사용자 임의의 JPA API를 만들고 싶으면 JPASpecification 클래스를 만들어서 사용하면 된다.
    //Specification<Entity Name> Method Name(Parameter)로 설정
/*    public static Specification<ChatMessage> equalRecipeNo(Long roomId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("recipe").get("recipeNo"), recipeNo);
    }

    public static Specification<ChatMessage> equalUserNo(Long userNo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("userNo"), userNo);
    }*/

}
