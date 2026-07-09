package com.seniorguard.app.service
import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
class AdBlockerService : AccessibilityService() {
    private val closeKeywords = listOf("跳过", "关闭", "\u00d7", "X", "close", "skip",
        "知道了", "确定", "同意并继续", "暂不", "取消", "广告", "ad", "关闭广告")
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
            && event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return
        val root = rootInActiveWindow ?: return
        try { findAndClickCloseButton(root) } finally { root.recycle() }
    }
    private fun findAndClickCloseButton(node: AccessibilityNodeInfo) {
        for (keyword in closeKeywords) {
            for (n in node.findAccessibilityNodeInfosByText(keyword)) {
                if (n.isClickable && n.isVisibleToUser) { n.performAction(AccessibilityNodeInfo.ACTION_CLICK); return }
            }
        }
    }
    override fun onInterrupt() {}
}