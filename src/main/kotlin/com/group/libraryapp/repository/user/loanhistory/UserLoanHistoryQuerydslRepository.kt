package com.group.libraryapp.repository.user.loanhistory

import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserLoanHistoryQuerydslRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {

    fun find(bookName: String, status: UserLoanStatus? = null): UserLoanHistory? {
        return jpaQueryFactory
            .select(userLoanHistory)
            .from(userLoanHistory)
            .where(
                userLoanHistory.bookName.eq(bookName),
                status?.let { userLoanHistory.status.eq(status) }
            )
            .limit(1)
            .fetchOne()
    }

    fun count(status: UserLoanStatus): Long {
        return jpaQueryFactory
            .select(userLoanHistory.count())
            .where(
                userLoanHistory.status.eq(status)
            )
            .from(userLoanHistory)
            .fetchOne() ?: 0L
    }
}