package com.fontaipi.expensetracker.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.expensetracker.data.database.entity.WalletColors
import com.fontaipi.expensetracker.model.Wallet
import com.fontaipi.expensetracker.ui.theme.ExpenseTrackerTheme
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionCard(
    icon: ImageVector,
    containerColor: Color,
    text: String,
//    category: Category = Category(
//        name = "Groceries",
//        color = CategoryRed,
//        icon = CategoryIcon.GROCERIES,
//    ),
    hashtags: Set<String> = setOf("starbucks", "flatwhite"),
    price: String = "3,50€",
    wallet: Wallet = Wallet(
        name = "Main account",
        balance = BigDecimal(100),
        colors = WalletColors.Type1
    ),
) {
    Card(
        onClick = { },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                CategoryBox(
                    icon = icon,
                    containerColor = containerColor,
                )
                Column {
                    Text(text = text, style = MaterialTheme.typography.titleMedium)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        items(hashtags.toList()) { category ->
                            Text(
                                text = "#$category",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }


            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text(text = price, style = MaterialTheme.typography.titleMedium)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        wallet.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(wallet.colors.primary)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TransactionCardPreview() {
    ExpenseTrackerTheme {
        //TransactionCard()
    }
}