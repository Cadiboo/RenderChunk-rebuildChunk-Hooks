package cadiboo.renderchunkrebuildchunkhooks;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class MethodMatcher {

	private final String clsName;
	private final String description;
	private final String srgName;
	private final String mcpName;

	public MethodMatcher(final String clsName, final String description, final String mcpName, final String srgName) {
		this.clsName = clsName;
		this.description = description;
		this.srgName = srgName;
		this.mcpName = mcpName;
	}

	public boolean match(final String methodName, final String methodDesc) {
		if (!methodDesc.equals(this.description)) {
			return false;
		}
		if (methodName.equals(this.mcpName)) {
			return true;
		}
		if (!VisitorHelper.useSrgNames()) {
			return false;
		}
		final String mapped = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(this.clsName, methodName, methodDesc);
		return mapped.equals(this.srgName);
	}

	@Override
	public String toString() {
		return String.format("Matcher: %s.[%s,%s] %s", this.clsName, this.srgName, this.mcpName, this.description);
	}
}
