package wx.demo.hook.chat

import android.content.Intent
import com.highcapable.yukihookapi.hook.param.HookParam
import me.hd.wauxv.hook.anno.HookAnno
import me.hd.wauxv.hook.anno.ViewAnno
import me.hd.wauxv.hook.base.SwitchHook
import me.hd.wauxv.hook.core.api.IStartActivity
import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
@HookAnno
@ViewAnno
object AutoSelectOriginalPhotoHook : SwitchHook("AutoSelectOriginalPhotoHook"), IStartActivity {
    override val location = "聊天"
    override val funcName = "自动勾选原图"
    override val funcDesc = "在发送图片和视频时自动勾选原图选项"

    override fun initOnce() {
    }

    override fun onStartActivityIntent(param: HookParam, intent: Intent) {
        if (!isEnabled) return
        when (intent.component?.className) {
            "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI",
            "com.tencent.mm.plugin.gallery.ui.ImagePreviewUI" -> {
                intent.putExtra("send_raw_img", true)
            }
        }
    }
}
