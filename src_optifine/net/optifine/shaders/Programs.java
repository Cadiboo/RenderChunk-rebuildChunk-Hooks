package net.optifine.shaders;

import java.util.ArrayList;
import java.util.List;

public class Programs {
   private List programs = new ArrayList();
   private Program programNone;

   public Programs() {
      this.programNone = this.make("", ProgramStage.NONE, true);
   }

   public Program make(String name, ProgramStage programStage, Program backupProgram) {
      int index = this.programs.size();
      Program prog = new Program(index, name, programStage, backupProgram);
      this.programs.add(prog);
      return prog;
   }

   private Program make(String name, ProgramStage programStage, boolean ownBackup) {
      int index = this.programs.size();
      Program prog = new Program(index, name, programStage, ownBackup);
      this.programs.add(prog);
      return prog;
   }

   public Program makeGbuffers(String name, Program backupProgram) {
      return this.make(name, ProgramStage.GBUFFERS, backupProgram);
   }

   public Program makeComposite(String name) {
      return this.make(name, ProgramStage.COMPOSITE, this.programNone);
   }

   public Program makeDeferred(String name) {
      return this.make(name, ProgramStage.DEFERRED, this.programNone);
   }

   public Program makeShadow(String name, Program backupProgram) {
      return this.make(name, ProgramStage.SHADOW, backupProgram);
   }

   public Program[] makeComposites(String prefix, int count) {
      Program[] ps = new Program[count];

      for(int i = 0; i < count; ++i) {
         String name = i == 0 ? prefix : prefix + i;
         ps[i] = this.makeComposite(name);
      }

      return ps;
   }

   public Program[] makeDeferreds(String prefix, int count) {
      Program[] ps = new Program[count];

      for(int i = 0; i < count; ++i) {
         String name = i == 0 ? prefix : prefix + i;
         ps[i] = this.makeDeferred(name);
      }

      return ps;
   }

   public Program getProgramNone() {
      return this.programNone;
   }

   public int getCount() {
      return this.programs.size();
   }

   public Program getProgram(String name) {
      if (name == null) {
         return null;
      } else {
         for(int i = 0; i < this.programs.size(); ++i) {
            Program p = (Program)this.programs.get(i);
            String progName = p.getName();
            if (progName.equals(name)) {
               return p;
            }
         }

         return null;
      }
   }

   public String[] getProgramNames() {
      String[] names = new String[this.programs.size()];

      for(int i = 0; i < names.length; ++i) {
         names[i] = ((Program)this.programs.get(i)).getName();
      }

      return names;
   }

   public Program[] getPrograms() {
      Program[] arr = (Program[])((Program[])this.programs.toArray(new Program[this.programs.size()]));
      return arr;
   }

   public Program[] getPrograms(Program programFrom, Program programTo) {
      int iFrom = programFrom.getIndex();
      int iTo = programTo.getIndex();
      if (iFrom > iTo) {
         int i = iFrom;
         iFrom = iTo;
         iTo = i;
      }

      Program[] progs = new Program[iTo - iFrom + 1];

      for(int i = 0; i < progs.length; ++i) {
         progs[i] = (Program)this.programs.get(iFrom + i);
      }

      return progs;
   }

   public String toString() {
      return this.programs.toString();
   }
}
