package com.seniorguard.app.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun MainScreen(onOpenAccessibility: () -> Unit, onOpenUsageStats: () -> Unit) {
    var fixCount by remember { mutableIntStateOf(0) }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("SeniorGuard", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("智能守护中", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(24.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("守护状态", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(8.dp))
                Text("守护运行中", fontSize = 16.sp)
                Text("今日自动修复: $fixCount 次", fontSize = 14.sp)
            }
        }
        Spacer(Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("覆盖能力", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(8.dp))
                Text("\u2705 25项自动修复", fontSize = 16.sp)
                Text("\u26a0\ufe0f 3项检测引导", fontSize = 16.sp)
                Text("总计覆盖28项常见问题", fontSize = 14.sp)
            }
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = onOpenAccessibility, modifier = Modifier.fillMaxWidth()) { Text("开启无障碍权限") }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onOpenUsageStats, modifier = Modifier.fillMaxWidth()) { Text("开启使用统计权限") }
    }
}