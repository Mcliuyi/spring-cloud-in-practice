package net.jaggerwang.scip.post.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import net.jaggerwang.scip.post.adapter.repository.jpa.entity.PostDo;

@Repository
public interface PostJpaRepository
        extends JpaRepository<PostDo, Long>, QuerydslPredicateExecutor<PostDo> {
}
