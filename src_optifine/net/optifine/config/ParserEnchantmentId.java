package net.optifine.config;

public class ParserEnchantmentId implements IParserInt {
   public int parse(String str, int defVal) {
      alk en = alk.b(str);
      return en == null ? defVal : alk.b(en);
   }
}
