import com.google.common.collect.Lists;
import java.util.List;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.optifine.reflect.Reflector;

public class awu$Builder {
   private final aow block;
   private final List listed = Lists.newArrayList();
   private final List unlisted = Lists.newArrayList();

   public awu$Builder(aow block) {
      this.block = block;
   }

   public awu$Builder add(axj... props) {
      axj[] var2 = props;
      int var3 = props.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         axj prop = var2[var4];
         this.listed.add(prop);
      }

      return this;
   }

   public awu$Builder add(IUnlistedProperty... props) {
      IUnlistedProperty[] var2 = props;
      int var3 = props.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         IUnlistedProperty prop = var2[var4];
         this.unlisted.add(prop);
      }

      return this;
   }

   public awu build() {
      axj[] listed = new axj[this.listed.size()];
      listed = (axj[])this.listed.toArray(listed);
      if (this.unlisted.size() == 0) {
         return new awu(this.block, listed);
      } else {
         IUnlistedProperty[] unlisted = new IUnlistedProperty[this.unlisted.size()];
         unlisted = (IUnlistedProperty[])this.unlisted.toArray(unlisted);
         return (awu)Reflector.newInstance(Reflector.ExtendedBlockState_Constructor, this.block, listed, unlisted);
      }
   }
}
