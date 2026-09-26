package net.maku.framework.common.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree node; anything that needs to be a tree node must extend this class
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Data
public class TreeNode<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Primary key
     */
    @Schema(description = "id")
    private Long id;
    /**
     * Parent ID
     */
    @Schema(description = "Parent ID")
    private Long pid;
    /**
     * Child node list
     */
    private List<T> children = new ArrayList<>();
}