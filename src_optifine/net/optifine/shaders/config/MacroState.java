package net.optifine.shaders.config;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionResolver;
import net.optifine.expr.ParseException;

public class MacroState {
   private boolean active = true;
   private Deque deque = new ArrayDeque();
   private Map mapMacroValues = new HashMap();
   private static final Pattern PATTERN_DIRECTIVE = Pattern.compile("\\s*#\\s*(\\w+)\\s*(.*)");
   private static final Pattern PATTERN_DEFINED = Pattern.compile("defined\\s+(\\w+)");
   private static final Pattern PATTERN_DEFINED_FUNC = Pattern.compile("defined\\s*\\(\\s*(\\w+)\\s*\\)");
   private static final Pattern PATTERN_MACRO = Pattern.compile("(\\w+)");

   public boolean isLineActive(String line) {
      Matcher m = PATTERN_DIRECTIVE.matcher(line);
      if (!m.matches()) {
         return this.active;
      } else {
         String name = m.group(1);
         String param = m.group(2);
         int posComment = param.indexOf("//");
         if (posComment >= 0) {
            param = param.substring(0, posComment);
         }

         boolean activePrev = this.active;
         this.processMacro(name, param);
         this.active = !this.deque.contains(Boolean.FALSE);
         return this.active || activePrev;
      }
   }

   private void processMacro(String name, String param) {
      StringTokenizer tok = new StringTokenizer(param, " \t");
      String macro = tok.hasMoreTokens() ? tok.nextToken() : "";
      String rest = tok.hasMoreTokens() ? tok.nextToken("").trim() : "";
      if (name.equals("define")) {
         this.mapMacroValues.put(macro, rest);
      } else if (name.equals("undef")) {
         this.mapMacroValues.remove(macro);
      } else {
         boolean act;
         if (name.equals("ifdef")) {
            act = this.mapMacroValues.containsKey(macro);
            this.deque.add(act);
         } else if (name.equals("ifndef")) {
            act = !this.mapMacroValues.containsKey(macro);
            this.deque.add(act);
         } else if (name.equals("if")) {
            act = this.eval(param);
            this.deque.add(act);
         } else if (!this.deque.isEmpty()) {
            if (name.equals("else")) {
               act = ((Boolean)this.deque.removeLast()).booleanValue();
               boolean act = !act;
               this.deque.add(act);
            } else if (name.equals("elif")) {
               this.deque.removeLast();
               act = this.eval(param);
               this.deque.add(act);
            } else if (name.equals("endif")) {
               this.deque.removeLast();
            }
         }
      }
   }

   private boolean eval(String str) {
      Matcher md = PATTERN_DEFINED.matcher(str);
      str = md.replaceAll("defined_$1");
      Matcher mdf = PATTERN_DEFINED_FUNC.matcher(str);
      str = mdf.replaceAll("defined_$1");
      boolean replaced = false;
      int count = 0;

      label52:
      do {
         replaced = false;
         Matcher mmn = PATTERN_MACRO.matcher(str);

         String match;
         char ch;
         do {
            do {
               do {
                  if (!mmn.find()) {
                     continue label52;
                  }

                  match = mmn.group();
               } while(match.length() <= 0);

               ch = match.charAt(0);
            } while(!Character.isLetter(ch) && ch != '_');
         } while(!this.mapMacroValues.containsKey(match));

         String val = (String)this.mapMacroValues.get(match);
         if (val == null) {
            val = "1";
         }

         int start = mmn.start();
         int end = mmn.end();
         str = str.substring(0, start) + " " + val + " " + str.substring(end);
         replaced = true;
         ++count;
      } while(replaced && count < 100);

      if (count >= 100) {
         .Config.warn("Too many iterations: " + count + ", when resolving: " + str);
         return true;
      } else {
         try {
            IExpressionResolver er = new MacroExpressionResolver(this.mapMacroValues);
            ExpressionParser ep = new ExpressionParser(er);
            IExpressionBool expr = ep.parseBool(str);
            boolean ret = expr.eval();
            return ret;
         } catch (ParseException var12) {
            .Config.warn("Invalid macro expression: " + str);
            .Config.warn("Error: " + var12.getMessage());
            return false;
         }
      }
   }
}
