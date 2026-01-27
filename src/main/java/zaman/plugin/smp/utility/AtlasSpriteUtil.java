package zaman.plugin.smp.utility;

import net.kyori.adventure.text.Component;

public class AtlasSpriteUtil {
    /**
     * Atlas Sprite 컴포넌트 생성
     * @param atlas 아틀라스 이름 (예: "minecraft:blocks", "minecraft:items")
     * @param sprite 스프라이트 경로 (예: "block/diamond_block", "item/diamond")
     * @param shadowColor 그림자 색상 (RGB 정수값)
     */
    public static Component createAtlasSprite(String atlas, String sprite, int shadowColor) {
        // Adventure API의 Component를 직접 JSON으로 변환
        String json = String.format(
                "{\"object\":\"atlas\",\"atlas\":\"%s\",\"sprite\":\"%s\",\"shadow_color\":%d}",
                atlas, sprite, shadowColor
        );

        return Component.text()
                .append(Component.text(json))
                .build();
    }

    public static Component createAtlasSprite(String atlas, String sprite) {
        return createAtlasSprite(atlas, sprite, 14606046); // 기본 그림자 색상
    }

    public static Component createItemIcon(String itemName) {
        return createAtlasSprite("minecraft:items", "minecraft:item/" + itemName);
    }

    public static Component createBlockIcon(String blockName) {
        return createAtlasSprite("minecraft:blocks", "minecraft:block/" + blockName);
    }
}