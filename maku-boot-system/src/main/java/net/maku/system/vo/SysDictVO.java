package net.maku.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * All dictionaries
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
@Schema(description = "All dictionaries")
public class SysDictVO {
    @Schema(description = "Dictionary type")
    private String dictType;

    @Schema(description = "Dictionary data list")
    private List<DictData> dataList = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Dictionary data")
    public static class DictData {
        @Schema(description = "Dictionary label")
        private String dictLabel;

        @Schema(description = "Dictionary value")
        private String dictValue;

        @Schema(description = "Label style")
        private String labelClass;

        @Schema(description = "Parent node")
        private String pid;
    }
}
