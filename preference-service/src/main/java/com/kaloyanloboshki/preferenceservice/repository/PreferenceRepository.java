package com.kaloyanloboshki.preferenceservice.repository;

import com.kaloyanloboshki.preferenceservice.model.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    Preference findByUserId(long userId);

    Optional<Preference> findOptionalByUserId(Long userId);

    @Query("select p.userId from Preference p where p.preferredCategory = :category")
    List<Long> getPreferenceByPreferredCategory(String category);
}
