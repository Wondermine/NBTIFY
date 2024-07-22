package io.wondermine.nbtify.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.*;

import java.util.Objects;

public class NBTToJsonHelper {

    public static JsonObject nbtToJson(CompoundTag nbt) {
        JsonObject jsonObject = new JsonObject();
        for (String key : nbt.getAllKeys()) {
            jsonObject.add(key, convertTag(Objects.requireNonNull(nbt.get(key))));
        }
        return jsonObject;
    }

    private static JsonElement convertTag(Tag tag) {
        return switch (tag.getId()) {
            case 10 -> // Compound
                    nbtToJson((CompoundTag) tag);
            case 9 -> // List
                    convertList((ListTag) tag);
            case 8 -> // String
                new JsonPrimitive(tag.getAsString());
            case 3 -> // Int
                    new JsonPrimitive(((IntTag) tag).getAsInt());
            case 1 -> // Byte
                    new JsonPrimitive(((ByteTag) tag).getAsByte());
            case 2 -> // Short
                    new JsonPrimitive(((ShortTag) tag).getAsShort());
            case 4 -> // Long
                    new JsonPrimitive(((LongTag) tag).getAsLong());
            case 5 -> // Float
                    new JsonPrimitive(((FloatTag) tag).getAsFloat());
            case 6 -> // Double
                    new JsonPrimitive(((DoubleTag) tag).getAsDouble());
            case 7 -> // Byte Array
                    convertByteArray((ByteArrayTag) tag);
            case 11 -> // Int Array
                    convertIntArray((IntArrayTag) tag);
            case 12 -> // Long Array
                    convertLongArray((LongArrayTag) tag);
            default -> new JsonPrimitive(tag.toString());
        };
    }

    private static JsonArray convertList(ListTag list) {
        JsonArray jsonArray = new JsonArray();
        for (Tag element : list) {
            jsonArray.add(convertTag(element));
        }
        return jsonArray;
    }

    private static JsonArray convertByteArray(ByteArrayTag array) {
        JsonArray jsonArray = new JsonArray();
        for (byte b : array.getAsByteArray()) {
            jsonArray.add(b);
        }
        return jsonArray;
    }

    private static JsonArray convertIntArray(IntArrayTag array) {
        JsonArray jsonArray = new JsonArray();
        for (int i : array.getAsIntArray()) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    private static JsonArray convertLongArray(LongArrayTag array) {
        JsonArray jsonArray = new JsonArray();
        for (long l : array.getAsLongArray()) {
            jsonArray.add(l);
        }
        return jsonArray;
    }
}
