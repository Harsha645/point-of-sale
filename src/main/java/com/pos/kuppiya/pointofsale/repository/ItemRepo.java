package com.pos.kuppiya.pointofsale.repository;

import com.pos.kuppiya.pointofsale.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ItemRepo extends JpaRepository<Item,Integer> {
    List<Item> findAllByActiveState(boolean states);

    int countAllByActiveStateEquals(boolean b);

    Page<Item>findAllByActiveStateEquals (boolean activeState, Pageable pageable);
}

