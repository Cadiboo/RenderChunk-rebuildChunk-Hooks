package net.optifine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import net.optifine.config.ConnectedParser;
import net.optifine.config.Matches;
import net.optifine.config.RangeListInt;
import net.optifine.render.Blender;
import net.optifine.util.NumUtils;
import net.optifine.util.SmoothFloat;
import net.optifine.util.TextureUtils;

public class CustomSkyLayer {
   public String source = null;
   private int startFadeIn = -1;
   private int endFadeIn = -1;
   private int startFadeOut = -1;
   private int endFadeOut = -1;
   private int blend = 1;
   private boolean rotate = false;
   private float speed = 1.0F;
   private float[] axis;
   private RangeListInt days;
   private int daysLoop;
   private boolean weatherClear;
   private boolean weatherRain;
   private boolean weatherThunder;
   public anh[] biomes;
   public RangeListInt heights;
   private float transition;
   private SmoothFloat smoothPositionBrightness;
   public int textureId;
   private amu lastWorld;
   public static final float[] DEFAULT_AXIS = new float[]{1.0F, 0.0F, 0.0F};
   private static final String WEATHER_CLEAR = "clear";
   private static final String WEATHER_RAIN = "rain";
   private static final String WEATHER_THUNDER = "thunder";

   public CustomSkyLayer(Properties props, String defSource) {
      this.axis = DEFAULT_AXIS;
      this.days = null;
      this.daysLoop = 8;
      this.weatherClear = true;
      this.weatherRain = false;
      this.weatherThunder = false;
      this.biomes = null;
      this.heights = null;
      this.transition = 1.0F;
      this.smoothPositionBrightness = null;
      this.textureId = -1;
      this.lastWorld = null;
      ConnectedParser cp = new ConnectedParser("CustomSky");
      this.source = props.getProperty("source", defSource);
      this.startFadeIn = this.parseTime(props.getProperty("startFadeIn"));
      this.endFadeIn = this.parseTime(props.getProperty("endFadeIn"));
      this.startFadeOut = this.parseTime(props.getProperty("startFadeOut"));
      this.endFadeOut = this.parseTime(props.getProperty("endFadeOut"));
      this.blend = Blender.parseBlend(props.getProperty("blend"));
      this.rotate = this.parseBoolean(props.getProperty("rotate"), true);
      this.speed = this.parseFloat(props.getProperty("speed"), 1.0F);
      this.axis = this.parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
      this.days = cp.parseRangeListInt(props.getProperty("days"));
      this.daysLoop = cp.parseInt(props.getProperty("daysLoop"), 8);
      List weatherList = this.parseWeatherList(props.getProperty("weather", "clear"));
      this.weatherClear = weatherList.contains("clear");
      this.weatherRain = weatherList.contains("rain");
      this.weatherThunder = weatherList.contains("thunder");
      this.biomes = cp.parseBiomes(props.getProperty("biomes"));
      this.heights = cp.parseRangeListInt(props.getProperty("heights"));
      this.transition = this.parseFloat(props.getProperty("transition"), 1.0F);
   }

   private List parseWeatherList(String str) {
      List weatherAllowedList = Arrays.asList("clear", "rain", "thunder");
      List weatherList = new ArrayList();
      String[] weatherStrs = .Config.tokenize(str, " ");

      for(int i = 0; i < weatherStrs.length; ++i) {
         String token = weatherStrs[i];
         if (!weatherAllowedList.contains(token)) {
            .Config.warn("Unknown weather: " + token);
         } else {
            weatherList.add(token);
         }
      }

      return weatherList;
   }

   private int parseTime(String str) {
      if (str == null) {
         return -1;
      } else {
         String[] strs = .Config.tokenize(str, ":");
         if (strs.length != 2) {
            .Config.warn("Invalid time: " + str);
            return -1;
         } else {
            String hourStr = strs[0];
            String minStr = strs[1];
            int hour = .Config.parseInt(hourStr, -1);
            int min = .Config.parseInt(minStr, -1);
            if (hour >= 0 && hour <= 23 && min >= 0 && min <= 59) {
               hour -= 6;
               if (hour < 0) {
                  hour += 24;
               }

               int time = hour * 1000 + (int)((double)min / 60.0D * 1000.0D);
               return time;
            } else {
               .Config.warn("Invalid time: " + str);
               return -1;
            }
         }
      }
   }

