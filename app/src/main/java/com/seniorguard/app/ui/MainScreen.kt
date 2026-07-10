package com.seniorguard.app.ui
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seniorguard.app.engine.SuspiciousApp

@Composable
fun MainScreen(
    suspiciousApps: List<SuspiciousApp>,
    onOpenAccessibility: () -> Unit,
    onOpenUsageStats: () -> Unit,
    onUninstall: (String) -> Unit
) {
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
                if (suspiciousApps.isEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text("未发现可疑应用", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    Spacer(Modifier.height(4.dp))
                    Text("发现 ${suspiciousApps.size} 个可疑应用", fontSize = 14.sp, color = MaterialTheme.colorScheme.error)
                }
            }
        }
        if (suspiciousApps.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text("可疑应用", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.align(Alignment.Start))
            Spacer(Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(suspiciousApps) { app ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(Modifier.padding(12.dp)) {
                            Text(app.appName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(app.pkgName, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(Modifier.height(4.dp))
                            Text("风险: ${app.reasons.joinToString(", ")}", fontSize = 13.sp, color = MaterialTheme.colorScheme.error)
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton(onClick = { onUninstall(app.pkgName) }, modifier = Modifier.align(Alignment.End)) {
                                Text("卸载")
                            }
                        }
                    }
                }
            }
        } else {
            Spacer(Modifier.weight(1f))
        }
        Button(onClick = onOpenAccessibility, modifier = Modifier.fillMaxWidth()) { Text("开启无障碍权限") }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onOpenUsageStats, modifier = Modifier.fillMaxWidth()) { Text("开启使用统计权限") }
    }
}
