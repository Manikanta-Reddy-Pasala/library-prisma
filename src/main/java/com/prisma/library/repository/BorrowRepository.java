package com.prisma.library.repository;

import com.prisma.library.model.Borrow;
import com.prisma.library.model.BorrowId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, BorrowId> {
    
}