   private boolean parseBoolean(String str, boolean defVal) {
      if (str == null) {
         return defVal;
      } else if (str.toLowerCase().equals("true")) {
         return true;
      } else if (str.toLowerCase().equals("false")) {
         return false;
      } else {
         .Config.warn("Unknown boolean: " + str);
         return defVal;
      }
   }

   private float parseFloat(String str, float defVal) {
      if (str == null) {
         return defVal;
      } else {
         float val = .Config.parseFloat(str, Float.MIN_VALUE);
         if (val == Float.MIN_VALUE) {
            .Config.warn("Invalid value: " + str);
            return defVal;
         } else {
            return val;
         }
      }
   }

   private float[] parseAxis(String str, float[] defVal) {
      if (str == null) {
         return defVal;
      } else {
         String[] strs = .Config.tokenize(str, " ");
         if (strs.length != 3) {
            .Config.warn("Invalid axis: " + str);
            return defVal;
         } else {
            float[] fs = new float[3];

            for(int i = 0; i < strs.length; ++i) {
               fs[i] = .Config.parseFloat(strs[i], Float.MIN_VALUE);
               if (fs[i] == Float.MIN_VALUE) {
                  .Config.warn("Invalid axis: " + str);
                  return defVal;
               }

               if (fs[i] < -1.0F || fs[i] > 1.0F) {
                  .Config.warn("Invalid axis values: " + str);
                  return defVal;
               }
            }

            float ax = fs[0];
            float ay = fs[1];
            float az = fs[2];
            if (ax * ax + ay * ay + az * az < 1.0E-5F) {
               .Config.warn("Invalid axis values: " + str);
               return defVal;
            } else {
               float[] as = new float[]{az, ay, -ax};
               return as;
            }
         }
      }
   }

