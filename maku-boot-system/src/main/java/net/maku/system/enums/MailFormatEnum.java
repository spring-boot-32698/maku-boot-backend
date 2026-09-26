package net.maku.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Mail format enum
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Getter
@AllArgsConstructor
public enum MailFormatEnum {
    /**
     * Plain text
     */
    TEXT,
    /**
     * HTML
     */
    HTML,
    /**
     * Template
     */
    TEMPLATE;
}
