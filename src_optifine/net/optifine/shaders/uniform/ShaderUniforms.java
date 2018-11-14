package net.optifine.shaders.uniform;

import java.util.ArrayList;
import java.util.List;

public class ShaderUniforms {
   private final List listUniforms = new ArrayList();

   public void setProgram(int program) {
      for(int i = 0; i < this.listUniforms.size(); ++i) {
         ShaderUniformBase su = (ShaderUniformBase)this.listUniforms.get(i);
         su.setProgram(program);
      }

   }

   public void reset() {
      for(int i = 0; i < this.listUniforms.size(); ++i) {
         ShaderUniformBase su = (ShaderUniformBase)this.listUniforms.get(i);
         su.reset();
      }

   }

   public ShaderUniform1i make1i(String name) {
      ShaderUniform1i su = new ShaderUniform1i(name);
      this.listUniforms.add(su);
      return su;
   }

   public ShaderUniform2i make2i(String name) {
      ShaderUniform2i su = new ShaderUniform2i(name);
      this.listUniforms.add(su);
      return su;
   }

   public ShaderUniform4i make4i(String name) {
      ShaderUniform4i su = new ShaderUniform4i(name);
      this.listUniforms.add(su);
      return su;
   }

   public ShaderUniform1f make1f(String name) {
      ShaderUniform1f su = new ShaderUniform1f(name);
      this.listUniforms.add(su);
      return su;
   }

   public ShaderUniform3f make3f(String name) {
      ShaderUniform3f su = new ShaderUniform3f(name);
      this.listUniforms.add(su);
      return su;
   }

   public ShaderUniform4f make4f(String name) {
      ShaderUniform4f su = new ShaderUniform4f(name);
      this.listUniforms.add(su);
      return su;
   }

   public ShaderUniformM4 makeM4(String name) {
      ShaderUniformM4 su = new ShaderUniformM4(name);
      this.listUniforms.add(su);
      return su;
   }
}
