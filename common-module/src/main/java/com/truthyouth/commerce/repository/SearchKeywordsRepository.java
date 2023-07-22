package com.truthyouth.commerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.truthyouth.commerce.entities.SearchKeywords;

public interface SearchKeywordsRepository extends JpaRepository<SearchKeywords, Long>{

	@Query(value = "select * from search_keywords where keywords like :keyword limit 10", nativeQuery = true)
	List<SearchKeywords> findByKeyword(String keyword);

}
