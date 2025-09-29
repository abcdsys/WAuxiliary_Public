package wx.demo.hook.misc

import me.hd.wauxv.data.config.DexDescData
import me.hd.wauxv.hook.anno.HookAnno
import me.hd.wauxv.hook.anno.ViewAnno
import me.hd.wauxv.hook.base.SwitchHook
import me.hd.wauxv.hook.core.dex.IDexFind
import me.hd.wauxv.hook.factory.findDexClassMethod
import me.hd.wauxv.hook.factory.toDexMethod
import org.luckypray.dexkit.DexKitBridge

@HookAnno
@ViewAnno
object ShareCheckHook : SwitchHook("ShareSignatureHook"), IDexFind {
    private object MethodCheckSign : DexDescData("ShareSignatureHook.MethodCheckSign")

    override val location = "杂项"
    override val funcName = "分享签名校验"
    override val funcDesc = "绕过第三方应用分享到微信的签名校验"

    override fun initOnce() {
        MethodCheckSign.toDexMethod {
            hook {
                beforeIfEnabled {
                    resultTrue()
                }
            }
        }
    }

    override fun dexFind(dexKit: DexKitBridge) {
        MethodCheckSign.findDexClassMethod(dexKit) {
            onMethod {
                searchPackages("com.tencent.mm.pluginsdk.model.app")
                matcher {
                    usingEqStrings("checkAppSignature get local signature failed")
                }
            }
        }
    }
}
