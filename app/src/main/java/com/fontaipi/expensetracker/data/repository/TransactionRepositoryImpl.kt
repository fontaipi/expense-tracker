package com.fontaipi.expensetracker.data.repository

import com.fontaipi.expensetracker.data.database.dao.AccountDao
import com.fontaipi.expensetracker.data.database.dao.TransactionDao
import com.fontaipi.expensetracker.data.database.entity.TransactionEntity
import com.fontaipi.expensetracker.data.database.entity.UpdateAccountBalance
import com.fontaipi.expensetracker.data.database.entity.asExternalModel
import com.fontaipi.expensetracker.model.Transaction
import com.fontaipi.expensetracker.ui.page.home.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.TimeZone
import javax.inject.Inject


class TransactionRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override fun getTransactions(limit: Int?): Flow<List<Transaction>> {
        return transactionDao.getTransactions(limit ?: Int.MAX_VALUE).map { transactions ->
            transactions.map { transaction ->
                transaction.asExternalModel()
            }
        }
    }

    override fun getTransactionsByAccountId(accountId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByAccountId(accountId).map { transactions ->
            transactions.map { transaction ->
                transaction.asExternalModel()
            }
        }
    }

    override fun getTransactionForMonth(month: Int, year: Int): Flow<List<Transaction>> {
        return transactionDao.getTransactions().map { transactions ->
            transactions.map { transaction ->
                transaction.asExternalModel()
            }.filter {
                val date =
                    LocalDateTime.ofInstant(it.date, TimeZone.getDefault().toZoneId())
                date.monthValue == month && date.year == year
            }
        }
    }

    override suspend fun addTransaction(transaction: TransactionEntity) {
        withContext(Dispatchers.IO) {
            val account = accountDao.getAccount(transaction.accountId)
            val amount = when (transaction.type) {
                TransactionType.INCOME -> transaction.amount
                TransactionType.EXPENSE -> -transaction.amount
                else -> -transaction.amount
            }
            accountDao.updateAccountBalance(
                UpdateAccountBalance(
                    id = transaction.accountId,
                    balance = account.balance + amount
                )
            )
            transactionDao.upsertTransaction(transaction)
        }
    }
}