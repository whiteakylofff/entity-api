package me.whiteakyloff.entityapi.entity.util;

import lombok.var;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonParser;

@UtilityClass
public class MojangUtil
{
    private final String UUID_URL_STRING = "https://api.mojang.com/users/profiles/minecraft/";
    private final String SKIN_URL_STRING = "https://sessionserver.mojang.com/session/minecraft/profile/";

    private final Map<String, Skin> skinMap = new HashMap<>();

    private String readURL(String url) throws IOException {
        var connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "entity-api");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setDoOutput(true);

        var output = new StringBuilder();
        var in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while (in.ready()) {
            output.append(in.readLine());
        }
        in.close();
        return output.toString();
    }

    public Skin getSkinTextures(String name) {
        var cachedSkin = MojangUtil.skinMap.get(name);
        if (cachedSkin != null && !cachedSkin.isExpired()) {
            return cachedSkin;
        }
        try {
            var playerUUID = new JsonParser()
                    .parse(MojangUtil.readURL(UUID_URL_STRING + name))
                    .getAsJsonObject()
                    .get("id")
                    .getAsString();
            var skinUrl = MojangUtil.readURL(SKIN_URL_STRING + playerUUID + "?unsigned=false");
            var textureProperty = new JsonParser()
                    .parse(skinUrl)
                    .getAsJsonObject()
                    .get("properties")
                    .getAsJsonArray()
                    .get(0)
                    .getAsJsonObject();
            var texture = textureProperty.get("value").getAsString();
            var signature = textureProperty.get("signature").getAsString();
            var skin = new Skin(System.currentTimeMillis(), name, playerUUID, texture, signature);

            MojangUtil.skinMap.put(name, skin);
            return skin;
        } catch (IOException exception) {
            return null;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public class Skin
    {
        private final long timestamp;
        private final String skinName, playerUUID, value, signature;

        public boolean isExpired() {
            return System.currentTimeMillis() - this.timestamp > TimeUnit.HOURS.toMillis(12L);
        }
    }
}
