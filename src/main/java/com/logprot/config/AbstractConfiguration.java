package com.logprot.config;

import com.logprot.Constants;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractConfiguration
{
    protected void createCategory(final ForgeConfigSpec.Builder builder, final String key)
    {
        // TODO: missing name, translation not allowed for now
        builder.comment(key).push(key);
    }

    protected void swapToCategory(final ForgeConfigSpec.Builder builder, final String key)
    {
        finishCategory(builder);
        createCategory(builder, key);
    }

    protected void finishCategory(final ForgeConfigSpec.Builder builder)
    {
        builder.pop();
    }

    private static String nameTKey(final String key)
    {
        return Constants.MOD_ID + ".config." + key;
    }

    private static String commentTKey(final String key)
    {
        return nameTKey(key) + ".comment";
    }

    private static ForgeConfigSpec.Builder buildBase(final ForgeConfigSpec.Builder builder, final String key)
    {
        return builder.comment((key)).translation(nameTKey(key));
    }

    protected static ForgeConfigSpec.BooleanValue defineBoolean(final ForgeConfigSpec.Builder builder, final String key, final boolean defaultValue)
    {
        return buildBase(builder, key).define(key, defaultValue);
    }

    protected static ForgeConfigSpec.IntValue defineInteger(final ForgeConfigSpec.Builder builder, final String key, final int defaultValue)
    {
        return defineInteger(builder, key, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    protected static ForgeConfigSpec.IntValue defineInteger(final ForgeConfigSpec.Builder builder, final String key, final int defaultValue, final int min, final int max)
    {
        return buildBase(builder, key).defineInRange(key, defaultValue, min, max);
    }

    protected static ForgeConfigSpec.LongValue defineLong(final ForgeConfigSpec.Builder builder, final String key, final long defaultValue)
    {
        return defineLong(builder, key, defaultValue, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    protected static ForgeConfigSpec.LongValue defineLong(final ForgeConfigSpec.Builder builder, final String key, final long defaultValue, final long min, final long max)
    {
        return buildBase(builder, key).defineInRange(key, defaultValue, min, max);
    }

    protected static ForgeConfigSpec.DoubleValue defineDouble(final ForgeConfigSpec.Builder builder, final String key, final double defaultValue)
    {
        return defineDouble(builder, key, defaultValue, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    protected static ForgeConfigSpec.DoubleValue defineDouble(
      final ForgeConfigSpec.Builder builder,
      final String key,
      final double defaultValue,
      final double min,
      final double max)
    {
        return buildBase(builder, key).defineInRange(key, defaultValue, min, max);
    }

    protected static <T> ForgeConfigSpec.ConfigValue<List<? extends T>> defineList(
      final ForgeConfigSpec.Builder builder,
      final String key,
      final List<? extends T> defaultValue,
      final Predicate<Object> elementValidator)
    {
        return buildBase(builder, key).defineList(key, defaultValue, elementValidator);
    }

    protected static <V extends Enum<V>> ForgeConfigSpec.EnumValue<V> defineEnum(final ForgeConfigSpec.Builder builder, final String key, final V defaultValue)
    {
        return buildBase(builder, key).defineEnum(key, defaultValue);
    }
}