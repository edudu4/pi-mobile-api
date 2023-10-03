package com.projeto.PI.Mobile.repository;

import com.projeto.PI.Mobile.domain.Habito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {
}
