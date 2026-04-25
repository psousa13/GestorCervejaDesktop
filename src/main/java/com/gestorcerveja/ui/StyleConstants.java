package com.gestorcerveja.ui;

public class StyleConstants {

    // ── Modal / ModalOverlay tokens ───────────────────────────────────────────
    /** Cor de destaque principal (vinho). */
    public static final String ACCENT       = "#7B2D3E";
    /** Fundo do card do modal (branco puro). */
    public static final String BG_DARK      = "white";
    /** Fundo dos inputs no modal (cinzento-quente claro). */
    public static final String BG_MID       = "#F0EBE8";
    /** Cor de texto principal. */
    public static final String TEXT_PRIMARY = "#1A1614";

    // ── Botões ────────────────────────────────────────────────────────────────
    public static final String BTN_PRIMARY =
            "-fx-background-color: #7B2D3E; -fx-text-fill: white; -fx-font-size: 12px; " +
                    "-fx-font-weight: bold; -fx-background-radius: 7px; -fx-padding: 6px 13px; " +
                    "-fx-cursor: hand; -fx-border-width: 0;";

    public static final String BTN_SECONDARY =
            "-fx-background-color: white; -fx-text-fill: #1A1614; -fx-font-size: 12px; " +
                    "-fx-border-color: #E8E2DF; -fx-border-width: 1px; -fx-border-radius: 7px; " +
                    "-fx-background-radius: 7px; -fx-padding: 6px 13px; -fx-cursor: hand;";

    // ── Cards ─────────────────────────────────────────────────────────────────
    public static final String CARD_NORMAL =
            "-fx-background-color: white; -fx-border-color: #E8E2DF; -fx-border-width: 1px; " +
                    "-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 13px 15px; " +
                    "-fx-cursor: hand;";

    public static final String CARD_SELECTED =
            "-fx-background-color: #F5EEF0; -fx-border-color: #7B2D3E; -fx-border-width: 1.5px; " +
                    "-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 13px 15px; " +
                    "-fx-cursor: hand;";

    // ── Navegação ─────────────────────────────────────────────────────────────
    public static final String NAV_NORMAL =
            "-fx-background-color: transparent; -fx-text-fill: rgba(255,255,255,0.5); " +
                    "-fx-font-size: 12.5px; -fx-padding: 8px 10px; -fx-cursor: hand; " +
                    "-fx-background-radius: 6px; -fx-alignment: center-left; -fx-border-width: 0;";

    public static final String NAV_ACTIVE =
            "-fx-background-color: rgba(255,255,255,0.12); -fx-text-fill: white; " +
                    "-fx-font-size: 12.5px; -fx-padding: 8px 10px; -fx-cursor: hand; " +
                    "-fx-background-radius: 6px; -fx-alignment: center-left; -fx-border-width: 0;";

    // ── Layout ────────────────────────────────────────────────────────────────
    public static final String SIDEBAR_BG =
            "-fx-background-color: #3E1520; -fx-min-width: 210px; -fx-max-width: 210px; -fx-pref-width: 210px;";

    public static final String CONTENT_BG =
            "-fx-background-color: #FAF8F6;";

    public static final String TOPBAR =
            "-fx-background-color: white; -fx-border-color: #E8E2DF; " +
                    "-fx-border-width: 0 0 1px 0; -fx-padding: 11px 22px;";

    // ── Tabela ────────────────────────────────────────────────────────────────
    public static final String STAT_CARD =
            "-fx-background-color: white; -fx-border-color: #E8E2DF; -fx-border-width: 1px; " +
                    "-fx-border-radius: 9px; -fx-background-radius: 9px; -fx-padding: 14px;";

    public static final String LIST_CARD =
            "-fx-background-color: white; -fx-border-color: #E8E2DF; -fx-border-width: 1px; " +
                    "-fx-border-radius: 9px; -fx-background-radius: 9px; -fx-padding: 15px;";

    public static final String TABLE_VIEW =
            "-fx-background-color: white; -fx-border-color: #E8E2DF; -fx-border-width: 1px; " +
                    "-fx-border-radius: 9px; -fx-background-radius: 9px;";

    // ── Outros ────────────────────────────────────────────────────────────────
    public static final String BANNER =
            "-fx-background-color: #3E1520; -fx-background-radius: 11px; -fx-padding: 18px 22px;";

    public static final String QUICK_BTN =
            "-fx-background-color: rgba(255,255,255,0.09); -fx-border-color: rgba(255,255,255,0.13); " +
                    "-fx-border-width: 1px; -fx-border-radius: 6px; -fx-background-radius: 6px; " +
                    "-fx-text-fill: rgba(255,255,255,0.8); -fx-font-size: 11px; -fx-padding: 6px 12px; -fx-cursor: hand;";

    public static final String ALERT_STRIP =
            "-fx-background-color: #FAEEDA; -fx-border-color: #FAC775; -fx-border-width: 1px; " +
                    "-fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 9px 14px; " +
                    "-fx-text-fill: #854F0B; -fx-font-size: 12px;";

    public static String badge(String color) {
        return switch (color) {
            case "green" -> "-fx-background-color: #EAF3DE; -fx-text-fill: #3B6D11; -fx-background-radius: 20px; -fx-padding: 2px 7px; -fx-font-size: 10px;";
            case "amber" -> "-fx-background-color: #FAEEDA; -fx-text-fill: #854F0B; -fx-background-radius: 20px; -fx-padding: 2px 7px; -fx-font-size: 10px;";
            case "wine"  -> "-fx-background-color: #F5EEF0; -fx-text-fill: #7B2D3E; -fx-background-radius: 20px; -fx-padding: 2px 7px; -fx-font-size: 10px;";
            case "blue"  -> "-fx-background-color: #E6F1FB; -fx-text-fill: #185FA5; -fx-background-radius: 20px; -fx-padding: 2px 7px; -fx-font-size: 10px;";
            case "red"   -> "-fx-background-color: #FCEBEB; -fx-text-fill: #A32D2D; -fx-background-radius: 20px; -fx-padding: 2px 7px; -fx-font-size: 10px;";
            default      -> "-fx-background-color: #E8E2DF; -fx-text-fill: #1A1614; -fx-background-radius: 20px; -fx-padding: 2px 7px; -fx-font-size: 10px;";
        };
    }
}