   public boolean isValid(String path) {
      if (this.source == null) {
         .Config.warn("No source texture: " + path);
         return false;
      } else {
         this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
         if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
            int timeFadeIn = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            if (this.startFadeOut < 0) {
               this.startFadeOut = this.normalizeTime(this.endFadeOut - timeFadeIn);
               if (this.timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn)) {
                  this.startFadeOut = this.endFadeIn;
               }
            }

            int timeOn = this.normalizeTime(this.startFadeOut - this.endFadeIn);
            int timeFadeOut = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            int timeOff = this.normalizeTime(this.startFadeIn - this.endFadeOut);
            int timeSum = timeFadeIn + timeOn + timeFadeOut + timeOff;
            if (timeSum != 24000) {
               .Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + timeSum);
               return false;
            } else if (this.speed < 0.0F) {
               .Config.warn("Invalid speed: " + this.speed);
               return false;
            } else if (this.daysLoop <= 0) {
               .Config.warn("Invalid daysLoop: " + this.daysLoop);
               return false;
            } else {
               return true;
            }
         } else {
            .Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
            return false;
         }
      }
   }

   private int normalizeTime(int timeMc) {
      while(timeMc >= 24000) {
         timeMc -= 24000;
      }

      while(timeMc < 0) {
         timeMc += 24000;
      }

      return timeMc;
   }

   public void render(amu world, int timeOfDay, float celestialAngle, float rainStrength, float thunderStrength) {
      float positionBrightness = this.getPositionBrightness(world);
      float weatherBrightness = this.getWeatherBrightness(rainStrength, thunderStrength);
      float fadeBrightness = this.getFadeBrightness(timeOfDay);
      float brightness = positionBrightness * weatherBrightness * fadeBrightness;
      brightness = .Config.limit(brightness, 0.0F, 1.0F);
      if (brightness >= 1.0E-4F) {
         bus.i(this.textureId);
         Blender.setupBlend(this.blend, brightness);
         bus.G();
         if (this.rotate) {
            float angleDayStart = 0.0F;
            if (this.speed != (float)Math.round(this.speed)) {
               long worldDay = (world.S() + 18000L) / 24000L;
               double anglePerDay = (double)(this.speed % 1.0F);
               double angleDayNow = (double)worldDay * anglePerDay;
               angleDayStart = (float)(angleDayNow % 1.0D);
            }

            bus.b(360.0F * (angleDayStart + celestialAngle * this.speed), this.axis[0], this.axis[1], this.axis[2]);
         }

         bve tess = bve.a();
         bus.b(90.0F, 1.0F, 0.0F, 0.0F);
         bus.b(-90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(tess, 4);
         bus.G();
         bus.b(90.0F, 1.0F, 0.0F, 0.0F);
         this.renderSide(tess, 1);
         bus.H();
         bus.G();
         bus.b(-90.0F, 1.0F, 0.0F, 0.0F);
         this.renderSide(tess, 0);
         bus.H();
         bus.b(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(tess, 5);
         bus.b(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(tess, 2);
         bus.b(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(tess, 3);
         bus.H();
      }
   }

   private float getPositionBrightness(amu world) {
      if (this.biomes == null && this.heights == null) {
         return 1.0F;
      } else {
         float positionBrightness = this.getPositionBrightnessRaw(world);
         if (this.smoothPositionBrightness == null) {
            this.smoothPositionBrightness = new SmoothFloat(positionBrightness, this.transition);
         }

         positionBrightness = this.smoothPositionBrightness.getSmoothValue(positionBrightness);
         return positionBrightness;
      }
   }

   private float getPositionBrightnessRaw(amu world) {
      vg renderViewEntity = bib.z().aa();
      if (renderViewEntity == null) {
         return 0.0F;
      } else {
         et pos = renderViewEntity.c();
         if (this.biomes != null) {
            anh biome = world.b(pos);
            if (biome == null) {
               return 0.0F;
            }

            if (!Matches.biome(biome, this.biomes)) {
               return 0.0F;
            }
         }

         return this.heights != null && !this.heights.isInRange(pos.q()) ? 0.0F : 1.0F;
      }
   }

   private float getWeatherBrightness(float rainStrength, float thunderStrength) {
      float clearBrightness = 1.0F - rainStrength;
      float rainBrightness = rainStrength - thunderStrength;
      float weatherBrightness = 0.0F;
      if (this.weatherClear) {
         weatherBrightness += clearBrightness;
      }

      if (this.weatherRain) {
         weatherBrightness += rainBrightness;
      }

      if (this.weatherThunder) {
         weatherBrightness += thunderStrength;
      }

      weatherBrightness = NumUtils.limit(weatherBrightness, 0.0F, 1.0F);
      return weatherBrightness;
   }

   private float getFadeBrightness(int timeOfDay) {
      int timeFadeOut;
      int timeDiff;
      if (this.timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn)) {
         timeFadeOut = this.normalizeTime(this.endFadeIn - this.startFadeIn);
         timeDiff = this.normalizeTime(timeOfDay - this.startFadeIn);
         return (float)timeDiff / (float)timeFadeOut;
      } else if (this.timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut)) {
         return 1.0F;
      } else if (this.timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut)) {
         timeFadeOut = this.normalizeTime(this.endFadeOut - this.startFadeOut);
         timeDiff = this.normalizeTime(timeOfDay - this.startFadeOut);
         return 1.0F - (float)timeDiff / (float)timeFadeOut;
      } else {
         return 0.0F;
      }
   }

   private void renderSide(bve tess, int side) {
      buk wr = tess.c();
      double tx = (double)(side % 3) / 3.0D;
      double ty = (double)(side / 3) / 2.0D;
      wr.a(7, cdy.g);
      wr.b(-100.0D, -100.0D, -100.0D).a(tx, ty).d();
      wr.b(-100.0D, -100.0D, 100.0D).a(tx, ty + 0.5D).d();
      wr.b(100.0D, -100.0D, 100.0D).a(tx + 0.3333333333333333D, ty + 0.5D).d();
      wr.b(100.0D, -100.0D, -100.0D).a(tx + 0.3333333333333333D, ty).d();
      tess.b();
   }

   public boolean isActive(amu world, int timeOfDay) {
      if (world != this.lastWorld) {
         this.lastWorld = world;
         this.smoothPositionBrightness = null;
      }

      if (this.timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn)) {
         return false;
      } else {
         if (this.days != null) {
            long time = world.S();

            long timeShift;
            for(timeShift = time - (long)this.startFadeIn; timeShift < 0L; timeShift += (long)(24000 * this.daysLoop)) {
               ;
            }

            int day = (int)(timeShift / 24000L);
            int dayOfLoop = day % this.daysLoop;
            if (!this.days.isInRange(dayOfLoop)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean timeBetween(int timeOfDay, int timeStart, int timeEnd) {
      if (timeStart <= timeEnd) {
         return timeOfDay >= timeStart && timeOfDay <= timeEnd;
      } else {
         return timeOfDay >= timeStart || timeOfDay <= timeEnd;
      }
   }

   public String toString() {
      return "" + this.source + ", " + this.startFadeIn + "-" + this.endFadeIn + " " + this.startFadeOut + "-" + this.endFadeOut;
   }
}
